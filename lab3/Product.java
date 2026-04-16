import java.util.ArrayList;
import java.util.List;

class Product {
    // attributes
    String productName;
    long price;
    boolean inStock;
    List<String> tags;
    
    // Constructor here
    public Product() {
        this.productName = "Unknown";
        this.price = 0;
        this.inStock = false;
        this.tags = new ArrayList<>();
    }

    // 2. Constructor Overloading hereeeee
    // Only productName
    public Product(String productName) {
        this.productName = (productName == null || productName.trim().isEmpty()) ? "Unknown" : productName;
        this.price = 0;
        this.inStock = false;
        this.tags = new ArrayList<>();
    }
    // Only productName and price
    public Product(String productName, long price) {
        this.productName = (productName == null || productName.trim().isEmpty()) ? "Unknown" : productName;
        this.price = Math.max(price, 0);
        this.inStock = false;
        this.tags = new ArrayList<>();
    }
    // Only price and inStock
    public Product(long price, boolean inStock) {
        this.productName = "Unknown";
        this.price = Math.max(price, 0);
        this.inStock = inStock;
        this.tags = new ArrayList<>();
    }

    // Only productName, price and inStock
    public Product(String productName, long price, boolean inStock) {
        this.productName = (productName == null || productName.trim().isEmpty()) ? "Unknown" : productName;
        this.price = Math.max(price, 0);
        this.inStock = inStock;
        this.tags = new ArrayList<>();
    }
    // Only productName, price and tags
    public Product(String productName, long price, List<String> tags) {
        this.productName = (productName == null || productName.trim().isEmpty()) ? "Unknown" : productName;
        this.price = Math.max(price, 0);
        
        if (tags != null) {
            this.tags = new ArrayList<>(tags);
        }
        else {
            this.tags = new ArrayList<>();
        }
    }

    // With all 4 fields
    public Product(String productName, long price, boolean inStock, List<String> tags) {
        this.productName = (productName == null || productName.trim().isEmpty()) ? "Unknown" : productName;
        this.price = Math.max(price, 0);
        this.inStock = inStock;

        // Logic to create or take in tags
        if (tags != null) {
            this.tags = new ArrayList<>(tags);
        }
        else {
            this.tags = new ArrayList<>();
        }
    }

    // 3. Deep Copy Constructor
    public Product(Product other) {
        this.productName = other.productName;
        this.price = other.price;
        this.inStock = other.inStock;
        this.tags = new ArrayList<>(other.tags);
    }

    // 4. Encapsulate the tags field.
    // - Preserve encapsulation.
    /*
    Why the need to encapsulate everything?
        Lists are mutable objects and can be changed after creation.
        If list reference is shared, the object's internal state can be modified.
    */
    public List<String> getTags() {
        return new ArrayList<>(this.tags);
    }

    public void setTags(List<String> tags) {
        if (tags != null) {
            this.tags = new ArrayList<>(tags);
        }
        else {
            this.tags = new ArrayList<>();
        }
    }

    public void addTag(String tag) {
    if (tag != null) {
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
