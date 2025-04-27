
import Database.DBHandler;
import Objects.User;


public class DBtester {
    public static void main(String args[]){
        DBHandler db = new DBHandler();
        User acc = db.verifyUser("BenDover", "123456");
        
        System.out.println(acc);
    }
}
