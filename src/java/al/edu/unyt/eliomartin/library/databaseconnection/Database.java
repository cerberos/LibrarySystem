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
import javax.sql.rowset.CachedRowSet;

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
    
    public Database(String sql) throws SQLException{
        connect();
        this.st = conn.prepareStatement(sql);
    }
    
    private void connect() throws SQLException {
        this.conn = DriverManager.getConnection(connPath, user, pass);
    }

    public PreparedStatement getSt() {
        return st;
    }
    
    public ResultSet getResults (PreparedStatement st) throws SQLException{
        return st.executeQuery();
    }
    
    public void close()  throws SQLException{
        if (st!=null) st.close();
        if (conn!=null) conn.close();
    }
    
}
