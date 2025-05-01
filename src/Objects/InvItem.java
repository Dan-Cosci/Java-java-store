package Objects;

public class InvItem {

    private int id;
    private String item;
    private float price;
    private int quantity;

    // Empty constructor
    public InvItem() {
    }
    
    // Constructor without ID
    public InvItem(String item, float price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Full constructor
    public InvItem(int id, String item, float price, int quantity) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // toString method (for easy printing)
    @Override
    public String toString() {
        return "InvItem{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
