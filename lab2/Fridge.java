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


/*
public class Fridge {
    ...

    public Fridge(int initialBalance) {
        ...
    }

    public void addFood(String item, int cost) {
        ...
    }

    public void getFood(String item) {
        ...
    }

    public void checkStatus() {
        ...
    }


}
*/

import java.util.ArrayList;
import java.util.List;

public class Fridge {

    // Attributes
    List<String> foodItems;
    int balance;

    public Fridge(int initialBalance) {
        
    }

    public static void main(String[] args) {
        Fridge fridge = new Fridge(5); // Initial balance: €5

        

        /*  Uncoded functionss
        fridge.addFood("Milk", 3);
        fridge.addFood("Bread", 1);

        // Not enough money now
        // Error message should appear
        fridge.addFood("Eggs", 4);

        fridge.getFood("Milk");
        fridge.getFood("Juice"); // Error message should appear

        fridge.checkStatus();
        */
    }
}
