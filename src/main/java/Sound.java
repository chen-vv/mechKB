import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.net.URL;

public class Sound {
    Clip clip;
    float previousVolume = 0;
    float currentVolume = -17;
    FloatControl fc;
    boolean mute = false;

//    public void setFile(URL url) {
//        try {
//            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
//            clip = AudioSystem.getClip();
//            clip.open(sound);
//            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//    }

    public void playClip(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

//    public void loop() {
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
//    }
//
//    public void stop() {
//        clip.stop();
//    }
//
//    public void volumeUp() {
//        currentVolume += 1.0f;
//        if (currentVolume > 6.0f) {
//            currentVolume = 6.0f;
//        }
//        System.out.println("Current Volume: " + currentVolume);
//        fc.setValue(currentVolume);
//    }
//
//    public void volumeDown() {
//        currentVolume -= 1.0f;
//        if (currentVolume < -80.0f) {
//            currentVolume = -80.0f;
//        }
//        System.out.println("Current Volume: " + currentVolume);
//        fc.setValue(currentVolume);
//    }

    public void volumeMute() {
        if (!mute) {
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            System.out.println("Current Volume: " + currentVolume);
            fc.setValue(currentVolume);
            mute = true;

//            slider.setValue(slider.getMinimum());
        }
        else {
            currentVolume = previousVolume;
//            slider.setValue((int) currentVolume);
            System.out.println("Current Volume: " + currentVolume);
            fc.setValue(currentVolume);
            mute = false;
        }
    }
}
