import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A mechKB is an app that allows users to test their typing speed
 * while simulating mechanical keyboard sounds whenever a physical
 * key is pressed.
 */
public class mechKB extends JFrame implements KeyListener {
    private final JLabel typeArea;
    private final JLabel words;
    private final JLabel description;
    private final JButton resetButton;
    private final Sound sound = new Sound();
    private final ArrayList<Integer> keysPressed = new ArrayList<>();
    private final Countdown countdown = new Countdown();
    private final WordBank wordBank = new WordBank();
    private String wordList = wordBank.getRandomWords();

    private char[] wordListArray = wordList.toCharArray();
    private int current_letter = 0;
    private boolean isMuted = false;
    private  boolean firstPress = true;

    private final URL space_enter_start = getClass().getResource("Keyboard_Button_10.1.wav");
    private final URL space_enter_end = getClass().getResource("Keyboard_Button_10.2.wav");
    private final URL numbers_start = getClass().getResource("Keyboard_Button_5.1.wav");
    private final URL numbers_end = getClass().getResource("Keyboard_Button_5.2.wav");
    private final URL arrows_start = getClass().getResource("Keyboard_Button_9.1.wav");
    private final URL arrows_end = getClass().getResource("Keyboard_Button_9.2.wav");
    private final URL letters_start = getClass().getResource("Keyboard_Button_8.1.wav");
    private final URL letters_end = getClass().getResource("Keyboard_Button_8.2.wav");
    private final URL backspace_start = getClass().getResource("Keyboard_Button_1.1.wav");
    private final URL backspace_end = getClass().getResource("Keyboard_Button_1.2.wav");

    private static final Integer SPACE = 32;
    private static final Integer BACKSPACE = 8;
    private static final Integer ENTER = 10;
    private static final Integer LETTER_START = 65;
    private static final Integer LETTER_END = 90;
    private static final Integer NUMBER_START = 48;
    private static final Integer NUMBER_END = 57;
    private static final Integer ARROW_START = 37;
    private static final Integer ARROW_END = 40;
    private final String instructions = "<html> Feel free to start typing whenever. Your typing speed will be " +
            "calculated once all the words have been typed. " +
            "<br>To restart the timer, hold down space and backspace. Have fun!</html>";

    /**
     * Creates an instance of a mechKB, setting up the application GUI
     * and initializing all necessary variables.
     */
    public mechKB() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.getLogger(mechKB.class.getName()).log(Level.SEVERE, null, e);
        }

        countdown.frame = this;
        JFrame frame = new JFrame("TypeWriter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1010, 610);
        frame.getContentPane().setBackground(Color.pink);
        frame.setLayout(null);
        frame.addKeyListener(this);

        typeArea = new JLabel();
        typeArea.setBounds(0, 250, 1000, 250);
        typeArea.setText("<html>");
        typeArea.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        typeArea.setBackground(Color.black);
        typeArea.setForeground(Color.pink);
        typeArea.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        typeArea.setOpaque(true);

        words = new JLabel();
        words.setText("<html>" + wordList + "</html>");
        words.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        words.setBounds(0, 0, 1000, 250);
        words.setBackground(Color.pink);
        words.setForeground(Color.WHITE);
        words.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        words.setOpaque(true);

        description = new JLabel();
        description.setText(instructions);
        description.setBounds(0, 500, 800, 50);
        description.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        description.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        description.setBackground(Color.black);
        description.setForeground(Color.WHITE);
        description.setOpaque(true);

        JLabel timer = countdown.timeLabel;
        timer.setBounds(800, 500, 100, 50);
        timer.setForeground(Color.WHITE);
        timer.setBackground(Color.black);

        resetButton = countdown.resetButton;
        resetButton.setBounds(900, 500, 100, 50);

        JMenuBar menu_main = new JMenuBar();
        JMenu menu_settings = new JMenu("Settings");
        JMenuItem menuItem_mute = new JMenuItem("Mute");
        menuItem_mute.addActionListener(e -> isMuted = !isMuted);
        menu_main.add(menu_settings);
        menu_settings.add(menuItem_mute);

        frame.setJMenuBar(menu_main);
        frame.add(words);
        frame.add(typeArea);
        frame.add(description);
        frame.add(timer);
        frame.add(resetButton);
        frame.setVisible(true);
    }

    /**
     * Invoked when a physical key that maps to an input character is pressed down.
     * Checks whether user keyboard input matches the example text displayed for
     * typing test. If matching, displays the lowercase letter of user's input.
     *
     * @param e An event which indicates that a keystroke occurred in a component
     */
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = Character.toLowerCase(e.getKeyChar());

        if (current_letter <= wordListArray.length - 2 && keyChar == wordListArray[current_letter]) {
            typeArea.setText(typeArea.getText() + keyChar);
            ++current_letter;

            if (current_letter == wordListArray.length - 1) {
                countdown.timer.stop();
                displayWPM();
            }
        }
    }

    /**
     * Calculates user's typing speed in words per minute (WPM), then
     * displays result along with a short feedback message.
     */
    private void displayWPM() {
        int WPM = (current_letter/5) * 60 / (60 - countdown.seconds);
        String wpmMessage = "<html>Your average typing speed is: ";
        description.setText(wpmMessage + WPM + "<br>");
        String feedback = "";

        if (WPM < 35) {
            feedback = "A bit on the slow side, but A+ for effort!  :^)";
        } else if (WPM < 46) {
            feedback = "Most people have a typing speed of 35 to 45 WPM. " +
                    "What a <b>typical</b> result... badum-tss!";
        } else if (WPM < 71) {
            feedback = "Good job! Here's a random joke: I walked into a bar, ouch that hurt :(";
        } else if (WPM < 110) {
            feedback = "Don't mean to stereo-type, but any chance that you're a computer major?";
        } else if (WPM < 161) {
            feedback = "Such speed, much wow. I bet you were real key-bored doing this test  :^)";
        }
        else if (WPM < 216) {
            feedback = "Holy cow! Are you a robot or something!?";
        } else {
            feedback = "You beat the world record for fastest typing speed! Stella Pajunas would be proud of you (and also jealous!)";
        }

        description.setText(description.getText() + feedback);
    }

    /**
     * Clears all associated components and allows user to attempt a new
     * typing test.
     */
    public void restart() {
        keysPressed.clear();
        wordList = wordBank.getRandomWords();
        wordListArray = wordList.toCharArray();
        current_letter = 0;

        typeArea.setText("<html>");
        words.setText("<html>" + wordList + "</html>");
        description.setText(instructions);

        if (Objects.equals(resetButton.getText(), "Reset")) {
            resetButton.doClick();
        }
    }

    /**
     * Invoked when any physical key is pressed down.
     * Plays first half of mechanical keyboard sound when user presses a key,
     * unless Mute option has been selected.
     * If user presses and holds down a key, only one click sound is played.
     * If pressed key maps to an alphabetical letter, test timer is started.
     *
     * Modifies keysPressed to track when space and backspace keys are pressed
     * simultaneously, in which case restart() is invoked.
     *
     * @param e An event which indicates that a keystroke occurred in a component
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (!firstPress || isMuted) {
            if ((keyCode == BACKSPACE && keysPressed.contains(SPACE)) ||
                    (keyCode == SPACE && keysPressed.contains(BACKSPACE))) {
                restart();
            }
            else if (keyCode == BACKSPACE || keyCode == SPACE) {
                keysPressed.add(keyCode);
            }
            else if (isBetween(keyCode, 65, 90) && !countdown.started) {
                resetButton.doClick();
            }
            return;
        }

        // Arrow key sounds
        if (isBetween(keyCode, ARROW_START, ARROW_END)) {
            sound.playClip(arrows_start);
        }

        // Letter key sounds
        else if (isBetween(keyCode, LETTER_START, LETTER_END)) {
            sound.playClip(letters_start);
            if (!countdown.started) {
                resetButton.doClick();
            }
        }

        // Number key sounds
        else if (isBetween(keyCode, NUMBER_START, NUMBER_END)) {
            sound.playClip(numbers_start);
        }

        // Enter key sound
        else if (keyCode == ENTER) {
            sound.playClip(space_enter_start);
        }

        // Space key sound
        else if (keyCode == SPACE) {
            keysPressed.add(SPACE);
            sound.playClip(space_enter_start);
        }

        // Backspace key sound
        else if (keyCode == BACKSPACE) {
            keysPressed.add(BACKSPACE);
            sound.playClip(backspace_start);
        }

        // Default key sound
        else {
            sound.playClip(letters_start);
        }

        firstPress = false;
    }

    /**
     * Invoked when any physical key is released after being pressed down.
     * Plays latter half of mechanical keyboard sound when user releases a key,
     * unless Mute option has been selected.
     *
     * Modifies keysPressed to track if space or backspace keys were released.
     *
     * @param e An event which indicates that a keystroke occurred in a component
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (isMuted) {
            if (keyCode == BACKSPACE || keyCode == SPACE) {
                keysPressed.remove(SPACE);
            }
            return;
        }

        // Arrow key sounds
        if (isBetween(keyCode, ARROW_START, ARROW_END)) {
            sound.playClip(arrows_end);
        }

        // Number key sounds
        else if (isBetween(keyCode, NUMBER_START, NUMBER_END)) {
            sound.playClip(numbers_end);
        }

        // Enter key sound
        else if (keyCode == ENTER) {
            sound.playClip(space_enter_end);
        }

        // Space key sound
        else if (keyCode == SPACE) {
            keysPressed.remove(SPACE);
            sound.playClip(space_enter_end);
        }

        // Backspace key sound
        else if (keyCode == BACKSPACE) {
            keysPressed.remove(BACKSPACE);
            sound.playClip(backspace_end);
        }

        // Default key sound
        else {
            sound.playClip(letters_end);
        }

        firstPress = true;
    }

    /**
     * Checks if an integer is between two integer values
     *
     * @param x integer to check if between lower and upper ranges
     * @param lower integer representing lower bound of range
     * @param upper integer representing upper bound of range
     * @return true if lower <= x <= upper, false otherwise
     */
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
