package Database;

import Objects.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public final class DBHandler {
    
    // initialize the database connection
    Connection con = null;
    private final String url = "jdbc:sqlite:src/Database/database.db";
    
    
    // database constructor
    public DBHandler(){
        try {
            getconnection();
        } catch (SQLException ex) {
            System.err.println("Error to connect to database: " + ex);
        }
    }
    
    // load image to database
    public void loadImage(String imgLoc, InvItem item){
        
        String sql1 = "INSERT INTO gallery (photo) VALUES(?)";
        String sql2 = "INSERT INTO inventory (item,price,quantity) VALUES (?,?,?)";
        
        // gets the data for the images
        File imgFile = new File(imgLoc);
        
        
        System.out.println(imgFile.getName());
        
        try {
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            PreparedStatement stmt2 = con.prepareStatement(sql2);

            FileInputStream fis = new FileInputStream(imgFile);
            byte[] imgData = fis.readAllBytes();

            stmt2.setString(0, item.getItem());
            stmt2.setFloat(1, item.getPrice());
            stmt2.setInt(2, item.getQuantity());

            stmt2.executeUpdate();
            
            stmt1.setBytes(1, imgData);
            stmt1.executeUpdate();
            
            createLog(item, "Add");

            fis.close();

        } catch (IOException | SQLException ex) {
            System.err.println("Error to load image: "+ ex);
        }
        
    }
    
    // can load multiple images using the unloaded folder
    public void quickLoad(String fileFolder){
        
        String sql1 = "INSERT INTO gallery (photo) VALUES(?)";
        String sql2 = "INSERT INTO inventory (item) VALUES (?)";
        String sql3 = "SELECT * FROM inventory WHERE item = ?";
        
        // gets the data for the images
        File folder = new File(fileFolder);
        
        File[] files = folder.listFiles();
        
        
        if (files != null) {
            for (File file : files) {
                try {
                    // adds to the database
                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    PreparedStatement stmt2 = con.prepareStatement(sql2);

                    FileInputStream fis = new FileInputStream(file);
                    byte[] imgData = fis.readAllBytes();

                    stmt2.setString(1, file.getName());
                    stmt2.executeUpdate();

                    stmt1.setBytes(1, imgData);
                    stmt1.executeUpdate();
                    
                    // records the log
                    PreparedStatement stmt3 = con.prepareStatement(sql3);
                    
                    stmt3.setString(1, file.getName());
                    ResultSet rs = stmt3.executeQuery();
                    
                    // add creates and add logs
                    InvLog log = createLog(new InvItem(rs.getString("item"), rs.getFloat("price"), rs.getInt("quantity")), "Add");
                    addLog(log);
                    
                    file.delete();
                    fis.close();

                } catch (IOException | SQLException ex) {
                    System.err.println("Error to load image: "+ ex);
                }
            }
 
        }
        
    }
    
    // login checker and returns user data
    public User verifyUser(String username, String password){
        String sql1 = "SELECT * FROM users WHERE username = ? AND password = ?";
        String sql2 = "SELECT * FROM users WHERE username = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql1);
            PreparedStatement stmt2 = con.prepareStatement(sql2);
            
            // query to check if the user exist
            stmt2.setString(1, username);
            ResultSet user = stmt2.executeQuery();
            if (!user.next()) {
                System.out.println("User does not exist");
                return null;
            }
            
            // query to check if the password is corrects and returns user data
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return new User(result.getInt("id"), 
                        result.getString("username"), 
                        result.getString("password"), 
                        result.getString("email"), 
                        result.getString("status"));
            }else{
                System.out.println("You have entered The wrong password");
            }
            
        } catch (SQLException ex) {
            System.err.println("Failed to  verify user: "+ ex);
        }
        
        return null;
    }
    
    // gets the inventory Item data
    public InvItem getItem(String item){
        String sql1 = "SELECT * FROM inventory where item = ?";
        
        try {
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            stmt1.setString(1, item);
            
            ResultSet rs = stmt1.executeQuery();
            if (rs.next()) {
                return new InvItem(rs.getInt(1), rs.getString(2), rs.getFloat(3),rs.getInt(4));
            }
            
            
        } catch (SQLException ex) {
            System.err.println("Error aquiring the data: "+ex.getMessage());
        }
        
        return null;
    }
    
    // update the metadata of the item and updates the data base
    public void updateItem(InvItem item){
        String sql = "UPDATE inventory SET price = ?, quantity = ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setFloat(1, item.getPrice());
            stmt.setInt(2, item.getQuantity());
            stmt.setInt(3, item.getId());
            
            stmt.executeUpdate();
            
            // add log
            InvLog log = createLog(item, "add");
            addLog(log);
            
        } catch (Exception e) {
            System.err.println("Failed to update inventory item: " + e.getMessage());
        }
        
    }
    
    // create Inventory log
    public InvLog createLog(InvItem item, String log){
        
        return new InvLog(item.getId(), log);        
    }
    
    // add logs to the database
    public void addLog(InvLog log){
        String sql = "INSERT INTO inventory_log (item_id,date,log) VALUEs (?,DATE('now'),?)";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, log.getItemId());
            stmt.setString(2, log.getLog());
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Error inserting the log: "+ e.getMessage());
        }
    }
    
    // connects to tthe database
    public void getconnection() throws SQLException{
        con = DriverManager.getConnection(url);
        String[] sqlCommands = {
            "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "status TEXT NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS inventory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "item TEXT UNIQUE NOT NULL," +
                "price REAL(32,2) NOT NULL DEFAULT (0)," +
                "quantity INTEGER NOT NULL DEFAULT (0)" +
            ");",

            "CREATE TABLE IF NOT EXISTS gallery (" +
                "item_id INTEGER PRIMARY KEY NOT NULL REFERENCES inventory(id)," +
                "photo BLOB NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS trans_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                "item_id INTEGER NOT NULL REFERENCES inventory(id)," +
                "user_id INTEGER NOT NULL REFERENCES users(id)," +
                "quantity INTEGER NOT NULL," +
                "date TEXT NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS inventory_log (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "item_id INTEGER NOT NULL REFERENCES inventory(id)," +
                "date TEXT NOT NULL," +
                "log TEXT NOT NULL" +
            ");"
        };

        Statement stmt = con.createStatement();
        for (String sql : sqlCommands) {
            stmt.execute(sql);
        }
    }
    
    // closes the database once not in use
    public void close(){
        try {
            con.close();
        } catch (SQLException ex) {
            System.err.println("Failed to close database: " + ex);
        }
    }
    
}
