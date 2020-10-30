// Dev: Lucas DaCambra
// Course Code: ICS4UI-02
// Date Written: 10/10/2020
// Description: The program plays hangman with the user.

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Random;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Hangman {
    static Soundtrack soundtrack = new Soundtrack();
    static Scanner scan = new Scanner(System.in);
    static List<String> bannedChars = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        soundtrack.setDefaults();
        soundtrack.addGuessSong("somnom woods");
        soundtrack.addGuessSong("grand finale");
        soundtrack.addGuessSong("cornered");
        soundtrack.addGuessSong("diggy diggy hole");
        soundtrack.addGuessSong("pigstep");

        bannedChars.add(" ");
        bannedChars.add("-");
        bannedChars.add(":");
        bannedChars.add("+");

        soundtrack.menu.play();

        // Dialogue
        textBox("Hello, and welcome to Hangman, Inc!\n", true);
        wait(0.5);

        textBox("Are you playing alone, or are you playing with friends?\n", true);

        wait(0.25);
        System.out.println("[1] Alone");
        wait(0.5);
        System.out.println("[2] With Friends\n");
        wait(0.5);

        int playerAmounts = selectInt(1, 2);
        String guessWord;
        Sound ding = new Sound();
        ding.sound = "ding";

        if (playerAmounts == 1) {
            textBox("Generating word", false);
            wait(0.25);
            textBox(".", false);
            wait(0.25);
            textBox(".", false);
            wait(0.25);
            textBox(".", true);
            wait(1.0);
            guessWord = getWordFromTxt();
            textBox("Done!\n", true);
        } else {
            textBox("Everyone other than the one who's coming up with the word\nmust look away from the screen at this point.", true);
            wait(0.25);
            textBox("When you hear a ding, it it safe to turn around.\n", true);
            guessWord = selectWord(false);
            textBox("Now do not touch the program until the ding.", true);
            cls();
            ding.play();
            wait(1.0);
        }

        Random rand = new Random();
        int randInt = rand.nextInt(soundtrack.guessSongs.size() - 1);

        textBox("Press Enter to continue", false);
        scan = new Scanner(System.in);
        scan.nextLine();
        System.out.println();

        soundtrack.stop();


        textBox("If at any point you submit more than one character as your guess,\nit will be assumed to be your final answer.", true);
        textBox("If your final answer is incorrect, then you lose the game, regardless\nof how many lives you have remaining.", true);
        wait(1.0);
        textBox("Now, good luck!\nYou have 5 incorrect guesses in total.\n", true);
        wait(0.5);

        textBox("Press Enter to continue", false);

        scan = new Scanner(System.in);
        scan.nextLine();
        System.out.println();

        soundtrack.guessSongs.get(randInt).play();

        guessLoop(guessWord);

        textBox("\nThank you for playing with Hangman, Inc!", true);
        textBox("Press enter to close the program.", false);
        scan.nextLine();
    }

    public static String getWordFromTxt() throws IOException {
        String word = "";

        // Locate file
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        File file = new File(path + "/GuessWords/GuessWords.txt");

        String line = "";

        int i = 0;
        boolean found = false;

        Random rand = new Random();

        // Loops through text file and assigns random word
        while (!found) {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                int randInt = rand.nextInt(400000);

                if (randInt == 500) {
                    word = line;
                    found = true;
                }

                if (found) {
                    break;
                }
            }
        }

        return word;
    }

    public static void cls() {
        for(int i = 0; i < 2000000; i++) {
            System.out.println();
        }
    }

    public static void clsf() {
        for(int i = 0; i <= 5723974; i++) {
            System.out.println("Simulating... " + i + "/5723974");
        }
    }

    public static void wait(Double seconds) throws InterruptedException {
        Thread.sleep(Math.round(seconds * 1000));
    }

    // Loops through text in string and prints one character at a time
    public static void textBox(String text, int time, boolean newLine) throws InterruptedException {
        for (int i = 0; i < text.length() - 1; i++) {
            Thread.sleep(time);
            System.out.print(text.charAt(i));
        }

        Thread.sleep(time);
        if (newLine) {
            System.out.println(text.charAt(text.length() - 1));
        } else {
            System.out.print(text.charAt(text.length() - 1));
        }
        Thread.sleep(500);
    }

    public static void textBox(String text, boolean newLine) throws InterruptedException {
        textBox(text, 25, newLine);
    }

    // Continuously loops until an integer within the selected range is chosen by the user
    public static int selectInt(int min, int max) throws InterruptedException {
        int num = -17;

        while (true) {

            textBox("Enter Number Here: ", false);
            try {
                num = scan.nextInt();

                if (num >= min && num <= max) {
                    break;
                } else {
                    textBox("Sorry, that is not a valid option.", true);
                }

            } catch (InputMismatchException e) {
                textBox("Sorry, that is not a valid option.", true);
                scan.next();
            }
        }

        return num;
    }

    // Continuously loops until a string is chosen by the user
    public static String selectWord(boolean guess) throws InterruptedException {
        String str = "";
        scan = new Scanner(System.in);

        while (true) {

            if (!guess) {
                textBox("Enter word here: ", false);
            } else {
                textBox("Enter letter here: ", false);
            }

            try {
                str = scan.nextLine();

                boolean allowed = true;

                if (str.length() == 1) {
                    for (String allowedChar : bannedChars) {
                        if (Character.toLowerCase(str.charAt(0)) == Character.toLowerCase(allowedChar.charAt(0))) {
                            allowed = false;
                            break;
                        }
                    }
                }

                if (allowed && str.length() >= 1) {
                    if (guess) {
                        bannedChars.add(Character.toString(Character.toLowerCase(str.charAt(0))));
                    }
                    break;
                }

                if (guess) {
                    textBox("Sorry, that is not a valid option or has previously been guessed.", true);
                } else {
                    textBox("Sorry, that is not a valid option.", true);
                }
            } catch (InputMismatchException e) {
                textBox("Sorry, that is not a valid option.", true);
                scan.next();
            }
        }

        return str;
    }

    public static void guessLoop(String word) throws InterruptedException {
        String blankWord = "";
        String guessedChar = "";
        int lives = 5;
        boolean finished = false;
        boolean correct = false;

        // Set up blank string
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ' ') {
                blankWord = blankWord + " ";
            } else if (word.charAt(i) == '-') {
                blankWord = blankWord + "-";
            } else if (word.charAt(i) == ':') {
                blankWord = blankWord + ":";
            } else if (word.charAt(i) == '+') {
                    blankWord = blankWord + "+";
            } else {
                blankWord = blankWord + "_";
            }
        }

        textBox("You have " + lives + " incorrect guesses remaining.\n", true);

        while (!finished) {
            // Remind player of word
            System.out.println("\n");
            textBox(blankWord, true);

            // Get guess
            String backupChar = blankWord;
            guessedChar = selectWord(true);

            // Check if char is valid
            if (guessedChar.length() == 1) {
                for (int i = 0; i < word.length(); i++) {
                    if (Character.toLowerCase(guessedChar.charAt(0)) == Character.toLowerCase(word.charAt(i))) {
                        blankWord = blankWord.substring(0, i) + word.charAt(i) + blankWord.substring(i + 1);
                    }
                }

                if (blankWord.equalsIgnoreCase(backupChar)) {
                    lives -= 1;
                    textBox("You have " + lives + " incorrect guesses remaining.\n", true);

                    if (lives <= 0) {
                        soundtrack.stop();
                        soundtrack.defeat.play();

                        textBox("\nYou are out of guesses.", true);
                        textBox("The correct answer was " + "\"" + word + "\".", true);

                        finished = true;
                        correct = false;
                    }
                }

                if (blankWord.equalsIgnoreCase(word)) {
                    finished = true;
                    correct = true;

                    soundtrack.stop();
                    soundtrack.victory.play();

                    textBox("You are correct!", true);
                    textBox("The answer was " + "\"" + word + "\"!", true);
                }
            } else {

                // Check if guess is correct
                if (guessedChar.equalsIgnoreCase(word)) {
                    finished = true;
                    correct = true;

                    soundtrack.stop();
                    soundtrack.victory.play();

                    textBox("You are correct!", true);
                    textBox("The answer was " + "\"" + word + "\"!", true);
                } else {
                    soundtrack.stop();
                    soundtrack.defeat.play();

                    textBox("\nThat was incorrect.", true);
                    textBox("The correct answer was " + "\"" + word + "\".", true);

                    finished = true;
                    correct = false;
                }
            }
        }
    }
}

class Song {
    // Variable Init
    public String name;

    public int loopAmount = -1;
    public int loopStart = 0;
    public int loopEnd = -1;

    public Clip clip;

    public void update(String newSong) throws IOException {
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
    }

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
            clip.loop(loopAmount);

            long i = 0;

            while (!clip.isActive()) {
                i++;
            }
        } catch (Exception e) {
            System.err.println("Sans Undertale");
        }
    }

    public void stop() {
        if (clip != null) {
            if (clip.isActive()) {
                clip.stop();
            }
        }
    }
}

class Soundtrack {
    Song menu = new Song();
    Song guessFour = new Song();
    Song guessOne = new Song();
    Song guessTwo = new Song();
    Song guessThree = new Song();
    Song victory = new Song();
    Song defeat = new Song();

    List<Song> guessSongs = new ArrayList<>();


    void setDefaults() throws IOException {
        menu.update("musee");
        guessOne.update("rose battle");
        guessTwo.update("fortune island");
        guessThree.update("temple of shrooms");
        guessFour.update("fawful castle");
        victory.update("victory ss");
        defeat.update("darkness falls");

        guessSongs.add(guessOne);
        guessSongs.add(guessTwo);
        guessSongs.add(guessThree);
        guessSongs.add(guessFour);
    }

    void addGuessSong(String song) throws IOException {
        Song sng = new Song();
        sng.update(song);

        guessSongs.add(sng);
    }

    void stop() {
        menu.stop();
        guessOne.stop();
        guessTwo.stop();
        guessThree.stop();
        guessFour.stop();
        victory.stop();
        defeat.stop();

        for (Song stop: guessSongs) {
            stop.stop();
        }
    }
}

class Sound {
    // Variable Init
    static String sound;

    static Clip clip;

    public static void play() {
        if (clip != null) {
            if (clip.isActive()) {
                clip.stop();
            }
        }

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
            clip.start();

            long i = 0;

            while (!clip.isActive()) {
                i++;
            }
        } catch (Exception e) {
            System.err.println("Sans Undertale");
        }
    }
}
