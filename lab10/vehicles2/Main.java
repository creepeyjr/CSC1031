public class Main {
    public static void main(String[] args) {
        Engine carEngine = new Engine(150);
        Car myCar = new Car("Toyota", 4, carEngine);
        myCar.startEngine();

        Engine bikeEngine = new Engine(20);
        Bike myBike = new Bike("Yamaha", true, bikeEngine);
        myBike.startEngine();

        Engine electricCarEngine = new Engine(200);
        ElectricCar tesla = new ElectricCar("Tesla", 4, 75, electricCarEngine);
        tesla.startEngine();
    }
}