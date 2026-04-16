public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker("John", 30, "123 Main St", "W123");
        worker.displayInfo();

        worker.updateWorkerInfo("456 Elm St");
        worker.displayInfo();

        worker.updateWorkerInfo(35);
        worker.displayInfo();

        worker.fire();
        worker.displayInfo();
    }
}