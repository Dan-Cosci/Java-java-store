package Database;

import java.sql.*;

public class DBHandler {
    
    Connection con = null;
    private final String url = "jdbc:sqlite:src/Database/database.db";
    
    public DBHandler() {
        try {
            getconnection();
        } catch (SQLException ex) {
            System.err.println("Error to connect to database: " + ex);
        }
    }
    
    public void getconnection() throws SQLException{
        con = DriverManager.getConnection(url);
    }
    
    public void close(){
        try {
            con.close();
        } catch (SQLException ex) {
            System.err.println("Failed to close database: " + ex);
        }
    }
    
}
