import com.sun.tools.javac.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class musicPlayer {
    public static void main(String[] args) {
        playSound();
    }

    public static synchronized void playSound() {
        try {
            Scanner scanner = new Scanner(System.in);
            String response = "";
            String filename = "C:\\Users\\Vicky Chen\\IdeaProjects\\KeyboardSounds\\src\\main\\resources\\Intellect.wav";
            File file = new File(filename);

            if (file.exists()) {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.start();

                while (!response.equals("Q")) {
                    System.out.println("P = Play, S = Stop, R = Reset, Q = Quit");
                    System.out.println("Enter your choice: ");
                    response = scanner.next();
                    response = response.toUpperCase();

                    switch (response) {
                        case ("P"):
                            clip.start();
                            break;
                        case ("S"):
                            clip.stop();
                            break;
                        case("R"):
                            clip.setMicrosecondPosition(0);
                            break;
                        case("Q"):
                            clip.close();
                            break;
                        default:
                            System.out.println("Not a valid response");
                    }
                }
                System.out.println("Goodbye!");
            } else {
                throw new RuntimeException("Sound file not found: " + filename);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
