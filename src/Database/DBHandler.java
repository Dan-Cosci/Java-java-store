package Database;

import Objects.*;
import java.io.File;
import java.io.FileInputStream;
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

        } catch (Exception ex) {
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

                    stmt1.setBytes(1, imgData);
                    stmt1.executeUpdate();

                    stmt2.setString(0, file.getName());
                    stmt2.executeUpdate();
                    
                    file.delete();
                    
                    fis.close();

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
