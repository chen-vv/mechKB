import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFrame extends JFrame implements KeyListener {
    JFrame frame = new JFrame("TypeWriter");
    private final Sound sound = new Sound();

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

    JLabel typeArea;
    JLabel words;
    JLabel timer;
    JButton resetButton;
    Countdown countdown = new Countdown();
    WordBank wordBank = new WordBank();
    String wordList = wordBank.getRandomWords();
    ArrayList<Integer> keysPressed = new ArrayList<>();
    char[] wordListArray = wordList.toCharArray();
    int current_letter = 0;
    int current_word = 0;
    boolean isMuted = false;
    boolean firstPress = true;
    private static final Integer SPACE = 32;
    private static final Integer BACKSPACE = 8;
    private static final Integer ENTER = 10;

    public MyFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, e);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1010, 610);
        frame.getContentPane().setBackground(Color.pink);
        frame.setLayout(null);
        frame.addKeyListener(this);

        typeArea = new JLabel();
        typeArea.setBounds(0, 250, 1000, 250);
        typeArea.setText("<html>");
        typeArea.setFont(new Font("Roboto", Font.PLAIN, 30));
        typeArea.setBackground(Color.black);
        typeArea.setForeground(Color.pink);
        typeArea.setOpaque(true);

        words = new JLabel();
        words.setText("<html>" + wordList + "</html>");
        words.setFont(new Font("Roboto", Font.PLAIN, 30));
        words.setBounds(0, 0, 1000, 250);
        words.setBackground(Color.pink);
        words.setForeground(Color.WHITE);
        JScrollPane scroller = new JScrollPane(words, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        words.setOpaque(true);

        timer = countdown.timeLabel;
        timer.setBounds(800, 500, 100, 50);
        timer.setForeground(Color.WHITE);
        timer.setBackground(Color.black);

        resetButton = countdown.resetButton;
        resetButton.setBounds(900, 500, 100, 50);

        // Build the menu
        JMenuBar menu_main = new JMenuBar();
        JMenu menu_settings = new JMenu("Settings");
        JMenuItem menuItem_mute = new JMenuItem("Mute");
        menuItem_mute.addActionListener(e -> volumeMute());

        menu_main.add(menu_settings);
        menu_settings.add(menuItem_mute);
        frame.setJMenuBar(menu_main);

//        frame.add(scroller);
        frame.add(words);
        frame.add(typeArea);
        frame.add(timer);
        frame.add(resetButton);
        frame.setVisible(true);
    }

    private void volumeMute() {
        isMuted = !isMuted;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();

        if (current_letter < wordListArray.length && keyChar == wordListArray[current_letter]) {
            typeArea.setText(typeArea.getText() + keyChar);
            ++current_letter;

            if (keyChar == ' ') {
                ++current_word;
                System.err.println("word changed: " + current_word);
            }
        }
    }

    private void restart() {
        keysPressed.clear();
        typeArea.setText("<html>");
        current_word = 0;
        current_letter = 0;
        resetButton.doClick();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Invoked when a physical key is pressed down. Uses KeyCode, int output
        if (!firstPress || isMuted) {
            if ((keyCode == BACKSPACE && keysPressed.contains(SPACE)) ||
                    (keyCode == SPACE && keysPressed.contains(BACKSPACE))) {
                restart();
            }
            return;
        }

        // Arrow key sounds
        if (isBetween(keyCode, 37, 40)) {
            sound.playClip(arrows_start);
        }

        // Letter key sounds
        else if (isBetween(keyCode, 65, 90)) {
            sound.playClip(letters_start);
            if (!countdown.started) {
                resetButton.doClick();
            }
        }

        // Number key sounds
        else if (isBetween(keyCode, 48, 57)) {
            sound.playClip(numbers_start);
        }

        // Enter key sound
        else if (keyCode == ENTER) {
            sound.playClip(space_enter_start);
        }

        // Space key sound
        else if (keyCode == SPACE) {
            System.out.println("space pressed: " + keyCode);
            keysPressed.add(SPACE);

            sound.playClip(space_enter_start);

            if (keysPressed.contains(BACKSPACE)) {
//                keysPressed.remove(BACKSPACE);
                System.out.println("space pressed while backspace");
            }
        }

        // Backspace key sound
        else if (keyCode == BACKSPACE) {
            System.out.println("backspace pressed: " + keyCode);
            keysPressed.add(BACKSPACE);
            sound.playClip(backspace_start);

            if (keysPressed.contains(SPACE)) {
//                keysPressed.remove(SPACE);
                System.out.println("backspace pressed while space");
            }
        }

        // Default key sound
        else {
            sound.playClip(letters_start);
        }

        firstPress = false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isMuted) {
            return;
        }

        // Called whenever a button is released
        int keyCode = e.getKeyCode();

        // Arrow key sounds
        if (isBetween(keyCode, 37, 40)) {
            sound.playClip(arrows_end);
        }

//        // Letter key sounds
//        else if (isBetween(keyCode, 65, 90)) {
//            sound.playClip(letters_start);
//        }

        // Number key sounds
        else if (isBetween(keyCode, 48, 57)) {
            sound.playClip(numbers_end);
        }

        // Enter key sound
        else if (keyCode == ENTER) {
            sound.playClip(space_enter_end);
        }

        // Space key sound
        else if (keyCode == SPACE) {
            keysPressed.remove((Integer) SPACE);
            sound.playClip(space_enter_end);
        }

        // Backspace key sound
        else if (keyCode == BACKSPACE) {
            keysPressed.remove((Integer) BACKSPACE);
            sound.playClip(backspace_end);
        }

        // Default key sound
        else {
            sound.playClip(letters_end);
        }

        firstPress = true;
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

//    public String deleteLastChar(String str) {
//        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
//            str = str.substring(0, str.length() - 1);
//        }
//        return str;
//    }
}
