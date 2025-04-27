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


            fis.close();

        } catch (IOException | SQLException ex) {
            System.err.println("Error to load image: "+ ex);
        }
        
    }
    
    // can load multiple images using the unloaded folder
    public void quickLoad(String fileFolder){
        
        String sql1 = "INSERT INTO gallery (photo) VALUES(?)";
        String sql2 = "INSERT INTO inventory (item) VALUES (?)";
        
        // gets the data for the images
        File folder = new File(fileFolder);
        
        File[] files = folder.listFiles();
        
        
        if (files != null) {
            for (File file : files) {
                try {
                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    PreparedStatement stmt2 = con.prepareStatement(sql2);

                    FileInputStream fis = new FileInputStream(file);
                    byte[] imgData = fis.readAllBytes();

                    stmt2.setString(1, file.getName());
                    stmt2.executeUpdate();

                    stmt1.setBytes(1, imgData);
                    stmt1.executeUpdate();

                    
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
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql1);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return new User(result.getInt("id"), 
                        result.getString("username"), 
                        result.getString("password"), 
                        result.getString("email"), 
                        result.getString("status"));
            }
            
        } catch (SQLException ex) {
            System.err.println("Failed to  verify user: "+ ex);
        }
        
        return null;
    }
    
    public InvItem getItem(){
        // TODO
        
        return null;
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
