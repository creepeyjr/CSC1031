/*
Understanding the task
======================

- Each docking station has a number of docks.
- Each dock may hold a bike that can be hired by a registered rider.
- System must support pedal and electrical bikes.
- Can be on hire, reserved for maintanance or out of service.
- 

*/

import java.util.ArrayList;
import java.util.List;



/*
BIKE CLASS STUFF HERE
=====================
*/

// Abstract Bike Class
abstract class Bike {
    // Attributes
    protected String status;  // Availability of bike, whether Available, OnHire, OutOfService or ReservedForMaintenance
    protected String condition;  // Good, Faulty, Needs Service
    protected String bikeId;
    
    protected List<String> faultHistory;  // Self explanatory


    // Constructor
    Bike(String status, String condition, String bikeId) {
        this.status = status;
        this.condition = condition;
        this. bikeId = bikeId;
        this.faultHistory = new ArrayList<>();
    }

    // Function declarations

        // All of these update the condition of the bike in some way.
    void markAvailable() {
        status = "Available";
        System.out.println("Bike "+ bikeId + "is Available for Hire.");
    }
    void markOnHire() {
        status = "OnHire";
        System.out.println("Bike "+ bikeId + "is Out On Hire.");
    }
    void markOutOfService() {
        status = "OutOfService";
        System.out.println("Bike "+ bikeId + "is Out Of Service.");
    }
    void reportFault(String faultDesc) {
        // update condition of bike to faulty
        this.status = "ReservedForMaintenance";
        this.condition = "Faulty";
        // record this incident now
        faultHistory.add(faultDesc);
        System.out.println("Bike " + bikeId + "fault recorded : " + faultDesc);
    }
    // abstract hireable check
    abstract boolean isHireable();
}

// PedalBike inheritance
class PedalBike extends Bike {
    PedalBike(String status, String condition, String bikeId) {
        super(status, condition, bikeId);
    }

    // Functions
    boolean confirmReadyForHire() {
        if (status.equals("Available") && condition.equals("Good")) {
            System.out.println("Bike " + bikeId + "is ready for Hire.");
            return true;
        }
        System.out.println("Bike " + bikeId + "is unavailable for Hire.");
        return false;
    }

    @Override
    boolean isHireable() {
        return confirmReadyForHire();
    }  // I will use this as a sort of blanket function for both bikes
}

// ElectricBike inheritance
class ElectricBike extends Bike {
    // Some more attributes
    int battery;

    ElectricBike(String status, String condition, String bikeId, int battery) {
        super(status, condition, bikeId);
        this.battery = battery;
    }

    // Functions
    void updateBatteryLevel(int newLevel) {
        battery = newLevel;
    }
    boolean isChargeSufficient() {
        if (battery >= 30) {
            System.out.println("Charge is sufficient.");
            return true;
        }
        System.out.println("Charge is insufficient. Apologies for the inconvenience.");
        return false;
    }

    @Override
    boolean isHireable() {
        return isChargeSufficient() && condition.equals("Good") && status.equals("Available");
    }  // Check those criterias muahaha
}

/*
DOCK CLASS STUFF HERE
=====================
*/
// A single dock here
class Dock {
    // attributes
    Bike parkedBike;  // witerally storing the Bike object.
    String dockId;
    boolean isWorking;

    // constructor
    Dock(String dockId, boolean isWorking) {
        this.parkedBike = null;  // start with no bike, just empty
        this.dockId = dockId;
        this.isWorking = isWorking;
    }

    // Functions
    void dockBike(Bike bike) {  // take in that Bike
        if (parkedBike == null && isWorking) {
            this.parkedBike = bike;  // need to better understand "this"
            System.out.println("Bike " + bike.bikeId + " docked in dock " + dockId);
        }
        else {
            System.out.println("Dock Occupied or Out of Service. Apologies.");
        }
    }
    Bike releaseBike() {
        if (parkedBike != null) {
            Bike bike = this.parkedBike;  // Bike bike object is made the current bike that is locked rn 
            this.parkedBike = null;  // Pretty much unlocking the bike here.
            System.out.println("Bike " + bike.bikeId + "released from dock " + dockId);
            return bike;  // "Giving" the bike back
        }
        else {
            System.out.println("No Bike to release from " + dockId + ". Apologies.");
            return null;
        }
    }
    boolean isOccupied() {
        return parkedBike != null;  // is there a bike in here?
    }
}

// Docking station
class DockingStation {
    // attributes
    String stationId;
    List<Dock> docks;  // list of docks
    String stationStatus;  // "Normal", "Nearly Full", "Full"

    // Constructor for class
    DockingStation(String stationId, int numberOfDocks) {
        this.stationId = stationId;
        this.docks = new ArrayList<>();
        this.stationStatus = "Normal";

        // Gonna build list of docks here
        for (int i = 0; i < numberOfDocks; i++) {
            String dockId = stationId + "-D" + i;  // give every dock individual id
            Dock newDock = new Dock(dockId, true);  // create new dock object
            this.docks.add(newDock);  // add to the list
        }

        System.out.println("Station " + stationId + " created with " + numberOfDocks + " docks.");
    }

    // Functions
    int countAvailableBikes() {
        int count = 0;

        for (Dock dock : docks) {  // "for the number of dock objects in 'docks' list"
            if (dock.isOccupied() == true && dock.parkedBike.isHireable() == true) {
                // If the dock is occupied AND the parked bike in the dock is hireable?
                count++;
            }
        }
        return count;
    }

    int countFreeDocks() {
        int count = 0;
        for (Dock dock : docks) {
            if (!dock.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    Bike findHireableBike() {
        for (Dock dock : docks) {
            if (dock.isOccupied() && dock.parkedBike.isHireable()) {
                System.out.println("Found hireable bike at " + dock.dockId);
                return dock.releaseBike();  // realse and return that bike
            }
        }
        System.out.println("No hireable bike available at station " + stationId);
        return null;  // no bike fr
    }

    // Accept return bike and dock it
    boolean acceptReturnedBike(Bike bike) {
        for (Dock dock : docks) {
            if (!dock.isOccupied()) {  // find first empty dock
                dock.dockBike(bike);  // dock that
                System.out.println("Bike " + bike.bikeId + " returned to station " + stationId);
                
                updateStationStatus();  // update station status frr
                return true;
            }
        }
        System.out.println("Station " + stationId + " is full. Cannot accept return.");
        
        updateStationStatus();  // calling func here will update stationStatus to full
        return false;
    }

    void updateStationStatus() {
        // Grab dock details
        int freeDocks = countFreeDocks();
    
        if (freeDocks == 0) {
            stationStatus = "Full";
            System.out.println("Station " + stationId + " is Full.");
        }
        else if (freeDocks <= 2) {
            stationStatus = "NearlyEmpty";
            System.out.println("Station " + stationId + " is nearly empty.");
        }
        else {
            stationStatus = "Normal";
        }
    }

    void displayStationInfo() {
        System.out.println("\n-- Station " + stationId + " --");
        System.out.println("- status : " + stationStatus);
        System.out.println("- Available : " + countAvailableBikes());
        System.out.println("- Free docks : " + countFreeDocks());
        System.out.println("- Total docks : " + docks.size());
    }
}

// Rider
class Rider {
    // attributes
    String riderId;
    String name;
    String email;
    List<String> messages;  // Store notifs received
    List<HireRecord> hireHistory;  // track all hires a rider has made

    // Constructor
    Rider(String riderId, String name, String email) {
        this.riderId = riderId;
        this.name = name;
        this.email = email;
        this.messages = new ArrayList<>();
        this.hireHistory = new ArrayList<>();
        
        System.out.println("Rider " + name + " (" + riderId + ") is registered.");
    }

    // Start functio here
    HireRecord startHire(Bike bike, DockingStation startStation) {
        if (!bike.isHireable()) {  // make sure the bike is hireable
            System.out.println("Cannot hire bike " + bike.bikeId);
            return null;
        }

        bike.markOnHire();
        HireRecord record = new HireRecord(this, bike, startStation);
        hireHistory.add(record);
        System.out.println("Rider " + name + " hired bike " + bike.bikeId);
        return record;
    }

    // now end said hire, THROW IT AWAY BAHA
    void endHire(HireRecord record, DockingStation endStation, Dock returnDock) {
        if (record != null && record.isActive) {
            record.closeHire(endStation, returnDock);
            System.out.println("Rider " + name + " returned bike");
        }
    }

    void receiveMessage(String message) {
        messages.add(message);
        System.out.println("[Message for " + name + "] " + message);
    }
}

class HireRecord {
    // Attributes that interact with objects
    Rider rider;
    Bike bike;
    DockingStation startStation;
    DockingStation endStation;
    Dock returnDock;

    // Attributes
    String hireId;
    String startTime;
    String endTime;
    boolean isActive;

    // Constructor
    HireRecord(Rider rider, Bike bike, DockingStation startStation) {
        // The ride starts here
        this.hireId = "HIRE" + System.currentTimeMillis();
        this.rider = rider;
        this.bike = bike;

        this.startTime = String.valueOf(System.currentTimeMillis());

        this.isActive = true;
        this.endStation = null;
        this.returnDock = null;
    }

    // Open a hire
    void openHire() {
        this.isActive = true;
        this.startTime = String.valueOf(System.currentTimeMillis());
        System.out.println("Hire " + hireId + " opened.");
    }

    // Close the hire
    void closeHire(DockingStation endStation, Dock returnDock) {
        this.endStation = endStation;
        this.endTime = String.valueOf(System.currentTimeMillis());
        this.returnDock = returnDock;
        this.isActive = false;
        this.bike.markAvailable();  // mark bike as available once again
    
        System.out.println("Hire " + hireId + " closed. Duration: " + calculateDuration() + " minuts.");
    }

    // calculate how long bike was hired for
    long calculateDuration() {
        return 30;  // placholder, doesn't have to be actual implementation
    }

    // Display hire details
    void displayHireDetails() {
        System.out.println("\n=== Hire Record " + hireId + " ===");
        System.out.println("Rider: " + rider.name);
        System.out.println("Bike: " + bike.bikeId + " (" + bike.getClass().getSimpleName() + ")");
        System.out.println("Start Station: " + startStation.stationId);
        System.out.println("Start Time: " + startTime);
        if (!isActive) {
            System.out.println("End Station: " + endStation.stationId);
            System.out.println("End Time: " + endTime);
            System.out.println("Duration: " + calculateDuration() + " minutes");
        } else {
            System.out.println("Status: ACTIVE");
        }
    }
}

class MaintenanceReport {  // represents a reported fault or service issue with a bike at the station or during return. 
    // Attributes
    String reportId;
    Bike faultyBike;  // introduce a bike object under the guise of it being faulty
    DockingStation station;
    String issueDesc;
    String priority;  // "Low", "High", "Urgent"
    boolean isClosed;

    // Constructor
    MaintenanceReport(Bike faultyBike, DockingStation station, String issueDesc) {
        this.reportId = "REP" + System.currentTimeMillis();
        this.faultyBike = faultyBike;
        this.station = station;
        this.issueDesc = issueDesc;
        this.isClosed = false;
    
        // assign priority too
        assignPriority();
    }

    void assignPriority() {
        if (faultyBike instanceof ElectricBike) {
            this.priority = "High";
        }
        else {
            this.priority = "Low";
        }
    }

    void recordIssue() {
        faultyBike.reportFault(issueDesc);
        System.out.println("Maintenance report " + reportId + " recorded");
    }


    void closeReport() {
        this.isClosed = true;
        System.out.println("Maintenance report " + reportId + " closed");
    }


    void displayReport() {
        System.out.println("\n=== Maintenance Report " + reportId + " ===");
        System.out.println("Bike: " + faultyBike.bikeId);
        System.out.println("Station: " + station.stationId);
        System.out.println("Issue: " + issueDesc);
        System.out.println("Priority: " + priority);
        System.out.println("Status: " + (isClosed ? "CLOSED" : "OPEN"));
    }
}

/*
NOTIFIABLE PARTY INTERFACE
==========================
Any class that can receive notifications must implement this
*/

interface NotifiableParty {
    void updateNotification(String message, String type);
}


/*
RIDER NOTIFIER
==============
Delivers messages to riders
*/

class RiderNotifier implements NotifiableParty {
    List<Rider> subscribers;
    
    RiderNotifier() {
        this.subscribers = new ArrayList<>();
    }
    
    void subscribe(Rider rider) {
        subscribers.add(rider);
    }
    
    @Override
    public void updateNotification(String message, String type) {
        for (Rider rider : subscribers) {
            rider.receiveMessage("[" + type + "] " + message);
        }
    }
}


/*
MAINTENANCE NOTIFIER
====================
Delivers alerts to maintenance team
*/

class MaintenanceNotifier implements NotifiableParty {
    List<String> contacts;
    
    MaintenanceNotifier() {
        this.contacts = new ArrayList<>();
    }
    
    void addContact(String contact) {
        contacts.add(contact);
    }
    
    @Override
    public void updateNotification(String message, String type) {
        for (String contact : contacts) {
            System.out.println("[MAINTENANCE to " + contact + "] " + type + ": " + message);
        }
    }
    
    void sendFaultAlert(String message) {
        updateNotification(message, "FAULT");
    }
}


/*
STATION EVENT COORDINATOR
=========================
Checks station conditions and triggers notifications
*/

class StationEventCoordinator {
    RiderNotifier riderNotifier;
    MaintenanceNotifier maintenanceNotifier;
    List<DockingStation> stations;
    
    StationEventCoordinator(RiderNotifier riderNotifier, MaintenanceNotifier maintenanceNotifier) {
        this.riderNotifier = riderNotifier;
        this.maintenanceNotifier = maintenanceNotifier;
        this.stations = new ArrayList<>();
    }
    
    void addStation(DockingStation station) {
        stations.add(station);
    }
    
    void evaluateStationStatus(DockingStation station) {
        station.updateStationStatus();
        
        if (station.stationStatus.equals("Full")) {
            riderNotifier.updateNotification(
                "Station " + station.stationId + " is FULL", "StationAlert"
            );
        } else if (station.stationStatus.equals("NearlyEmpty")) {
            riderNotifier.updateNotification(
                "Station " + station.stationId + " is NEARLY EMPTY", "StationAlert"
            );
        }
    }
    
    void handleBikeFault(Bike bike, DockingStation station, String issue) {
        MaintenanceReport report = new MaintenanceReport(bike, station, issue);
        report.recordIssue();
        maintenanceNotifier.sendFaultAlert(
            "Bike " + bike.bikeId + " at " + station.stationId + ": " + issue
        );
    }
    
    void handleLowBattery(ElectricBike bike, DockingStation station) {
        if (!bike.isChargeSufficient()) {
            maintenanceNotifier.updateNotification(
                "Bike " + bike.bikeId + " low battery (" + bike.battery + "%)", "LOW_BATTERY"
            );
        }
    }
}


public class CityBikeHire {
    public static void main(String[] args) {
        System.out.println("=== CITY BIKE HIRE SYSTEM ===\n");
        
        // Setup notifiers
        RiderNotifier riderNotifier = new RiderNotifier();
        MaintenanceNotifier maintenanceNotifier = new MaintenanceNotifier();
        maintenanceNotifier.addContact("maintenance@citybike.com");
        
        // Setup coordinator
        StationEventCoordinator coordinator = new StationEventCoordinator(riderNotifier, maintenanceNotifier);
        
        // Create station
        DockingStation station = new DockingStation("CENTRAL", 3);
        coordinator.addStation(station);
        
        // Create bikes
        PedalBike bike1 = new PedalBike("Available", "Good", "B001");
        ElectricBike bike2 = new ElectricBike("Available", "Good", "B002", 15); // Low battery
        
        // Dock bikes
        station.docks.get(0).dockBike(bike1);
        station.docks.get(1).dockBike(bike2);
        
        // Create and subscribe rider
        Rider alice = new Rider("R001", "Alice", "alice@email.com");
        riderNotifier.subscribe(alice);
        
        // Test notifications
        System.out.println("\n--- Testing Station Status ---");
        coordinator.evaluateStationStatus(station);
        
        System.out.println("\n--- Testing Low Battery ---");
        coordinator.handleLowBattery(bike2, station);
        
        System.out.println("\n--- Testing Fault ---");
        coordinator.handleBikeFault(bike1, station, "Broken brake");
        
        System.out.println("\n--- Testing Hire ---");
        Bike hired = station.findHireableBike();
        if (hired != null) {
            HireRecord hire = alice.startHire(hired, station);
            hire.displayHireDetails();
        }
        
        System.out.println("\n=== SYSTEM TEST COMPLETE ===");
    }
}