
import Database.DBHandler;
import Objects.InvItem;
import Objects.User;


public class DBtester {
    public static void main(String args[]){
        DBHandler db = new DBHandler();
        InvItem obj = db.getItem("canCoke.jpg");
        System.out.println(obj);
        obj.setPrice(25.5f);
        obj.setQuantity(70);
        db.updateItem(obj);
        
    }
}
