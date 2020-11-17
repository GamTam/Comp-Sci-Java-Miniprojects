package HandacondaBattle;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

// A single looping song
class Song {
    // Variable Init
    public String name;
    public int loopStart = 0;
    public int loopEnd = -1;

    public Clip clip;

    public Song(String name) throws IOException {
        this.name = name;

        // Locate file
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        File file = new File(path + "/songData.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String oneData = "";
        int i = 0;
        boolean keepLooping = true;

        // Loops through CSV and adds data to food class
        while ((oneData = br.readLine()) != null && keepLooping) {
            String[] data = oneData.split(",");

            if (i > 0 && data[0].equalsIgnoreCase(name)) {
                name = data[0];
                loopStart = Integer.parseInt(data[1]);
                loopEnd = Integer.parseInt(data[2]);
                keepLooping = false;
            }

            i++;
        }
    }

    // Plays song
    public void play() {
        if (clip != null) {
            if (clip.isActive()) {
                clip.stop();
            }
        }

        try {
            // Locate file
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(path + "/songs/" + name + ".wav");

            // Load and play audio
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = inputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(inputStream);
            clip.setLoopPoints(loopStart, loopEnd);
            clip.loop(-1);

            long i = 0;

            while (!clip.isActive()) {
                i++;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Stops song
    public void stop() {
        if (clip != null) {
            if (clip.isActive()) {
                clip.stop();
            }
        }
    }
}
