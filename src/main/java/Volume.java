import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Volume {
    Sound sound = new Sound();
    JSlider slider;
    URL s1, s2;
    JTextArea area;

    public static void main(String[] args) {
        try {
            new Volume();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public Volume() {
//        JFrame window = new JFrame("TypeWriter");
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch(Exception e) {
//            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, e);
//        }
//
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setLayout(new GridLayout(1, 3));
//        window.setSize(640, 480);
//
//        // SLIDER MODIFICATION
//        slider = new JSlider(-40, 6);
//        slider.addChangeListener(e -> {
//            sound.currentVolume = slider.getValue();
//            if (sound.currentVolume == -40) {
//                sound.currentVolume = -80;
//            }
//            System.out.println("Current volume: " + sound.currentVolume);
//            sound.fc.setValue(sound.currentVolume);
//        });
//
//        JButton muteB = new JButton("Mute");
//        muteB.addActionListener(e -> sound.volumeMute(slider));
//        window.add(muteB);
//
//        window.add(slider);
//        window.pack();
//        window.setVisible(true);

        JFrame frame = new JFrame("Text Editor");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, e);
        }

        // Set attributes of the app window
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);
        frame.setVisible(true);

//        s1 = getClass().getResource("Keyboard_Button_1.wav");
//        s2 = getClass().getResource("Keyboard_Button_2.wav");
//        URL s3 = getClass().getResource("Keyboard_Button_3.wav");
//        URL s4 = getClass().getResource("Keyboard_Button_4.wav");
//        URL s5 = getClass().getResource("Keyboard_Button_5.wav");
//        URL s6 = getClass().getResource("Keyboard_Button_6.wav");
//        URL s7 = getClass().getResource("Keyboard_Button_7.wav");
//        URL s8 = getClass().getResource("Keyboard_Button_8.wav");
//        URL s9 = getClass().getResource("Keyboard_Button_9.wav");
//        URL s10 = getClass().getResource("Keyboard_Button_10.wav");

        Scanner scanner = new Scanner(System.in);
        String response;

        while (true) {
            response = scanner.next();
            response = response.toUpperCase(Locale.ROOT);
            String[] responseChar = response.split("");

            for (int i = 0; i < responseChar.length; i++) {
                System.out.println(responseChar[i]);
                switch (response) {
                    case ("A"):
                        playMusic(s1);

                    default:
                        playMusic(s2);
                }
//                    break;
//                case("D"):
//                case("E"):
//                case("F"):
//                    playMusic(s1);
//                case("G"):
//                case("H"):
//                case("I"):
//                    playMusic(s2);
//                case("J"):
//                case("K"):
//                case("L"):
//                    playMusic(s3);
//                case("M"):
//                case("N"):
//                case("O"):
//                    playMusic(s4);
//                case("P"):
//                case("Q"):
//                case("R"):
//                    playMusic(s5);
            }
        }

    }

    //    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        int returnValue = 0;
        if (ae.equals("Open")) {
            returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                try {
                    FileReader read = new FileReader(f);
                    Scanner scan = new Scanner(read);
                    String response;
//                    while(scan.hasNextLine()) {
//                        String line = scan.nextLine() + "\n";
//                        ingest = ingest + line;
//                    }
                    while (scan.hasNext()) {
                        response = scan.next().toUpperCase();

                        switch (response) {
                            case("A"):
                                playMusic(s1);
                            default:
                                playMusic(s2);
                        }
                        ingest = ingest + response;
                    }

                    area.setText(ingest);
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                }
            }
        }
    }

    public void playMusic(URL url) {
//        sound.setFile(url);
        sound.playClip(url);
    }
}
