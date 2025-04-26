package Database;

import Objects.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;


public final class DBHandler {
    
    // initialize the database
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
        
    public void loadImages(String folderLoc){
        String sql = "INSERT INTO gallery (photo) VALUES(?)";
        
        // gets the data for the images
        File folder = new File(folderLoc);
        
        File[] files = folder.listFiles();
        
        if (files != null) {
            System.out.println(folder.getName());
            for (File file : files) {
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    
                    FileInputStream fis = new FileInputStream(file);
                    byte[] imgData = fis.readAllBytes();
                    
                    stmt.setBytes(1, imgData);
                    
                    stmt.executeUpdate();
                    
                } catch (Exception ex) {
                    System.err.println("Error to load image: "+ ex);
                }
            }
        }
        
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
