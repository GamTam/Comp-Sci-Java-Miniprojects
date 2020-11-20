package HandacondaBattle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.Hashtable;

public class Soundtrack extends GameObject{
    private Hashtable<String, Song> soundtrack = new Hashtable<>();

    private boolean fadeOut = false;

    public Soundtrack(String menu, String gameplay, String win, String lose, String secret, Game game) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        super(0, 0, ID.SOUNDTRACK, game);

        soundtrack.put("title", new Song(menu));
        soundtrack.put("secret", new Song(secret));
        soundtrack.put("win", new Song(win));
        soundtrack.put("lose", new Song(lose));
        soundtrack.put("game heavy", new Song(gameplay));
        soundtrack.put("game light", new Song(gameplay + " Thinking"));

        soundtrack.put("mario", new Song("musee"));
        soundtrack.put("luigi", new Song("brobot battle"));
        soundtrack.put("shy guy", new Song("snifit or whiffit"));
        soundtrack.put("fawful", new Song("fawful is there"));
        soundtrack.put("sans", new Song("megalovania"));
        soundtrack.put("starlow", new Song("grand finale"));
    }

    public void play(String song) {
        for (String s : soundtrack.keySet()) {
            soundtrack.get(s).stop();
        }

        soundtrack.get(song).play();
    }

    public void fadeOutAll() {
        for (String s : soundtrack.keySet()) {
            soundtrack.get(s).fadeOut();
        }
    }

    @Override
    public void tick() {
    }

    public void swapHeavy() {
        String song = "";

        for (String s : soundtrack.keySet()) {
            if (soundtrack.get(s).clip.isActive()) {
                song = s;
            }
        }

        if (song.equalsIgnoreCase("game heavy")) {
            soundtrack.get("game light").clip.setFramePosition(soundtrack.get("game heavy").clip.getFramePosition());
            soundtrack.get("game light").fadeIn();
            soundtrack.get("game heavy").fadeOut();
        } else if (song.equalsIgnoreCase("game light")) {
            soundtrack.get("game heavy").clip.setFramePosition(soundtrack.get("game light").clip.getFramePosition());
            soundtrack.get("game heavy").fadeIn();
            soundtrack.get("game light").fadeOut();
        } else if (song.equalsIgnoreCase("")){
            play("game light");
        }
    }

    @Override
    public void render(Graphics g) {
        //
    }
}
