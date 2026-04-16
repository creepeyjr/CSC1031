public class Person {
    String name;
    int age;
    String address;

    public Person(String name, int age, String address) {
        System.out.println("Person constructor");
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
    }
}
interface SpecialFunctionality {
    void fire();
}


class Worker extends Person implements SpecialFunctionality {
    // attribute
    String workerID;
    
    // constructor
    public Worker(String name, int age, String address, String workerID) {
        super(name, age, address);
        this.workerID = workerID;
    }

    // Method overloading
    void updateWorkerInfo(String newAddress) {
        this.address = newAddress;
    }

    void updateWorkerInfo( int newAge) {
        this.age = newAge;
    }

    public void fire() {
        this.address = "Fired";
        System.out.println("Worker " + workerID + " has been fired!");
    }
}

