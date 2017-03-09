/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.util.Date;
import java.sql.SQLException;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Martin
 */
 @ManagedBean(name="bookAvailability")
 @ViewScoped
public class EditBookAvailabilityBean {
    private String message="";
    
    //@ManagedProperty(value="#{param.book}")
    private Book Book;

   // @ManagedProperty(value="#{bookBean.book.isbn}")
    private String isbn;
    
   // @ManagedProperty(value="#{bookBean.book.numberOfCopies}")
    private int numberOfCopies;
    private int userid;
    
    public String loanBook() throws SQLException, ClassNotFoundException{
        if (numberOfCopies>0){
            java.sql.Date d = new java.sql.Date(daysAfter(3).getTime());
            
            String sql = "insert into loans_history (userid, isbn, deadline) values (?,?,?)";
            Database db=new Database(sql);
            db.getSt().setInt(1, userid);
            db.getSt().setString(2, isbn);
            db.getSt().setDate(3, d);
        
            if(db.update()){
                message="Congratulations! you got your book.<br/>";
                return "index";
            } else {
                message = "You cang get this book right now!<br/>";
                return null;
            }
        }
        message = "You can't get this book right now!<br/>";
        return null;
    }
    
    
    private Date daysAfter(int day){
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }
      public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
