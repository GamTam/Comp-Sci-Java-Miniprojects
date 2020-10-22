// Dev: Lucas DaCambra
// Course Code: ICS4UI-02
// Date Written: 9/17/2020
// Description: The program calculates the percentage of a user's income based on their inputs over a user specified time-period.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Budget {	
	
	// Variable Setup
	static List<String> categories = new ArrayList<>();	
	static Double income;
	static List<Double> spent = new ArrayList<>();
	static String name;
	static int months;
	static Scanner scan = new Scanner(System.in);
	static Double total = 0d;

	public static void main(String[] args) throws InterruptedException {	
		// Set up default spending categories
		categories.add("Food");
		categories.add("Clothes");
		categories.add("Entertainment");
		categories.add("Cell Phone");
		
		// Display Text
		Thread.sleep(1000);
		textBox("Welcome to the B U D G E T  M A S T E R  3 0 0 0. ", 25, true);
		textBox("In order to continue, you must answer the following questions:", 25, true);
		System.out.println("");
		
		//Setup questions
		textBox("Do you wish to use the default categories? Y/N: ", 25, false);
		String defaults = scan.nextLine();
		defaults = defaults.toLowerCase();
		
		
		if (defaults.equals("y") || defaults.equals("yes")) {
			textBox("Okay, moving on with the default categories...", 25, true);
			
		} else if (defaults.equals("n") || defaults.equals("no")) {
			
			// Loop through custom number, and add all user picked items to list			
			categories = new ArrayList<>();
			textBox("Then please enter your categories. ", 25, false);
			textBox("Type 'done' when you wish to finish.", 25, true);
			String done = "weeeeeeee";
			int i = 0;
			while (!done.equals("done")) {	
				textBox("Category #" + (i+1) + ": ", 25, false);
				String cat = scan.nextLine();
				if (cat.isEmpty()) {
					while (cat.isEmpty()) {
						cat = scan.nextLine();
					}
				}
				i += 1;
				if (!cat.equals("done")) {
					categories.add(cat);
				} else {
					done = cat;
				}
			}
			if (categories.size() <= 0) quit();
			textBox("Thank you! ", 25, false);
			
		} else {			
			quit();
		}
		
		// Ask for name and number of months to travel back		
		textBox("Now, can you please enter your name? ", 25, false);		
		name = scan.nextLine();
		
		newLine();
		
		textBox("Hello " + name + ", could you please state how far back in months you wish to list your expenses: ", 25, false);
		months = scan.nextInt();
		
		newLine();
		
		if (months == 1) {
			textBox("Over the past month, how much money did you spend in: ", 25, true);
		} else if (months > 1){
			textBox("Over the past " + months + " months, how much money did you spend in: ", 25, true);
		} else {
			quit();
		}
		
		// Loop through all categories and prompt user for amount spent
		for (String cat: categories) {
			textBox(cat + ": $", 25, false);
			spent.add(scan.nextDouble());
		}
		
		textBox("And how much did you make? $", 25, false);
		income = scan.nextDouble();
		
		textBox("Calculating", 25, false);
		Thread.sleep(200);
		textBox(".", 25, false);
		Thread.sleep(200);
		textBox(".", 25, false);
		Thread.sleep(200);
		textBox(".", 25, false);
		Thread.sleep(200);
		textBox(" Done!", 25, true);
		Thread.sleep(200);
		textBox("  ", 25, true);
		
		// Display output
		if (months == 1) {
			textBox(name + ", over the past month, you have: ", 25, true);
		} else if (months > 1){
			textBox(name + ", over the past " + months + " months, you have: ", 25, true);
		} else {
			quit();
		}
		
		if (categories.size() > 1) {
			for (int i=0; i < categories.size() - 1; i++) {
				textBox("Spent " + calculatePercent(spent.get(i), income) + "% of your income on " + categories.get(i) + ",", 25, true);
				total += spent.get(i);
				Thread.sleep(200);
			}
			textBox("And spent " + calculatePercent(spent.get(spent.size() - 1), income) + "% of your income on " + categories.get(categories.size() - 1) + ".", 25, true);
			Thread.sleep(200);
			total += spent.get(spent.size() - 1);
		} else {
			textBox("Spent " + calculatePercent(spent.get(spent.size() - 1), income) + "% of your income on " + categories.get(categories.size() - 1) + ".", 25, true);
			Thread.sleep(200);
			total += spent.get(spent.size() - 1);
		}
		
		System.out.println("");
		
		textBox("In total, you spent " + calculatePercent(total, income) + "% of your income ($" + String.valueOf(total) + "), leaving you with " + calculatePercent(income - total, income) + "% left ($" + String.valueOf(income - total) + ").", 25, true);
		System.out.println("");
		
		// Give advice
		if (total > income) {
			textBox("You have spent more money than you have made.", 25, true);
			textBox("This is not usually a good idea, as you will run out of money if you\nkeep going like this", 25, true);
		} else if (total / income > 0.8) {
			textBox("You have spent most of your money, not leaving much left for emergencies.", 25, true);
			textBox("You should try to save more money.", 25, true);
		} else if (total/income > 0.5) {
			textBox("You're spending a bit over half your money.", 25, true);
			textBox("That's a healthy amount to be saving!", 25, false);
			textBox(" Good job!", 25, true);
		} else {
			textBox("You've spent less than half your income!", 25, true);
			textBox("That's a lot of savings!", 25, true);
		}
				
		textBox("Thank you for your time, " + name + "!", 25, false);
		
		scan.close();
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
	
	// Quit program	
	public static void quit() throws InterruptedException {				
		textBox("That is not a valid answer. ", 25, false);
		textBox("Please try again.", 25, true);
		scan.close();
		System.exit(0);					
	}
	
	// Start a new line in the console
	public static void newLine() {
		System.out.println("");
	}
	
	// Calculates the percent
	public static String calculatePercent(Double spent, Double max) {
		return String.format("%.2f", (spent/max) * 100);
	}
}
