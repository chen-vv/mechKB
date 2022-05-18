import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    /**
     * Plays an audio clip from the start
     * @param url the URL of the audio clip to be played, should be located
     *            in the Resources folder
     */
    public void playClip(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error has occurred");
        }
    }
}
