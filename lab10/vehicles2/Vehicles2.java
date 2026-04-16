//package vehicles2;

abstract class Vehicle {
    // attributes
    String brand;
    Engine engine;  // pass in engine

    // constructor
    Vehicle(String brand, Engine engine) {
        this.brand = brand;
        this.engine = engine;
    }

    abstract void startEngine();
}

// Engine class here
class Engine {
    // attribute
    int horsePower;

    //constructor
    Engine(int horsePower) {
        this.horsePower = horsePower;
    }

    // getter for horsepower
    public int getHorsePower() {
        return horsePower;
    }
}

// Implementations

class Car extends Vehicle {
    // attributes
    int numDoors;

    // constructor
    Car(String brand, int numDoors, Engine engine) {
        super(brand, engine);
        this.numDoors = numDoors;
    }

    @Override  // IMPLMENT ENGINE
    void startEngine() {
        System.out.println("Starting car with " + engine.getHorsePower() + " horsepowers");
    }
}

class Bike extends Vehicle {
    // attributes
    boolean hasCarrier;

    // constructor
    Bike(String brand, boolean hasCarrier, Engine engine) {
        super(brand, engine);
        this.hasCarrier = hasCarrier;
    }

    @Override  // IMPLMENT ENGINE
    void startEngine() {
        System.out.println("Starting bike with " + engine.getHorsePower() + " horsepowers");
    }
}

class ElectricCar extends Car {
    // attributes
    int batteryCapacity;

    ElectricCar(String brand, int numDoors, int batteryCapacity, Engine engine) {
        super(brand, numDoors, engine);
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    void startEngine() {
        System.out.println("Starting electric car silently with " + engine.getHorsePower() + " horsepowers");
    }
}