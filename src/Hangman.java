import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Hangman {
    static Song song = new Song();
    static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Now playing:\nCornered | Phoenix Wright: Ace Attorney\n");
        song.song = "cornered";
        song.loopStart = 287687;
        song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        System.out.println("Now playing:\nThe Grand Finale | Mario & Luigi: Bowser's Inside Story\n");
        song.song = "grand finale";
        song.loopStart = 407547;
        song.loopEnd = 4070802;
        song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        System.out.println("Now playing:\nThe Final Antasma Battle | Mario & Luigi: Dream Team\n");
        song.song = "antasma fight";
        song.loopStart = 6731;
        song.loopEnd = 3574966;
        song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        System.out.println("Now playing:\nBattle! (Gym Leader) | Pokemon Sword/Shield\n");
        song.song = "swsh gym";
        song.loopStart = 759108;
        song.loopEnd = 4161508;
        Song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        /* System.out.println("Now playing:\nMegalovania | UNDERTALE\n");
        song.song = "megalovania";
        Song.playSong(); */

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        System.out.println("Now playing:\nMain Theme | Will You Snail\n");
        song.song = "Will you snail theme";
        Song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        System.out.println("Now playing:\nDiggy Diggy Hole | Yogscast\n");
        song.song = "diggy diggy hole";
        song.loopStart = 830720;
        song.loopEnd = 9901440;
        Song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");

        System.out.println("Now playing:\nSnif City | Paper Mario: The Origami King\n");
        song.song = "Snif City";
        song.loopStart = 70081;
        song.loopEnd = 3910172;
        Song.playSong();

        System.out.print("For next song, press enter.");
        scan.nextLine();
        song.stopSong();
        System.out.print("\n\n");
    }

}

class Song {
    // Variable Init
    static String song;

    static int loopAmount = -1;
    static int loopStart = 0;
    static int loopEnd = -1;

    static Clip clip;

    public static void playSong() {
        if (clip != null) {
            if (clip.isActive()) {
                clip.stop();
            }
        }

        try {
            // Locate file
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(path + "/songs/" + song + ".wav");

            // Load and play audio
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = inputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(inputStream);
            clip.setLoopPoints(loopStart, loopEnd);
            clip.loop(loopAmount);

            long i = 0;

            while (!clip.isActive()) {
                i++;
            }
        } catch (Exception e) {
            System.err.println("Sans Undertale");
        }
    }

    public static void stopSong() {
        if (clip.isActive()) {
            clip.stop();
            loopAmount = -1;
            loopStart = 0;
            loopEnd = -1;
        }
    }
}
