// Dev: Lucas DaCambra
// Course Code: ICS4UI-02
// Date Written: 9/23/2020
// Description: The program adds together user picked items price and calorie count and gives the according advice.

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path; 
import java.nio.file.Paths;

public class McDonalds {
	
	// Category Setup
	static FoodCategory mainOrder = new FoodCategory();
	static FoodCategory sideOrder = new FoodCategory();
	static FoodCategory drinkOrder = new FoodCategory();
	static FoodCategory dessertOrder = new FoodCategory();
	
	// Variable Setup
	static Scanner scan = new Scanner(System.in);
	
	static String orderOption;
	
	static Hashtable<String, Integer> orders = new Hashtable<String, Integer>();	
	static Hashtable<String, FoodCategory> menu = new Hashtable<String, FoodCategory>();
	
	static double totalPrice = 0;
	static int totalCals = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		// Read from files
		newCategory(mainOrder, "Main Order.csv");
		newCategory(sideOrder, "Side Order.csv");
		newCategory(drinkOrder, "Drinks.csv");
		newCategory(dessertOrder, "Desserts.csv");
		
		// Set up dictionaries
		menu.put("Main", mainOrder);
		menu.put("Side", sideOrder);
		menu.put("Drink", drinkOrder);
		menu.put("Dessert", dessertOrder);
		
		orders.put("Main", -1);
		orders.put("Side", -1);
		orders.put("Drink", -1);
		orders.put("Dessert", -1);
		
		// Dialogue
		textBox("Hello, and welcome to McDonalds's!\n", 25, true);
		wait(0.5);

		System.out.print("\033[H\033[2J");
		System.out.flush();
		
		// Main Order
		textBox("Would you like to order one of our Main Selections? Y/N: ", 25, false);
		orderOption = scan.nextLine();
		if (orderOption.equalsIgnoreCase("y") || orderOption.equalsIgnoreCase("yes")) {
			order("Main");
		} else if (orderOption.equalsIgnoreCase("n") || orderOption.equalsIgnoreCase("no")) {
			textBox("Okay, moving on...\n", 25, true);
		} else {
			quit();
		}
		
		// Side Order
		textBox("Would you like to order something on the side? Y/N: ", 25, false);
		orderOption = scan.nextLine();
		if (orderOption.equalsIgnoreCase("y") || orderOption.equalsIgnoreCase("yes")) {
			order("Side");
		} else if (orderOption.equalsIgnoreCase("n") || orderOption.equalsIgnoreCase("no")) {
			textBox("Okay, moving on...\n", 25, true);
		} else {
			quit();
		}
		
		// Drink Order
		textBox("Would you like to order a drink? Y/N: ", 25, false);
		orderOption = scan.nextLine();
		if (orderOption.equalsIgnoreCase("y") || orderOption.equalsIgnoreCase("yes")) {
			order("Drink");
		} else if (orderOption.equalsIgnoreCase("n") || orderOption.equalsIgnoreCase("no")) {
			textBox("Okay, moving on...\n", 25, true);
		} else {
			quit();
		}
		
		// Dessert Order
		textBox("And how about dessert? Y/N: ", 25, false);
		orderOption = scan.nextLine();
		if (orderOption.equalsIgnoreCase("y") || orderOption.equalsIgnoreCase("yes")) {
			order("Dessert");
		} else if (orderOption.equalsIgnoreCase("n") || orderOption.equalsIgnoreCase("no")) {
			textBox("Okay, moving on...\n", 25, true);
		} else {
			quit();
		}
		
		
		
		
		// Final Display
		textBox("Thank you for your order!\n", 25, true);		
		wait(1.0);
		
		textBox("Your total comes to $" + Double.toString(totalPrice) + ", ", 25, false);
		textBox("and the meal contains " + Integer.toString(totalCals) + " calories.\n", 25, true);
		
		if (totalCals > 2000) {
			textBox("That is more calories than are required in a day.", 25, true);
			textBox("Stop.", 25, true);
		} else if (totalCals > 1000) {
			textBox("That is more than half of the recommended daily calories", 25, true);
			textBox("Try eating less.", 25, true);
		} else if (totalCals > 500) {
			textBox("That's a healthy amount of calories!", 25, true);
			textBox("Good job!", 25, true);
		} else {
			textBox("That's not nearly enough calories based on the recommended.", 25, true);
			textBox("Eat more.", 25, true);
		}
		
		newLine();
		
		textBox("Thank you for ordering from McDonald's!", 25, true);
		textBox("Your order is on it's way!", 25, false);
	}
	
	// Reads CSV file and adds data to food class
	public static void newCategory(FoodCategory food, String file) throws IOException {
		// Variable Setup
		Path path = Paths.get(file).toAbsolutePath();
		BufferedReader br = new BufferedReader(new FileReader(path.toString()));
		String oneData = "";
		int i = 0;
		
		// Loops through CSV and adds data to food class
		while ((oneData = br.readLine()) != null) {
			String[] data = oneData.split(",");
			
			if (i > 0) {
				food.food.add(data[0]);
				food.price.add(Double.parseDouble(data[1]));
				food.calories.add(Integer.parseInt(data[2]));
			}
			
			i++;
		}
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
	
	// Wait for time in seconds
	public static void wait(Double seconds) throws InterruptedException {
		Thread.sleep(Math.round(seconds * 4));
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

	// Adds menu item to user order
	public static void order(String type) throws InterruptedException {
		textBox("Okay!", 25, false);
		textBox(" Currently on the menu, we have: \n", 25, true);
		textBox(String.format("%2$-10s %3$-50s %4$-15s %5$s", " ", "Item #", "Item Name", "Price", "Calories"), 25, true);
		
		textBox(String.format("%2$-10s %3$-50s %4$-15s %5$s", " ", "---------", "---------", "---------", "---------"), 25, true);

		
		for (int i=0; i < menu.get(type).food.size(); i++) {
			String price = String.format("%.2f", menu.get(type).price.get(i));
			String item = "";
			if (menu.get(type).calories.get(i) == 1) {
				item = String.format("%2$-10s %3$-50s %4$-15s %5$s", " ", "Item #" + (i + 1) + ": ", menu.get(type).food.get(i), "$" + price, menu.get(type).calories.get(i) + " Calorie");
			} else {
				item = String.format("%2$-10s %3$-50s %4$-15s %5$s", " ", "Item #" + (i + 1) + ": ", menu.get(type).food.get(i), "$" + price, menu.get(type).calories.get(i) + " Calories");
			}
			textBox(item, 25, true);
			wait(0.5);
		}
		
		newLine();
		textBox("Which item would you like to order? ", 25, false);
		
		String line = scan.nextLine();
		newLine();
		
		try {
			// If user inputs a number, make sure its a valid number in the list, then add it
			int choice = Integer.parseInt(line);
			
			choice -= 1;
			if (choice < menu.get(type).food.size() && choice >= 0) {
				orders.put(type, choice);
				totalPrice += menu.get(type).price.get(choice);
				totalCals += menu.get(type).calories.get(choice);
			} else {
				quit();
			}
		} catch (NumberFormatException e) {
			// If the user inputs a string, make sure that it's an item on the list, then add it
			String choice = line;
			
			for (int i=0; i < menu.get(type).food.size(); i++) {
				if (choice.equalsIgnoreCase(menu.get(type).food.get(i))) {
					orders.put(type, i);
					totalPrice += menu.get(type).price.get(i);
					totalCals += menu.get(type).calories.get(i);
					textBox("Would you like another " + type + "? Y/N: ", 25, false);
					orderOption = scan.nextLine();
					if (orderOption.equalsIgnoreCase("y") || orderOption.equalsIgnoreCase("yes")) {
						order(type);
					} else if (orderOption.equalsIgnoreCase("n") || orderOption.equalsIgnoreCase("no")) {
						return;
					} else {
						quit();
					}
					return;
				}
			}
			
			quit();
		}
		
		textBox("Would you like another " + type + "? Y/N: ", 25, false);
		orderOption = scan.nextLine();
		if (orderOption.equalsIgnoreCase("y") || orderOption.equalsIgnoreCase("yes")) {
			order(type);
		} else if (orderOption.equalsIgnoreCase("n") || orderOption.equalsIgnoreCase("no")) {
			return;
		} else {
			quit();
		}
	}
}

// Container for category information
class FoodCategory {
	List<String> food = new ArrayList<String>();
	List<Double> price = new ArrayList<Double>();
	List<Integer> calories = new ArrayList<Integer>();
}
