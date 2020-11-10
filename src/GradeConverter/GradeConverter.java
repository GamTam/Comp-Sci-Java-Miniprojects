/*
Dev: Lucas DaCambra
Course Code: ICS4UI-02
Date Written: 11/8/2020
Description: The program manages the grades for multiple people
 */

package GradeConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeConverter {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException, IOException {
        textBox("Welcome to Grade Tracker!", true);
        textBox("This application is designed to help you with knowing what your grades are!\n", true);
        wait(0.5);
        textBox("Please select someone for grade management:\n", true);

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        File file = new File(path + "/Names");
        if (!file.exists()) {
            file.mkdirs();
        }
        File person;
        person = fileSelect(file, "person", "");

        textBox("Now, choose which subject you wish to grade them in.\n", true);
        File gradeCategory = fileSelect(person, "subject", ".csv");
    }

    public static File fileSelect(File folder, String type, String fileType) throws InterruptedException, IOException {
        int i = 1;
        Hashtable<Integer, File> people = new Hashtable<Integer, File>();

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
                        File average = new File(folder + "/" + name + "/Average.txt");
                        average.createNewFile();
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
                        writer.write("Assesment,Number Grade,Letter Grade");
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

    // Pauses processing
    public static void wait(Double seconds) throws InterruptedException {
        Thread.sleep(Math.round(seconds * 1000));
    }

    // Continuously loops until an integer within the selected range is chosen by the user
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

    // Continuously loops until a string is chosen by the user
    public static String selectWord() throws InterruptedException {
        String str = "";
        scan = new Scanner(System.in);

        while (true) {
            try {
                str = scan.nextLine();
                break;
            } catch (InputMismatchException e) {
                textBox("Sorry, that is not a valid option.", true);
                scan.next();
            }
        }

        return str;
    }
}
