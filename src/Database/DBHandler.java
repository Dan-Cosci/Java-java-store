package Database;

import Objects.*;
import java.sql.*;

public class DBHandler {
    
    // initialize the database
    Connection con = null;
    private final String url = "jdbc:sqlite:src/Database/database.db";
    
    
    // database constructor
    public DBHandler() {
        try {
            getconnection();
        } catch (SQLException ex) {
            System.err.println("Error to connect to database: " + ex);
        }
    }
        
    public void loadImages(String folderLoc){
        String sql = "INSERT INTO gallery (item_id,photo) VALUES(?,?) ";
    }
    
    public User getUser(){
        // TODO
        
        return null;
    }
    
    public InvItem getItem(){
        // TODO
        
        return null;
    }
    
    
    
    
    // connects to tthe database
    public void getconnection() throws SQLException{
        con = DriverManager.getConnection(url);
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
