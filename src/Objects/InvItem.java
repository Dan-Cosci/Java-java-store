package Objects;

import java.awt.Image;
import java.awt.Toolkit;

public class InvItem {

    private int id;
    private String item;
    private float price;
    private int quantity;
    private Image img;

    // Empty constructor
    public InvItem() {
    }
    
    // Constructor without ID
    public InvItem(String item, float price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Full constructor with the image
    public InvItem(int id, String item, float price, int quantity) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Full constructor
    public InvItem(int id, String item, float price, int quantity, byte[] img) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.img = imgConvert(img);
    }

    // image converter
    private Image imgConvert(byte[] img){
        Image returnImage = null;
        
        returnImage = Toolkit.getDefaultToolkit().createImage(img);
        
        return returnImage;
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
    
    public Image getImage() {
        return img;
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
    
    public void setImage(Image img) {
        this.img = img;
    }

    // toString method (for easy printing)
    @Override
    public String toString() {
        return "InvItem{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", hasImage=" + img.toString() +
                '}';
    }
}
