/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coding
 */
@ManagedBean(name="bookBean")
public class BookBean {
    
    @Resource(name="jdbc/addressbook")
    DataSource dataSource;
    
    public ResultSet getBooks() throws SQLException {
        String sql = "select * from books";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","123456");
                PreparedStatement st = conn.prepareStatement(sql);
                CachedRowSet rs = new com.sun.rowset.CachedRowSetImpl()) {
            
            
            rs.populate(st.executeQuery());
            return rs;
        } 
    }
}
