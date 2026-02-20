/*
- Create a class.
- Which represents configurable products in an e-commerce system.
- Product should have diff configs depending on use case.
- Be capable of creating independent copies for diff purposes. (order snapshots, versioning)
*/

import java.util.ArrayList;
import java.util.List;



public class Product {
    // Attributes
    String productName;
    long price; // in cents, so long number
    boolean inStock;  // in stock boolean
    List<String> tags;  // list of tags describing product

    // 1. Public Constructor
    public Product() {
        // initialise all variables and lists
        this.productName = "Unknown";
        this.price = 0;
        this.inStock = false;
        this.tags = new ArrayList<>();
    }

    // Constructor with only product name
    public Product(String productName) {
        this.productName = productName;
        this.price = 0;
        this.inStock = false;
        this.tags = new ArrayList<>();
    }

    // Constructor with product name and price
    public Product(String productName, long price) {
        this.productName = productName;
        this.price = price;
        this.inStock = false;
        this.tags = new ArrayList<>();
    }

    // Constructor with product name, price, inStock
    public Product(String productName, long price, boolean inStock) {
        this.productName = productName;
        this.price = price;
        this.inStock = inStock;
        this.tags = new ArrayList<>();

    // 2. Main Constructor with all the fields
    public Product(String productName, long price, boolean inStock, List<String> tags) {
        this.productName = productName;
        this.price = price;
        this.inStock = inStock;
        /*  AI USED HERE
            Ternary operator (Short if-else statement)
            Structure : condition ? value_if_true : value_if_false
        
            tags != null - Checks if tags parameter passed to constructor is not null.
            ? - if, then this
            new ArrayList<>(tags) - If tags is not null, create a new ArrayList that contains all elements passed from the passed-in tags list.
            : - Else part of statement
            new ArrayList<>() : If tags is null, create a new empty ArrayList.
        */
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        /* LONG VERSION
        if (tags != null) {
            this.tags = new ArrayList<>(tags);  // Copy the passed-in list
        } else {
            this.tags = new ArrayList<>();      // Create empty list
        }
        */
    }

    // 3. Deep Copy Constructor
    public Product productCopy () {
        Product a = new Product(this.productName, this.price, this.inStock);  //Use constructor to copy fields
        return a;  // Method to return new independent object.
    }

    // 4. Encapsulate the tags field.
    // - Preserve encapsulation.
    /*
    Why the need to encapsulate everything?
        Lists are mutable objects and can be changed after creation.
        If list reference is shared, the object's internal state can be modified.
    */

    public List<String> getTags() {
        return new ArrayList<>(this.tags);  // Returns a copy, not original
    }

    public void setTags(List<String> tags) {
        // Takes a list of parameters from caller, 
        // Creates a new ArrayList with said parameters
        // And if the parameters are empty/null, create new list
        // assigns this list to this.tags
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    }

    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            this.tags.add(tag);
        }
    }

    // 5. Override toString() function 
    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", tags=" + tags +
                '}';
    }
}
