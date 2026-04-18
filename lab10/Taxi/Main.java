public class Main {
    public static void main(String[] args) {
        DispatchCenter dispatchCenter = new DispatchCenter();

        Passenger alice = new Passenger("Alice");
        Passenger bob = new Passenger("Bob");

        Taxi taxi1 = new Taxi("Taxi-01");
        Taxi taxi2 = new Taxi("Taxi-02");
        Taxi taxi3 = new Taxi("Taxi-03");

        dispatchCenter.registerTaxi(taxi1);
        dispatchCenter.registerTaxi(taxi2);
        dispatchCenter.registerTaxi(taxi3);

        alice.requestRide("Airport", dispatchCenter);
        bob.requestRide("Downtown", dispatchCenter);

        taxi1.setAvailable(true);
        taxi2.setAvailable(true);
        taxi3.setAvailable(true);

        taxi1.respondToRide(true); // Accept the ride
        taxi2.respondToRide(false); // Reject the ride
        taxi3.respondToRide(true); // Accept the rejected ride
    }
}