package HandacondaBattle;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

class Song implements Runnable {
    // Variable Init
    public String name;
    public int loopStart = 0;
    public int loopEnd = -1;

    private int targetGain = 1;

    public Clip clip;
    public boolean running = false;

    public Song (String newSong) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
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

            if (i > 0 && data[0].equalsIgnoreCase(newSong)) {
                name = data[0];
                loopStart = Integer.parseInt(data[1]);
                loopEnd = Integer.parseInt(data[2]);
                keepLooping = false;
            }

            i++;
        }

        file = new File(path + "/songs/" + name + ".wav");

        // Load and play audio
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = inputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip)AudioSystem.getLine(info);
        clip.open(inputStream);
        clip.setLoopPoints(loopStart, loopEnd);
    }

    // Plays song
    public void play() {

        try {
            clip.loop(-1);
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

    public void fadeOut() {
        if (!running) {
            targetGain = -50;
            Thread t = new Thread(this);
            System.out.println(t);
            t.start();
        }
    }

    public void fadeIn() {
        if (!running) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10);
            try {
                clip.loop(-1);
            } catch (Exception e) {
                System.err.println(e);
            }
            targetGain = 0;
            Thread t = new Thread(this);
            System.out.println(t);
            t.start();
        }
    }

    public void run() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (gainControl.getValue() > targetGain) {
            while (gainControl.getValue() > targetGain) {
                gainControl.setValue(gainControl.getValue() - 0.2f);
                System.out.println(gainControl.getValue());
                try {Thread.sleep(10);} catch (Exception e) {}
            }

            stop();
        } else if (gainControl.getValue() < targetGain) {
            while (gainControl.getValue() < targetGain) {
                gainControl.setValue(gainControl.getValue() + 0.2f);
                System.out.println(gainControl.getValue());
                try {Thread.sleep(10);} catch (Exception e) {}
            }
            gainControl.setValue(targetGain);
        }

        running = false;
    }
}