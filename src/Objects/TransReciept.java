package Objects;

public class TransReciept {

    private int id;
    private int item_id;
    private int user_id;
    private int quantity;
    // Date will be handled by SQLite, so no need to store it manually here.

    // Empty constructor
    public TransReciept() {
    }

    // Full constructor
    public TransReciept(int id, int item_id, int user_id, int quantity) {
        this.id = id;
        this.item_id = item_id;
        this.user_id = user_id;
        this.quantity = quantity;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // toString method
    @Override
    public String toString() {
        return "TransReciept{" +
                "id=" + id +
                ", item_id=" + item_id +
                ", user_id=" + user_id +
                ", quantity=" + quantity +
                '}';
    }
}

