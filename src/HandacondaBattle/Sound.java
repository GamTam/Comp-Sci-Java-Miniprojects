package HandacondaBattle;

import javax.sound.sampled.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Sound {
    // Variable Init
    public String sound;

    public Clip clip;

    public Sound(String sound) {
        this.sound = sound;

        try {
            // Locate file
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(path + "/sounds/" + sound + ".wav");

            // Load and play audio
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = inputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(inputStream);
        } catch (Exception e) {
            System.err.println("Sans Undertale");
        }
    }

    public void play() {
        clip.start();
        int i = 0;
        while (!clip.isActive()) {
            i++;
        }
    }
}
