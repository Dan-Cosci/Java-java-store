package Database;

import Objects.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
    
    public void getMostBought(){
        String sql = "SELECT inventory.item, SUM(trans_history.quantity) AS total_quantity" +
                    "FROM trans_history" +
                    "JOIN inventory ON trans_history.item_id = inventory.id" +
                    "GROUP BY trans_history.item_id" +
                    "ORDER BY total_quantity DESC" +
                    "LIMIT 1;";
    }

    
    // Can load multiple images using the unloaded folder
    public void quickLoad(String fileFolder) {

        // Insert both item and photo into the unified inventory table
        String sql = "INSERT INTO inventory (item, photo) VALUES (?, ?)";

        File folder = new File(fileFolder);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {

                    // Read image data
                    byte[] imgData = fis.readAllBytes();

                    // Prepare the INSERT statement
                    PreparedStatement stmt = con.prepareStatement(sql);

                    // Remove the file extension from the filename to use as the item name
                    String fileName = file.getName();
                    String name = fileName.substring(0, fileName.lastIndexOf('.'));

                    stmt.setString(1, name);
                    stmt.setBytes(2, imgData);

                    stmt.executeUpdate();

                    // Fetch the inserted item to record the log
                    String selectSQL = "SELECT * FROM inventory WHERE item = ?";
                    PreparedStatement selectStmt = con.prepareStatement(selectSQL);
                    selectStmt.setString(1, name);

                    ResultSet rs = selectStmt.executeQuery();
                    if (rs.next()) {
                        InvLog log = createLog(new InvItem(rs.getInt("id"),rs.getString("item"), rs.getFloat("price"), rs.getInt("quantity")), "Add");
                        addLog(log);
                    }

                    // Delete the file after successful insertion
                    file.delete();

                } catch (IOException | SQLException ex) {
                    System.err.println("Error loading image: " + ex);
                }
            }
        }
    }

    // gets all users for admin table
    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {                
                User user = new User(rs.getInt("id"), 
                                    rs.getString("username"), 
                                    rs.getString("password"), 
                                    rs.getString("email"), 
                                    rs.getString("status"));
                
                users.add(user);
               
            }
            
            return users;
            
        } catch (Exception e) {
            System.err.println("Error getting all user data: "+ e.getMessage());
        }
        
        return users;
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
            System.err.println("Failed to  verify user: " + ex);
        }
        
        return null;
    }
    
    // checks if username already exist
    public boolean UserExists(String usrn){
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usrn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (Exception e) {
            System.err.println("Error verifying username: "+e.getMessage());
        }
        
        return false;
    }
    
    // adds a new user
    public void AddUser(String username, String password, String email, String status){
        String sql = "INSERT INTO users (username, password, email, status) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, status);
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Error inserting new user: " + e.getMessage());
        }
    }
    
    
    // returns all items in inventory
    public ArrayList<InvItem> getInventory(){
        ArrayList<InvItem> items = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {                
                InvItem item = new InvItem(rs.getInt("id"), 
                                        rs.getString("item"), 
                                        rs.getFloat("price"), 
                                        rs.getInt("quantity"),
                                        rs.getBytes("photo"));
                
                items.add(item);
            }
            
            return items;
            
        } catch (Exception e) {
            System.out.println("Failed to get inventory: " + e.getMessage());
        }
        
        return items;
    }
    
    // gets the inventory Item data
    public InvItem getItem(String item){
        String sql1 = "SELECT * FROM inventory where item = ?";
        
        try {
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            stmt1.setString(1, item);
            
            ResultSet rs = stmt1.executeQuery();
            if (rs.next()) {
                return new InvItem(rs.getInt(1), rs.getString(2), rs.getFloat(3),rs.getInt(4), rs.getBytes(5));
            }
            
            
        } catch (SQLException ex) {
            System.err.println("Error aquiring the data: "+ex.getMessage());
        }
        
        return null;
    }
    
    // update the metadata of the item and updates the data base
    public void updateItem(InvItem item, String logStr){
        String sql = "UPDATE inventory SET price = ?, quantity = ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setFloat(1, item.getPrice());
            stmt.setInt(2, item.getQuantity());
            stmt.setInt(3, item.getId());
            
            stmt.executeUpdate();
            
            // add log
            InvLog log = createLog(item, logStr);
            addLog(log);
            
        } catch (Exception e) {
            System.err.println("Failed to update inventory item: " + e.getMessage());
        }
        
    }
    
    // adds new inventory Item
    public void addItem(String imgLoc, InvItem item, String log) {

        String sql = "INSERT INTO inventory (item, price, quantity, photo) VALUES (?, ?, ?, ?)";
        String sql2 = "SELECT *  FROM inventory WHERE item = ?";

        File imgFile = new File(imgLoc);

        System.out.println(imgFile.getName());

        try (FileInputStream fis = new FileInputStream(imgFile)) {
            byte[] imgData = fis.readAllBytes();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, item.getItem());
            stmt.setFloat(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setBytes(4, imgData);
            stmt.executeUpdate();
            
            PreparedStatement stmt2 = con.prepareStatement(sql2); 
            stmt.setString(1, item.getItem());
            ResultSet rs = stmt2.executeQuery();
            
            addLog(createLog(new InvItem(rs.getInt("id"), rs.getString("item"), rs.getFloat("price"), rs.getInt("quantity")), log));

        } catch (IOException | SQLException ex) {
            System.err.println("Error loading image: " + ex);
        }
    }
    
    
    // create Inventory log
    public InvLog createLog(InvItem item, String log){
        
        return new InvLog(item.getId(),item.getItem(), log);        
    }
    
    // add logs to the database
    public void addLog(InvLog log){
        String sql = "INSERT INTO inventory_log (item_id, item_name, date, log) VALUEs (?, ?, DATE('now'), ?)";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, log.getItemId());
            stmt.setString(2, log.getItem());
            stmt.setString(3, log.getLog());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Insert Success", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            
        } catch (Exception e) {
            System.err.println("Error inserting the log: "+ e.getMessage());
        }
    }
    
    // update only the log message and date for an existing log entry
    public void updateLog(InvLog log) {
        String sql = "UPDATE inventory_log SET log = ?, date = DATE('now') WHERE id = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, log.getLog());
            stmt.setInt(2, log.getId()); // assumes InvLog has getId()

            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Rows updated: " + rows, "Table update", JOptionPane.INFORMATION_MESSAGE);


        } catch (Exception e) {
            System.err.println("Error updating the log: " + e.getMessage());
        }
    }
        
    // Deletes a log from inventory_log using only the log ID
    public void deleteLog(int logId) {
        String sql = "DELETE FROM inventory_log WHERE id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, logId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Log deleted successfully");
                JOptionPane.showMessageDialog(null, "Log deleted successfully", "Log Deleted", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "No log found with ID: " + logId, "ID not exist", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting the log: " + e.getMessage());
        }
    }

    
    // gets all Inventory logs
    public ArrayList<InvLog> getInvLog(){
        
        String sql = "SELECT * FROM inventory_log";
        ArrayList<InvLog> logs = new ArrayList<>();
        try {
            Statement smtm = con.createStatement();
            ResultSet rs = smtm.executeQuery(sql);
            
            while (rs.next()) {                
                logs.add(new InvLog(rs.getInt("id"),
                                    rs.getInt("item_id"),
                                    rs.getString("item_name"),
                                    rs.getString("log"),
                                    rs.getString("date")
                ));
            }
            
        } catch (Exception e) {
            System.err.println("Error loading the inventory history: "+e.getMessage());
        }
        
        
        return logs;
    }
    
    
    // quick search function for inventory logs
    public ArrayList<InvLog> quickSearch(String search, int Mode) {
        ArrayList<InvLog> arr = new ArrayList<>();
        String sql = "";

        switch (Mode) {
            case 0: // Item Name Search
                sql = "SELECT * FROM inventory_log WHERE item_name = ?";
                break;
            case 1: // ID Search
                sql = "SELECT * FROM inventory_log WHERE id = ?";
                break;
            default:
                System.err.println("Invalid search mode");
                return arr;
        }

        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            if (Mode == 1) {
                stmt.setInt(1, Integer.parseInt(search));
            } else {
                stmt.setString(1, search);
            }

            ResultSet rs = stmt.executeQuery();

            // adds items to the arrayList
            while (rs.next()) {
                arr.add(new InvLog(
                    rs.getInt("id"),
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("log"),
                    rs.getString("date")
                ));
            }

        } catch (Exception e) {
            System.err.println("Search did not work. Error: " + e.getMessage());
        }

        return arr;
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
            "    id       INTEGER      PRIMARY KEY AUTOINCREMENT UNIQUE," +
            "    item     TEXT         UNIQUE NOT NULL,\n" +
            "    price    REAL (32, 2) NOT NULL DEFAULT (0)," +
            "    quantity INTEGER      NOT NULL DEFAULT (0)," +
            "    photo    BLOB         NOT NULL" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS trans_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                "item_id INTEGER NOT NULL REFERENCES inventory(id) ON DELETE CASCADE," +
                "user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE," +
                "quantity INTEGER NOT NULL," +
                "date TEXT NOT NULL" +
            ");",

            "CREATE TABLE IF NOT EXISTS inventory_log ("+
                "id        INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "item_id   INTEGER NOT NULL REFERENCES inventory (id) ON DELETE CASCADE,"+
                "item_name TEXT    REFERENCES inventory (item) ON DELETE CASCADE,"+
                "date      TEXT    NOT NULL,"+
                "log       TEXT    NOT NULL"+
            ");"
//            ,"INSERT OR IGNORE INTO users (username, password, email, status) VALUES('Admin','123456','admin@gmail.com','admin')",
//            "INSERT OR IGNORE INTO users (username, password, email, status) VALUES('Pacifica','123456','pacifica@gmail.com','user')",
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
