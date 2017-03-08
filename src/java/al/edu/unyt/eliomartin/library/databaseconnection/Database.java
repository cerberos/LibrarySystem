/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Coding
 */
public class Database {
    Connection conn = null;
    PreparedStatement st = null;
    
    String connPath = "jdbc:mysql://localhost:3306/library";
    String user = "root";
    String pass = "";
    
    public Database(String sql) throws SQLException, ClassNotFoundException{
        
        connect();
        this.st = conn.prepareStatement(sql);
        
    }
    
    private void connect() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver"); //This way the connection with the database always works
        this.conn = DriverManager.getConnection(connPath, user, pass);
        
    }

    public PreparedStatement getSt() {
        return st;
    }
    
    public ResultSet getSelect () throws SQLException{
        return st.executeQuery();
    }
    
    public Boolean update() throws SQLException
    {
        if(st.executeUpdate()==0)
            return false;
        else
            return true;
            
    }
    
    public void close()  throws SQLException{
        if (st!=null) st.close();
        if (conn!=null) conn.close();
    }
    
}
