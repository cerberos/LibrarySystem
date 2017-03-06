/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Coding
 */
@ManagedBean(name="bookBean")
public class BookBean {
    
    Database db = null;
    CachedRowSet rs = new com.sun.rowset.CachedRowSetImpl();
    
    public BookBean() throws SQLException{
        try{
            db = new Database("select * from books;");
            rs.populate( db.getResults(db.getSt()) );
        } finally {
            if (db != null)
            db.close();
        }
    }
    
    public ResultSet getBooks(){
        return rs;
    }
}
