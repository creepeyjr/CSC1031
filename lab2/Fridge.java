/*
OBJECTIVES
----------
The fridge will :
    - Store a list of food items.
    - Track a money balance to purchase food.
    - Provide methods to interact with the fridge :
        - Adding food.
        - Retrieving food.
        - Checking fridge's status. 
*/

import java.util.ArrayList;
import java.util.List;

public class Fridge {

    // Attributes
    List<String> contents;  // array for fridge content 
    int balance;  // balance variable

    // public constructor
    public Fridge(int initialBalance) {
        this.balance = initialBalance;  // initial balance of fridge
        // initial state of fridge balance
        if (balance <= -1) {
            System.out.println("Error");
            this.balance = 0;
        }

        // Create array for storing food
        this.contents = new ArrayList<>();
    }

    // Extra boolean function to manage what inputs are valid.
    public boolean validInput(String item, int cost) {
        if (item != null && item.isEmpty() == false && cost >= 0 && this.balance >= cost) {
            /*  IS TRUE IF :
                    item isn't null object
                    item is not empty
                    cost is not a negative number
                    balance is greater than cost
            */
            return true;
        }
        return false;
    }

    // Public getters and setters
    public void addFood(String item, int cost) {
        // add food item to the fridge and deducts the cost from balance
        // initial state of fridge is unacceptable, print Error and set balance to 0.
        if (validInput(item, cost)) { 
            this.balance = this.balance - cost;  // pay for food
            contents.add(item);  // add item to fridge
            System.out.println("Item " + item + " has been added to the fridge.");
        }
        else {
            System.out.println("Error"); // not enough funds
        }
    }

    public void getFood(String item) {
        // check if food exists in fridge
        // ADD INPUT IS VALID IF TEST REQUIRES IT.
        if (contents.contains(item) == true) {
            // remove an item from the fridge
            contents.remove(item);
            System.out.println("Item " + item + " has been removed from the fridge.");
        }
        else {
            // Send out an error message
            System.out.println("Error");
        }
        
    }

    public void checkStatus() {
        System.out.println("Food items:");

        if (contents.size() >= 1) {
            for (int i = 0; i < contents.size(); i++) {
                System.out.println(contents.get(i));
            }
        }
        else {
            System.out.println("(none)");
        }
        System.out.println("Balance: €" + balance);
    }
}