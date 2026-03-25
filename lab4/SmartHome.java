
// Import packages
import java.util.ArrayList;
import java.util.List;


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

    
    // Required Methods
    public int getId() {
        return id; 
    }


    public void turnOn() {
        if (isOn == false) {
            System.out.println("Turning on " + brand + " appliance (ID: " + id + ")");
            this.isOn = true;
        }
        else {
            System.out.println(brand + " appliance (ID: " + id + ") is already ON");
        }        
    }

    public void turnOff() {
        if (isOn == true) {
            System.out.println("Turning off " + brand + " appliance (ID: " + id + ")");
            this.isOn = false;
        }
        else {
            System.out.println(brand + " appliance (ID: " + id + ") is already OFF");
        }
    }

    public boolean isOn() {
        return isOn;
    }

    // Setters and Getters
    // Setters must only accept positive values.
    // Non-positive values get error message
    public void setBrand(String brand) {
        if (brand == null || brand.length() <= 0) {
            System.out.println("Invalid value. Must be positive.");
        }
        else {
            this.brand = brand;
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setPowerConsumption(double powerConsumption) {
        if (powerConsumption > 0) {
            this.powerConsumption = powerConsumption;
        }
        else {
            System.out.println("Invalid value. Must be positive.");
        }
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }
}

class WashingMachine extends Appliance {
    private int drumSize;

    // Constructor for washing machine
    public WashingMachine(String brand, double powerConsumption, int drumSize) {
        super(brand, powerConsumption);  // Call parent constructor
        this.drumSize = drumSize;
    }

    // Setter and getter for drumsize here
    public void setDrumSize(int drumSize) {
        if (drumSize > 0) {
            this.drumSize = drumSize;
        }
        else {
            System.out.println("Invalid value. Must be positive.");
        }
    }

    public int getDrumSize() {
        return drumSize;
    }

    public void washClothes() {
        if (isOn() == true) {
            System.out.println("Washing clothes in a " + getBrand() + " washing machine");
        }
        else {
            System.out.println("Cannot wash clothes. The washing machine is OFF.");
        }
    }
}

class Refrigerator extends Appliance {
    private double temperature; 
    
    // constructor
    public Refrigerator(String brand, double powerConsumption, double temperature) {
        super(brand, powerConsumption);
        this.temperature = temperature;
    }

    // setters and getters
    public void setTemperature(double temperature) {
        // no negativity checks
        this.temperature = temperature;
    }
    public double getTemperature() {
        return temperature;
    }

    public void coolItems() {
        if (isOn() == true) {
            System.out.println("Cooling items in " + getBrand() + " refrigerator at " + getTemperature() + "°C (ID: " + getId() + ")");
        }
        else {
            System.out.println("Cannot cool items. The refrigerator is OFF.");
        }
    }
}

class SmartWashingMachine extends WashingMachine {
    private boolean hasWiFi;

    // constructor
    public SmartWashingMachine(String brand, double powerConsumption, int drumSize, boolean hasWiFi) {
        super(brand, powerConsumption, drumSize);
        this.hasWiFi = hasWiFi;
    }

    // get em and set em
    public void setHasWiFi(boolean hasWiFi) {
        this.hasWiFi = hasWiFi;
    }

    public boolean hasWiFi() {
        return hasWiFi;
    }

    public void connectToWiFi() {
        if (isOn() == true) {
            System.out.println("Smart Washing Machine (ID: " + getId() + ") connected to WiFi.");
        }
        else {
            System.out.println("Cannot connect to WiFi. The machine is OFF.");
        }
    }
}  

class SmartHome {
    private String ownerName;
    private List<Appliance> appliances;

    // constructor
    public SmartHome(String ownerName) {
        this.ownerName = ownerName;
        this.appliances = new ArrayList<>(); 
    }

    // Getter
    public String getOwnerName() {
        return ownerName;
    }

    // Array functionality
    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    public void removeAppliance(Appliance appliance) {
        appliances.remove(appliance);
    }

    public int getTotalAppliancesInHome() {
        return appliances.size();
    }

    public void turnOnAllAppliances() {
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).turnOn();
        }
    }
    
    public void turnOffAllAppliances() {
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).turnOff();
        }
    }
}