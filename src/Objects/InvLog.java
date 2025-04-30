package Objects;


public class InvLog {
    
    private int id;
    private int itemId;
    private String log;
    
    public InvLog(int id, int itemId, String log) {
        this.id = id;
        this.itemId = itemId;
        this.log = log;
    }
    
    public InvLog(int itemId, String log) {
        this.itemId = itemId;
        this.log = log;
    }
    
    // Getters
    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getLog() {
        return log;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setLog(String log) {
        this.log = log;
    }

    // toString method
    @Override
    public String toString() {
        return "InvLog{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", log='" + log + '\'' +
                '}';
    }
}
