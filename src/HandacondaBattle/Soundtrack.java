package HandacondaBattle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.Hashtable;

public class Soundtrack extends GameObject{
    private Hashtable<String, Song> soundtrack = new Hashtable<>();

    private boolean heavy = true;

    public Soundtrack(String menu, String gameplay, String win, String lose, String secret, Game game) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        super(0, 0, ID.SOUNDTRACK, game);

        soundtrack.put("title", new Song(menu));
        soundtrack.put("secret", new Song(secret));
        soundtrack.put("win", new Song(win));
        soundtrack.put("lose", new Song(lose));
        soundtrack.put("game", new Song(gameplay));

        soundtrack.put("mario", new Song("musee"));
        soundtrack.put("luigi", new Song("brobot battle"));
        soundtrack.put("shy guy", new Song("snifit or whiffit"));
        soundtrack.put("fawful", new Song("fawful is there"));
        soundtrack.put("fawful 2", new Song("grand finale"));
        soundtrack.put("sans", new Song("megalovania"));
        soundtrack.put("toadette", new Song("where's toad"));
    }

    public void play(String song) {
        for (String s : soundtrack.keySet()) {
            if (!s.equalsIgnoreCase(song)) soundtrack.get(s).stop();
        }

        soundtrack.get(song).play();
    }

    public void randomGameSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        int num = game.random.nextInt(10);

        if (num == 1) {
            soundtrack.put("game", new Song("King Bowser"));
        } else if (num == 2) {
            soundtrack.put("game", new Song("scissors"));
        } else if (num == 3) {
            soundtrack.put("game", new Song("final bowser g1"));
        } else if (num == 4) {
            soundtrack.put("game", new Song("grand finale"));
        } else if (num == 5) {
            soundtrack.put("game", new Song("the fanged fastener"));
        } else if (num == 6) {
            soundtrack.put("game", new Song("ASGORE"));
        } else if (num == 7) {
            soundtrack.put("game", new Song("MEGALOVANIA Orchestra"));
        } else if (num == 8) {
            soundtrack.put("game", new Song("final bowser g2"));
        } else if (num == 9) {
            soundtrack.put("game", new Song("cs miniboss"));
        } else {
            soundtrack.put("game", new Song("Origami King Boss"));
        }
    }

    public void fadeOutAll() {
        for (String s : soundtrack.keySet()) {
            soundtrack.get(s).fadeOut();
        }
    }

    public String getsongPlaying() {
        for (String s : soundtrack.keySet()) {
            if (soundtrack.get(s).clip.isActive()) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        //
    }
}
