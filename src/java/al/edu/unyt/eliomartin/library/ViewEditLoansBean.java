/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Martin
 */
@ManagedBean(name="viewEditLoansBean")
@ViewScoped
public class ViewEditLoansBean {
    
    private ArrayList<Loans_history> loans_history= new ArrayList();
    private Map<String,String> loaned_list = new LinkedHashMap<String,String>();
    
    private String bookIsbn;
    private String message = "";
    public final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

    @ManagedProperty(value="#{logedInUser.login}")
    private Login login;
    
    private int bookTotalFee = 2000;
    

    public void checkForExpiration() throws SQLException, ClassNotFoundException{
        String sql = "select * from loans_history where userid=? and datereturned is Null";
        Database db = null ;
        ResultSet rs = null;
        
        try{
            db = new Database(sql);
            db.getSt().setInt(1, login.getUserID());
            rs= db.getSelect() ;
            
            if (!rs.next()){
                this.message += "Tere is no book that will expire soon.";
            } else {
                rs.beforeFirst();
            }
            
            while(rs.next()) {
		if (DifferenceInDays(new java.sql.Date(new Date().getTime()),rs.getDate("deadline")) < 1 )
                    this.message += "The book with isbn: <b>"+ rs.getString("isbn") + "</b> will expire today in midknight. <br/>";
            }
        } finally {
            if (rs != null) rs.close();
            if (db != null) db.close();
        }
    }
    
    public ArrayList<Loans_history> getLoan_list() throws SQLException, ClassNotFoundException
    {
        String sql = "select * from loans_history where userid=?";
        Database db = null ;
        ResultSet rs = null;
        this.loans_history.clear();
         try
        {
            db = new Database(sql);
            db.getSt().setInt(1, login.getUserID());
            rs= db.getSelect() ;
            while(rs.next())
            {
                Loans_history lh = new Loans_history(rs.getInt("userid"),rs.getString("isbn"),rs.getDate("dateLoaned"),
                        rs.getDate("deadline"),rs.getFloat("feePaid"),rs.getDate("dateReturned"));
                this.loans_history.add(lh);            
            }
            return this.loans_history;
        } finally {
            if (rs != null) rs.close();
            if (db != null) db.close();
            
        }     
    }
    
    
    
    
    public Map<String, String> loaned() throws SQLException, ClassNotFoundException {
        String sql = "select * from loans_history where userid=? and datereturned is Null";
        Database db = null ;
        ResultSet rs = null;
        loaned_list.clear();
        
        try{
            db = new Database(sql);
            db.getSt().setInt(1, login.getUserID());
            rs= db.getSelect() ;
            
            while(rs.next())
		loaned_list.put(rs.getString("isbn"), rs.getString("isbn"));
        
            return loaned_list;
        } finally {
            if (rs != null) rs.close();
            if (db != null) db.close();
        }
    }
    
    
    public  void returnBook() throws SQLException, ClassNotFoundException {
        String sql = "update loans_history set datereturned=?, feepaid=? where userid=? and isbn=? and datereturned is NULL";
        Database db = null ;
        ResultSet rs = null;
        java.sql.Date d = new java.sql.Date(new Date().getTime());
        Loans_history lh = new Loans_history(login.getUserID(), bookIsbn);
        float fee = 0;
        int res= DifferenceInDays(lh.getDateLoaned(), d);
        
        if (res <= 3)
            fee = (float)(bookTotalFee * 0.1);
        else if (res>3) {
            fee = (float)((bookTotalFee * 0.1)+( (res-3) * 0.2 * bookTotalFee ));
                if ( fee > bookTotalFee) fee=bookTotalFee;
        } else 
            fee = 0;
                
                
                try{
            db = new Database(sql);
            db.getSt().setDate(1, d);
            db.getSt().setFloat(2, fee);
            db.getSt().setInt(3, login.getUserID());
            db.getSt().setString(4, bookIsbn);
            if (db.update()){
                this.message += "You returned succesfuly your book.";
            } else {
                this.message += "There was a problem and your book wasn't returned.";
            }
            
            
            
        } finally {
            if (rs != null) rs.close();
            if (db != null) db.close();
        }
    }
    
    public static int DifferenceInDays(java.sql.Date from, java.sql.Date to) {
        return (int)((to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY);
    }
    
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
