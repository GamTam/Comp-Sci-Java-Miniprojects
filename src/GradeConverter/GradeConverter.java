/*
Dev: Lucas DaCambra
Course Code: ICS4UI-02
Date Written: 11/8/2020
Description: The program manages the grades for multiple people
 */

package GradeConverter;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class GradeConverter {
    static Scanner scan = new Scanner(System.in);
    static Hashtable<Integer, List<String>> grades = new Hashtable<>();
    static Sound textSound = new Sound();

    public static void main(String[] args) throws InterruptedException, IOException {
        textSound.sound = "textbox blip";
        textBox("\nWelcome to Grade Tracker!", true);
        textBox("This application is designed to help you with knowing what your grades are!\n", true);
        wait(0.5);
        boolean done = false;
        while (!done) {
            textBox("Please select someone for grade management:\n", true);

            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(path + "/Names");

            if (!file.exists()) {
                file.mkdirs();
            }

            File person = fileSelect(file, "person", "");

            textBox("Now, choose which subject you wish to grade them in.\n", true);
            File gradeCategory = fileSelect(person, "subject", ".csv");

            textBox("Now, choose to add a new assessment or modify previous assessments.\n", true);
            grades = getCSV(gradeCategory);
            int grade = selectInt(1, grades.size() + 1);

            if (grade <= grades.size()) {
                textBox("What is the new grade for " + grades.get(grade).get(0) + "? ", true);
                int newMark = selectInt(0, 100);
                List list = new ArrayList();
                list.add(grades.get(grade).get(0));
                list.add(Integer.toString(newMark));
                grades.put(grade, list);
            } else {
                textBox("Enter the title of the assessment: ", false);
                String name = selectWord();
                textBox("And what is the grade for " + name + "? ", true);

                int newMark = selectInt(0, 200);
                List list = new ArrayList();
                list.add(name);
                list.add(Integer.toString(newMark));
                grades.put(grade, list);
            }

            updateGrades(gradeCategory, grades);

            textBox("\nThank you for using the Grade Converter! The file is available at:\n ", true);
            System.out.println(gradeCategory);
            wait(1.0);

            textBox("\nDo you want to see the full chart?", true);
            textBox("[1] Yes", true);
            textBox("[2] No", true);

            int cont = selectInt(1, 2);

            boolean show = false;
            if (cont == 1) {
                show = true;
            }

            if (show) {
                int length = 16;
                for (int i = 1; i < grades.size() + 1; i++) {
                    int tempLength = grades.get(i).get(0).length();
                    if (tempLength > length) {
                        length = tempLength;
                    }
                }

                length += 20;

                textBox(String.format("\n%2$-" + length + "s %3$-20s %4$-15s", " ", "Assesment Title", "Number Grade", "Letter Grade"), 25, true);
                textBox(String.format("%2$-" + length + "s %3$-20s %4$-15s", " ", "---------------", "------------", "------------"), 25, true);
                for (int i = 1; i < grades.size() + 1; i++) {
                    textBox(String.format("%2$-" + length + "s %3$-20s %4$-15s", " ", grades.get(i).get(0), grades.get(i).get(1), toLetter(grades.get(i).get(1))), 25, true);
                }
            }

            textBox("\nDo you wish to modify/add more people?", true);
            textBox("[1] Yes", true);
            textBox("[2] No", true);

            cont = selectInt(1, 2);

            if (cont == 2) {
                done = true;
            }
        }

        textBox("Goodbye!", false);
    }

    /*pre: Storage file, Grades dictionary
    * post: The csv file will have updated to include the data in the dictionary */
    public static void updateGrades(File file, Hashtable<Integer, List<String>> grades) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write("Assessment,Number Grade,Letter Grade\n");
        for (int i = 1; i < grades.size() + 1; i++) {
            String letter = toLetter(grades.get(i).get(1));
            String str = grades.get(i).get(0) + "," + grades.get(i).get(1) + "," + letter + "\n";
            writer.write(str);
        }
        writer.close();
    }

    /*pre: integer
    * post: the method returns a string based on the value of the input */
    public static String toLetter(String input) {
        int num = Integer.parseInt(input);

        if (num >= 95) {
            return "A+";
        } else if (num >= 90) {
            return "A";
        } else if (num >= 85) {
            return "A-";
        } else if (num >= 80) {
            return "B";
        } else if (num >= 70) {
            return "C";
        } else if (num >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    /*pre: csv file
    * post: adds all data from the csv file into a dictionary and returns the dictionary */
    public static Hashtable<Integer, List<String>> getCSV (File file) throws IOException, InterruptedException {
        Hashtable<Integer, List<String>> table = new Hashtable<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String oneData = "";
        int i = 0;

        // Loops through CSV and adds data to food class
        while ((oneData = br.readLine()) != null) {
            String[] data = oneData.split(",");

            if (i > 0) {
                List list = new ArrayList();
                list.add(data[0]);
                list.add(data[1]);
                table.put(i, list);

                textBox("[" + i + "]: " + data[0], true);
            }

            i++;
        }
        textBox("[" + i + "]: New Assessment", true);

        return table;
    }

    /*pre: file location, the category of the file, the filetype
    * post: returns a pre-existing or new file based on the inputs */
    public static File fileSelect(File folder, String type, String fileType) throws InterruptedException, IOException {
        int i = 1;
        Hashtable<Integer, File> people = new Hashtable<>();

        for (File file : folder.listFiles()) {
            if (file.isDirectory() && fileType.equalsIgnoreCase("")) {
                textBox("[" + i + "]: " + file.getName(), true);
                people.put(i - 1, file);
                i++;
            } else if (!file.isDirectory() && !fileType.equalsIgnoreCase("")){
                String name = file.getName();
                String nameFileType = name.substring(name.length() - 4);
                if (nameFileType.equalsIgnoreCase(".csv")) {
                    name = name.replace(nameFileType, "");
                    textBox("[" + i + "]: " + name, true);
                    people.put(i - 1, file);
                    i++;
                }
            }
        }

        textBox("[" + i + "]: New " + type + "\n", true);

        boolean nameFound = false;
        while (!nameFound) {
            int selection;
            selection = selectInt(1, i);

            if (selection == i) {
                if (fileType.equalsIgnoreCase("")) {
                    textBox("Please enter the name of the " + type + ": ", true);
                    String name = selectWord();
                    File newFile = new File(folder + "/" + name);
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                        return (newFile);
                    } else {
                        textBox("That " + type + " is already in the database.", true);
                    }
                } else {
                    textBox("Please enter the name of the " + type + ": ", true);
                    String name = selectWord();
                    File newFile = new File(folder + "/" + name + fileType);
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                        FileWriter writer = new FileWriter(newFile);
                        writer.write("Assessment,Number Grade,Letter Grade");
                        writer.close();
                        return (newFile);
                    } else {
                        textBox("That " + type + " is already in the database.", true);
                    }
                }
            } else {
                return people.get(selection - 1);
            }
        }

        return (people.get(0));
    }

    /*pre: any string, the space between each letter appearing, weather or not there is a new line at the end
    * post: the text will have been displayed to the screen one character at a time */
    public static void textBox(String text, int time, boolean newLine) throws InterruptedException {
        for (int i = 0; i < text.length() - 1; i++) {
            Thread.sleep(time);
            textSound.play();
            System.out.print(text.charAt(i));
        }

        Thread.sleep(time);
        textSound.play();
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

    /*pre: the time to wait
    * post: the program will have stopped for the specified amount of time */
    public static void wait(Double seconds) throws InterruptedException {
        Thread.sleep(Math.round(seconds * 1000));
    }

    /*pre: minimum and maximum selection range
    * post: returns the user selected number */
    public static int selectInt(int min, int max) throws InterruptedException {
        int num = -17;
        scan = new Scanner(System.in);

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

    /*pre: none
     * post: returns the user selected string */
    public static String selectWord() throws InterruptedException {
        String str = "";
        scan = new Scanner(System.in);

        while (true) {
            try {
                boolean allowed = true;
                str = scan.nextLine();

                for (int i=0; i < str.length(); i++) {
                    if (str.charAt(i) == '/' || str.charAt(i) == '\\') {
                        allowed = false;
                    }
                }

                if (allowed) {
                    break;
                }

                textBox("Sorry, that is not a valid option.", true);
            } catch (InputMismatchException e) {
                textBox("Sorry, that is not a valid option.", true);
                scan.next();
            }
        }

        return str;
    }
}

// A sound
class Sound {
    // Variable Init
    static String sound;

    static Clip clip;

    /*pre: none
     * post: plays an audio file */
    public static void play() {

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
