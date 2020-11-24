package HandacondaBattle.Scenes;

import HandacondaBattle.*;
import HandacondaBattle.BattleBackgrounds.GrandFinale;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Battle extends Scene {

    BattleChars player;
    BattleChars enemy;
    Sound start = new Sound("fight start");

    int playerChoice;
    int enemyChoice;

    boolean heavy;

    Button rock;
    Button paper;
    Button scissors;

    BattlePhase phase = BattlePhase.Start;

    CardReveal playerCard;
    CardReveal enemyCard;

    WinText winText;

    int timer;
    int step = 0;

    public Battle(Game game, Handler handler, SCENE scene) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        super(game, handler, scene);

        game.loaded = false;

        // Loads all of the objects into the scene
        new GrandFinale(this.game);

        player = new BattleChars(this.game, game.playerChar, true);
        enemy = new BattleChars(this.game, game.opponentChar, false);

        rock = new Button(260, -500, 10, ID.BUTTON, this.game, "/cards/rock", true);
        paper = new Button(game.getWidth() / 2, -500, 10, ID.BUTTON, this.game, "/cards/paper", true);
        scissors = new Button(565, -500, 10, ID.BUTTON, this.game, "/cards/scissors", true);

        playerCard = new CardReveal(208, game.getHeight() + 100, this.game);
        enemyCard = new CardReveal(game.getWidth() - 208, game.getHeight() + 100, this.game);

        this.game.addMouseListener(this);
        game.soundtrack.randomGameSong();
        game.soundtrack.play("game");
        heavy = true;
        start.play();
        game.writeStats();
        game.loaded = true;
    }

    // Checks when the mouse is clicked
    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        int x = e.getX();
        int y = e.getY();

        int yPoint = -200;

        if (key == MouseEvent.BUTTON1) {
            if (rock.mouseOver(x, y)) {
                paper.goToPoint(paper.getX(), yPoint);
                rock.goToPoint(rock.getX(), yPoint);
                scissors.goToPoint(scissors.getX(), yPoint);

                rock.selected = true;
                paper.selected = false;
                scissors.selected = false;
                phase = BattlePhase.Reveal;

                playerChoice = 1;
                enemyChoice = game.random.nextInt(3) + 1;

                timer = 60;
            } else if (paper.mouseOver(x, y)) {
                paper.goToPoint(paper.getX(), yPoint);
                rock.goToPoint(rock.getX(), yPoint);
                scissors.goToPoint(scissors.getX(), yPoint);

                rock.selected = false;
                paper.selected = true;
                scissors.selected = false;
                phase = BattlePhase.Reveal;

                playerChoice = 2;
                enemyChoice = game.random.nextInt(3) + 1;

                timer = 60;
            } else if (scissors.mouseOver(x, y)) {
                paper.goToPoint(paper.getX(), yPoint);
                rock.goToPoint(rock.getX(), yPoint);
                scissors.goToPoint(scissors.getX(), yPoint);

                rock.selected = false;
                paper.selected = false;
                scissors.selected = true;
                phase = BattlePhase.Reveal;

                playerChoice = 3;
                enemyChoice = game.random.nextInt(3) + 1;

                timer = 60;
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        game.mouse.x = -100;
        game.mouse.y = -100;
    }

    public void tick() throws IOException {
        if (start != null) {
            if (!start.clip.isActive()) {
                rock.goToPoint(260, 95);
                paper.goToPoint(game.width / 2, 95);
                scissors.goToPoint(565, 95);
                start = null;
                phase = BattlePhase.Choosing;
            }
        }

        if (timer <= 0) {
            if (phase == BattlePhase.Choosing) {
                if (rock.yDest != 95) {
                    rock.goToPoint(260, 95);
                    paper.goToPoint(game.width / 2, 95);
                    scissors.goToPoint(565, 95);
                }

                step = 0;
            } else if (phase == BattlePhase.Reveal) {
                if (step == 0) {
                    if(playerCard.yDest != 353) {
                        playerCard.goToPoint(playerCard.getX(), 353);
                        enemyCard.goToPoint(enemyCard.getX(), 353);
                    }

                    if (enemyCard.inRange(enemyCard.getY(), 353)) {
                        enemyCard.small = true;
                        playerCard.small = true;

                        if (enemyChoice == 1) {
                            enemyCard.otherSide = enemyCard.rock;
                        } else if (enemyChoice == 2) {
                            enemyCard.otherSide = enemyCard.paper;
                        }  else if (enemyChoice == 3) {
                            enemyCard.otherSide = enemyCard.scissors;
                        } else if (enemyChoice == 0) {
                            enemyChoice += 1;
                            enemyCard.otherSide = enemyCard.rock;
                        }

                        if (playerChoice == 1) {
                            playerCard.otherSide = playerCard.rock;
                        } else if (playerChoice == 2) {
                            playerCard.otherSide = playerCard.paper;
                        }  else if (playerChoice == 3) {
                            playerCard.otherSide = playerCard.scissors;
                        }

                        step += 1;
                        timer = 60;
                    }
                } else if (step == 1) {
                    enemyCard.flipping = true;
                    playerCard.flipping = true;
                    step += 1;
                    timer = 60;
                } else if (step == 2) {
                    Sound sound = new Sound("text appear");
                    sound.play();
                    if (calculateWin() == 1) {
                        winText = new WinText(this.game, "win");
                    } else if (calculateWin() == 0) {
                        winText = new WinText(this.game, "lose");
                    }  else if (calculateWin() == 2) {
                        winText = new WinText(this.game, "draw");
                    }

                    step += 1;
                    timer = 60;
                } else if (step == 3) {
                    enemyCard.flipping = false;
                    playerCard.flipping = false;
                    game.handler.removeObject(winText);
                    playerCard.goToPoint(playerCard.getX(), game.getHeight() + 100);
                    enemyCard.goToPoint(enemyCard.getX(), game.getHeight() + 100);

                    step += 2;
                    timer = 60;
                } else if (step == 5) {
                    if (!(calculateWin() == 2)) {
                        Sound sound = new Sound("hit");
                        sound.play();
                    }

                    if (calculateWin() == 1) {
                        enemy.state = PlayerState.Hit;
                        enemy.hp -= 1;
                    } else if (calculateWin() == 0) {
                        player.state = PlayerState.Hit;
                        player.hp -= 1;
                    }

                    step += 1;
                    timer = 60;
                    if (player.hp <= 0 || enemy.hp <= 0) {
                        game.soundtrack.fadeOutAll();
                        if (player.hp <= 0) {
                            player.state = PlayerState.Dead;
                            enemy.state = PlayerState.Win;
                        } else {
                            player.state = PlayerState.Win;
                            enemy.state = PlayerState.Dead;
                        }
                    }
                } else if (step == 6) {
                    if (player.hp <= 0 || enemy.hp <= 0) {
                        timer = 240;
                        Sound snd;
                        if (player.hp <= 0) {
                            snd = new Sound("defeat");
                            new WinText(this.game, "defeat");
                            game.gamesLost += 1;
                        } else {
                            snd = new Sound("victory");
                            new WinText(this.game, "victory");
                            game.gamesWon += 1;
                        }
                        snd.play();
                        game.writeStats();
                    }

                    step += 1;
                } else if (step == 7) {
                    if (player.hp <= 0 || enemy.hp <= 0) {
                        game.removeMouseListener(this);
                        game.handler.addObject(new SceneTransition(SCENE.MainMenu, game));
                        step += 1;
                        timer = 60;
                    } else {
                        phase = BattlePhase.Choosing;
                        step = 0;
                        playerCard.activeSprite = playerCard.back;
                        enemyCard.activeSprite = enemyCard.back;

                        rock.selected = false;
                        paper.selected = false;
                        scissors.selected = false;
                    }
                }
            }
        } else {
            timer--;
        }
    }

    public int calculateWin() {
        int state = 1;

        if (playerChoice == enemyChoice) {
            state = 2;
        } else if (playerChoice == 1 && enemyChoice == 2) {
            state = 0;
        } else if (playerChoice == 2 && enemyChoice == 3) {
            state = 0;
        } else if (playerChoice == 3 && enemyChoice == 1) {
            state = 0;
        }

        return state;
    }
}
