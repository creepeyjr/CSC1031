import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

interface Reader{
    Scanner scanner = new Scanner(System.in);
}

abstract class Document implements Reader {
    protected List<String> content = new ArrayList<>();

    // Creating abstract getDocType Method
    protected abstract String getDocType();

    // Template method
    public final void generateDocument() {
        createHeader();
        createBody();
        createFooter();
        printDocument();
    }

    // Header Method : company name and date
    private void createHeader() {
        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();
        if (companyName.isEmpty()) throw new IllegalArgumentException("Error: Company name cannot be empty.");

        System.out.print("Enter date (DD/MM/YYYY): ");
        String date = scanner.nextLine();
        if (date.isEmpty()) throw new IllegalArgumentException("Date cannot be empty.");

        // Add lines to the document
        content.add("Company: " + companyName);
        content.add("Date: " + date);
    }

    // CreateBody Method : Abstract here, subclasses will define how the body is constructed.
    abstract void createBody();

    private void createFooter() {
        if (getDocType().equals("INVOICE")) {
            content.add("Prepared by: AutoDoc System");
            content.add("Document Type: INVOICE");
        }
        else if (getDocType().equals("REPORT")) {
            content.add("Reviewed by: Management Department");
        }
        else {
            content.add("Prepared by: AutoDoc System");
            content.add("Document Type: RECEIPT");
        }
    }

    private void printDocument() {
        System.out.println("\n=== Printing Document ===");
        System.out.println("=== " + getDocType() + " ===");
        for (String line : content) {
            System.out.println(line);
        }
        System.out.println("=========================");
    }
}
// Concreate class extensions

// Invoice class
class Invoice extends Document {
    // Defining getDocType method
    @Override
    protected String getDocType() {
        return "INVOICE";
    }

    // Override abstract createBody
    void createBody() {
        // Create body inputs
        // Total amount
        System.out.print("Enter total amount: ");
        double totalAmount = scanner.nextDouble();
        if (totalAmount <= 0) throw new IllegalArgumentException("Error: Total amount must be positive.");
    
        // Add new values to doc
        content.add("Total Due: €" + totalAmount);
    }
}
// report class
class Report extends Document {
    // Defining getDocType method
    @Override
    protected String getDocType() {
        return "REPORT";
    }

    // Override abstract createBody
    void createBody() {
        // Create body inputs
        //Report summary
        System.out.print("Enter report summary: ");
        String summary = scanner.nextLine();
        if (summary.isEmpty()) {
            System.out.print("Warning: Summary is empty.\n");
        }
            // Add new values to doc
        content.add("Report Summary: " + summary);
    }
}
// Receipt class
class Receipt extends Document {
    // Defining getDocType method
    @Override
    protected String getDocType() {
        return "RECEIPT";
    }
    // Override abstract createBody
    void createBody() {
        //scanner.nextLine();
        
        // Create body inputs
        //Amount paid
        System.out.print("Enter amount paid: ");
        double amountPaid = scanner.nextDouble();
        if (amountPaid <= 0) throw new IllegalArgumentException("Amount paid must be positive.");

        //Number of items
        System.out.print("Enter number of items: ");
        int itemsCount = scanner.nextInt();

        if (itemsCount <= 0) throw new IllegalArgumentException("Error: Items count must be positive.");

        double eachPrice = amountPaid / itemsCount;

        // Add new values to doc
        content.add("Total Paid: €" + amountPaid);
        content.add("Items Purchased: " + itemsCount);
        content.add("Price per Item: €" + eachPrice);
    }
}

public class DocumentGenerator implements Reader{
    public static void main(String[] args) {
        try {
            System.out.println("Choose document type: (INV) Invoice, (REP) Report, (REC) Receipt");
            String choice = scanner.nextLine();
            Document document;
            switch (choice) {
                case "INV":
                    document = new Invoice();
                    break;
                case "REP":
                    document = new Report();
                    break;
                case "REC":
                    document = new Receipt();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid choice. Exiting.");
            }
            document.generateDocument();
            
        } 
        catch (InputMismatchException e) {
            System.out.println("Error: Total amount must be numeric.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}