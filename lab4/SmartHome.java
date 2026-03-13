
// Import packages
package lab4;  // What is this bruh??
import java.util.ArrayList;
import java.util.List;

// I will genuinely kill myself

public class SmartHome {
    // Fuck this chud class
}

// Part 1 : Creating of Appliance class
class Appliance {
    // Whole load of Attributes!!
    private final int id;  // Unique identifier for each appliance
    private String brand;  
    private double powerConsumption;
    private boolean isOn;  // Tracks whether the appliance is currently ON or OFF
    private static int nextId = 1;  // Static Variable used to generate unique IDs

    // Constructor
    public Appliance(String brand, double powerConsumption) {
        // Responsibilities
        this.id = nextId;
        nextId++;
        this.brand = brand;
        this.powerConsumption = powerConsumption;
        this.isOn = false;
    }

    /* 
    // Required Methods
    public int getId() {
        id = 
    }
    */

    void turnOn() {
        
    }
}
