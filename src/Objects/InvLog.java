package Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class InvLog {
    
    private int id;
    private int itemId;
    private String log;
    private String item;
    private Date date;
    
    // partial constructor
    public InvLog(int id, int itemId, String log) {
        this.id = id;
        this.itemId = itemId;
        this.log = log;
    }
    
    // constructor with out the product name
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
    
    // full constructor
    public InvLog(int id, int itemId, String item, String log, String date) {
        this.id = id;
        this.item = item;
        this.itemId = itemId;
        this.log = log;

        try {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            this.date = form.parse(date);
        } catch (ParseException ex) {
            System.err.println("Failed to parse date: " +ex.getMessage());
        }
    }
    
    public InvLog(int itemId, String item,String log) {
        this.itemId = itemId;
        this.item = item;
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
    
    public String getItem(){
        return item;
    }
    
    public String getDateString(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = formatter.format(date);
        return formattedDate;
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
    
    public void setItem(String item){
        this.item = item;
    }

    // toString method
    @Override
    public String toString() {
        return "InvLog{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", log='" + log + '\'' +
                ", date='" +  date +'\'' +
                '}';
    }
}
