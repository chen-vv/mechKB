import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Countdown implements ActionListener {
    private static final int MILLIS = 1000;
    JButton resetButton = new JButton("Start");
    JLabel timeLabel = new JLabel();

//    JLabel words_fromFrame = null;
//    JLabel typeArea_fromFrame = null;
//    JLabel description_fromFrame = null;
//    WordBank wordbank_fromFrame = null;
//    ArrayList<Integer> keysPressed_fromFrame = null;
    mechKB frame = null;

    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 1;
    boolean started = false;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%1d", minutes);
    Timer timer = new Timer(1000, e -> {
        elapsedTime += MILLIS;

        if (elapsedTime > 0 && elapsedTime < 60 * MILLIS) {
            seconds = 60 - (elapsedTime / MILLIS);
            if (minutes == 1) {
                --minutes;
            }
        } else {
            seconds = 0;
        }

        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%1d", minutes);
        timeLabel.setText(minutes_string + ":" + seconds_string);
    });

    Countdown() {
        timeLabel.setText(minutes_string + ":" + seconds_string);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        resetButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.pink);
        resetButton.setContentAreaFilled(false);
        resetButton.setOpaque(true);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            if (started) {
                started = false;
                resetButton.setText("Start");
                reset();
                frame.restart();
            }
            else {
                started = true;
                resetButton.setText("Reset");
                start();
            }
        }
    }

    void start() {
        timer.start();
    }

    void reset() {
        timer.stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 1;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%1d", minutes);
        timeLabel.setText(minutes_string + ":" + seconds_string);
    }
}
