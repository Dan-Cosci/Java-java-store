package Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class InvLog {
    
    private int id;
    private int itemId;
    private String log;
    private Date date;
    
    public InvLog(int id, int itemId, String log) {
        this.id = id;
        this.itemId = itemId;
        this.log = log;
    }
    
    public InvLog(int id, int itemId, String log, String date) {
        this.id = id;
        this.itemId = itemId;
        this.log = log;

        try {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            this.date = form.parse(date);
        } catch (ParseException ex) {
            System.err.println("Failed to parse date: " +ex.getMessage());
        }
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
    
    public Date getDate(){
        return date;
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
