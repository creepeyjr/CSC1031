import java.util.ArrayList;
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
        if (companyName.isEmpty()) throw new IllegalArgumentException("Company name cannot be empty.");

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
        if (getDocType() == "Invoice") {
            content.add("Prepared by: AutoDoc System");
            content.add("Document Type: INVOICE");
        }
        else if (getDocType() == "Report") {
            content.add("Reviewed by: Management Department");
        }
        else {
            content.add("Prepared by: AutoDoc System");
            content.add("Document Type: RECEIPT");
        }
    }

    private void printDocument() {
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
        return "Invoice";
    }

    // Override abstract createBody
    void createBody() {
        // Create body inputs
        // Total amount
        System.out.print("Enter total amount: ");
        double totalAmount = scanner.nextDouble();
        if (totalAmount <= 0) throw new IllegalArgumentException("Total amount must be positive.");
    
        // Add new values to doc
        content.add("Total Amount: €" + totalAmount);
    }
}
// report class
class Report extends Document {
    // Defining getDocType method
    @Override
    protected String getDocType() {
        return "Report";
    }

    // Override abstract createBody
    void createBody() {
        // Create body inputs
        //Report summary
        System.out.print("Enter report summary: ");
        String summary = scanner.nextLine();
        if (summary.isEmpty()) {
            System.out.println("Warning, Summary empty.");
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
        return "Receipt";
    }
    // Override abstract createBody
    void createBody() {
        // Create body inputs
        //Amount paid
        System.out.print("Enter amount paid: ");
        double amountPaid = scanner.nextDouble();
        if (amountPaid <= 0) throw new IllegalArgumentException("Amount paid must be positive.");

        //Number of items
        System.out.print("Enter number of items: ");
        int itemsCount = scanner.nextInt();
        if (itemsCount <= 0) throw new IllegalArgumentException("Items count must be positive.");

        // Price per item
        if (itemsCount == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        double eachPrice = amountPaid / itemsCount;

        // Add new values to doc
        content.add("Total Paid: " + amountPaid);
        content.add("Items Purchased: " + itemsCount);
        content.add("Price per Item: " + eachPrice);
    }
}