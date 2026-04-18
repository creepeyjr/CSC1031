import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

class Passenger {
    String name;
    
    Passenger(String name) {
        this.name = name;
    }

    public void requestRide(String destination, DispatchCenter center) {
        System.out.println("Passenger " + name + " requested a ride to " + destination + ".");
        center.addPassengerRequest(this, destination);
    }
}

// Helper class to store the pair of passenger and their destination
class PassengerRequest {
    Passenger passenger;
    String destination;

    PassengerRequest(Passenger passenger, String destination) {
        this.passenger = passenger;
        this.destination = destination;
    }
}

class Taxi {
    String taxiID;
    private DispatchCenter center;
    private PassengerRequest currentAssignment;

    Taxi(String taxiID) {
        this.taxiID = taxiID;
    }

    public void setDispatchCenter(DispatchCenter center) {
        this.center = center;
    }

    public void setAvailable(boolean available) {
        if (available && center != null) {
            System.out.println("Taxi " + taxiID + " is now available.");
            center.addAvailableTaxi(this);
        }
    }

    public void assignRide(PassengerRequest request) {
        this.currentAssignment = request;
    }

    public void respondToRide(boolean accept) {
        if (currentAssignment == null) return;

        if (accept) {
            System.out.println("Taxi " + taxiID + " accepted the ride to " + currentAssignment.destination + ".");
            // Assignment complete, clear current assignment
            currentAssignment = null;
        } else {
            System.out.println("Taxi " + taxiID + " rejected the ride to " + currentAssignment.destination + ". Searching for another taxi...");
            PassengerRequest rejectedRequest = this.currentAssignment;
            this.currentAssignment = null;
            // Put rejecting taxi back in queue and reassign the passenger
            center.handleRejection(this, rejectedRequest);
        }
    }
}

class DispatchCenter {
    private Queue<PassengerRequest> passengerQueue = new LinkedList<>();
    private Queue<Taxi> taxiQueue = new LinkedList<>();

    // Required by the example Main class
    public void registerTaxi(Taxi taxi) {
        taxi.setDispatchCenter(this);
    }

    public void addPassengerRequest(Passenger passenger, String destination) {
        PassengerRequest request = new PassengerRequest(passenger, destination);
        if (!taxiQueue.isEmpty()) {
            assign(taxiQueue.poll(), request);
        } else {
            passengerQueue.add(request);
        }
    }

    public void addAvailableTaxi(Taxi taxi) {
        if (!passengerQueue.isEmpty()) {
            assign(taxi, passengerQueue.poll());
        } else {
            taxiQueue.add(taxi);
        }
    }

    private void assign(Taxi taxi, PassengerRequest request) {
        System.out.println("Dispatch assigned Taxi " + taxi.taxiID + " to passenger " + request.passenger.name + ".");
        taxi.assignRide(request);
    }

    public void handleRejection(Taxi taxi, PassengerRequest request) {
        // Rejecting taxi goes to end of queue
        taxiQueue.add(taxi);
        
        // Pass request to next available taxi or put back in passenger queue
        if (!taxiQueue.isEmpty()) {
            assign(taxiQueue.poll(), request);
        } else {
            // This case handles if the rejecting taxi was the only one available
            passengerQueue.add(request);
        }
    }
}