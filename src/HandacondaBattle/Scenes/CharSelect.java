package HandacondaBattle.Scenes;

import HandacondaBattle.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.MouseEvent;
import java.io.IOException;

// The Character select screen
public class CharSelect extends Scene {
    Button random;
    Button mario;
    Button luigi;
    Button fawful;
    Button toadette;
    Button sans;
    Button shyGuy;

    Button back;

    Button easy;
    Button normal;
    Button hard;

    Button start;

    Button playerChar = null;
    Button difficulty = null;

    public CharSelect(Game game, Handler handler, SCENE scene) throws IOException {
        super(game, handler, scene);
        Sound snd = new Sound("choosefighter");

        game.loaded = false;



        // Loads all of the objects into the scene
        new BG(this.game, "charselect bg", true, true);

        random =   new Button(400, 196, 0, ID.BUTTON, this.game, "charselect/random", true);

        mario =    new Button(185, 91, 0, ID.BUTTON, this.game, "charselect/mario", true);
        luigi =    new Button(400, 91, 0, ID.BUTTON, this.game, "charselect/luigi", true);
        fawful =   new Button(615, 91, 0, ID.BUTTON, this.game, "charselect/fawful", true);

        toadette = new Button(185, 301, 0, ID.BUTTON, this.game, "charselect/toadette", true);
        sans =     new Button(400, 301, 0, ID.BUTTON, this.game, "charselect/sans", true);
        shyGuy =   new Button(615, 301, 0, ID.BUTTON, this.game, "charselect/shy guy", true);

        back =     new Button(127, 565, 0, ID.BUTTON, this.game, "charselect/back", true);

        easy = new Button(616, 425, 0, ID.BUTTON, this.game, "charselect/easy", true);
        normal = new Button(616, 491, 0, ID.BUTTON, this.game, "charselect/normal", true);
        hard = new Button(616, 557, 0, ID.BUTTON, this.game, "charselect/hard", true);

        start = new Button(-125, 443, 10, ID.BUTTON, this.game, "charselect/begin", true);

        this.game.addMouseListener(this);
        game.loaded = true;
        snd.play();
    }

    // Checks when the mouse is clicked
    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        int x = e.getX();
        int y = e.getY();

        if (key == MouseEvent.BUTTON1) {
            if (playerChar != null) {
                playerChar.selected = false;
            }

            if (difficulty != null) {
                difficulty.selected = false;
            }

            if (back.mouseOver(x, y)) {
                this.game.removeMouseListener(this);
                game.handler.addObject(new SceneTransition(SCENE.MainMenu, game));
            } else if (mario.mouseOver(x, y)) {
                playerChar = mario;
            } else if (luigi.mouseOver(x, y)) {
                playerChar = luigi;
            } else if (fawful.mouseOver(x, y)) {
                playerChar = fawful;
            } else if (toadette.mouseOver(x, y)) {
                playerChar = toadette;
            } else if (sans.mouseOver(x, y)) {
                playerChar = sans;
            } else if (shyGuy.mouseOver(x, y)) {
                playerChar = shyGuy;
            } else if (random.mouseOver(x, y)) {
                playerChar = random;
            } else if (easy.mouseOver(x, y)) {
                difficulty = easy;
            } else if (normal.mouseOver(x, y)) {
                difficulty = normal;
            } else if (hard.mouseOver(x, y)) {
                difficulty = hard;
            } else if (start != null) {
                if (start.mouseOver(x, y)) {
                    game.difficulty = difficulty.name;

                    if (playerChar.name.equalsIgnoreCase("random")) {
                        int num = game.random.nextInt(6);

                        if (num == 1) {
                            game.playerChar = "Mario";
                            game.marioTimes += 1;
                        } else if (num == 2) {
                            game.playerChar = "Luigi";
                            game.luigiTimes += 1;
                        } else if (num == 3) {
                            game.playerChar = "Fawful";
                            game.fawfulTimes += 1;
                        } else if (num == 4) {
                            game.playerChar = "Shy Guy";
                            game.shyGuyTimes += 1;
                        } else if (num == 5) {
                            game.playerChar = "Sans";
                            game.sansTimes += 1;
                        } else {
                            game.playerChar = "Toadette";
                            game.toadetteTimes += 1;
                        }
                    } else {
                        game.playerChar = playerChar.name;
                    }

                    if (playerChar.name.equalsIgnoreCase("mario")) {
                        game.marioTimes += 1;
                    } else if (playerChar.name.equalsIgnoreCase("luigi")) {
                        game.luigiTimes += 1;
                    } else if (playerChar.name.equalsIgnoreCase("fawful")) {
                        game.fawfulTimes += 1;
                    } else if (playerChar.name.equalsIgnoreCase("shy guy")) {
                        game.shyGuyTimes += 1;
                    } else if (playerChar.name.equalsIgnoreCase("sans")) {
                        game.sansTimes += 1;
                    } else if (playerChar.name.equalsIgnoreCase("toadette")) {
                        game.toadetteTimes += 1;
                    }

                    if (difficulty.name.equalsIgnoreCase("easy")) {
                        game.easyTimes += 1;
                    } else if (difficulty.name.equalsIgnoreCase("normal")) {
                        game.normalTimes += 1;
                    } else if (difficulty.name.equalsIgnoreCase("hard")) {
                        game.hardTimes += 1;
                    }

                    game.games += 1;

                    this.game.removeMouseListener(this);
                    game.soundtrack.fadeOutAll();
                    game.handler.addObject(new SceneTransition(SCENE.Game, game));
                }
            }
        }

        if (playerChar != null) {
            playerChar.selected = true;
        }

        if (difficulty != null) {
            difficulty.selected = true;
        }

        if (playerChar != null && difficulty != null) {
            assert start != null;
            start.goToPoint(189, 443);
        }
    }

    public void mouseExited(MouseEvent e) {
        game.mouse.x = -100;
        game.mouse.y = -100;
    }
}
