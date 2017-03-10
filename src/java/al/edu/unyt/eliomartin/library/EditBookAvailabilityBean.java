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
    
    @ManagedProperty(value="#{bookBean.book}")
    private Book book;

   @ManagedProperty(value="#{logedInUser.login}")
    private Login login;
    
    public String loanBook() throws SQLException, ClassNotFoundException{
        if (book.getNumberOfCopies()>0){
            java.sql.Date d = new java.sql.Date(daysAfter(3).getTime());
            
            String sql = "insert into loans_history (userid, isbn, deadline) values (?,?,?)";
            Database db=new Database(sql);
            db.getSt().setInt(1, login.getUserID());
            db.getSt().setString(2, book.getIsbn());
            db.getSt().setDate(3, d);
        
            if(db.update()){
                sql = "update books set numberofcopies=?,  where isbn=?";
                db=new Database(sql);
                db.getSt().setInt(1, book.getNumberOfCopies()-1);
                db.getSt().setString(2, book.getIsbn());
                
                if(db.update()){
                    message="Congratulations! you got your book.<br/>";
                    return "index";
                } else {
                    message = "You can't get this book right now!<br/>";
                    return null;
            }
                
            } else {
                message = "You can't get this book right now!<br/>";
                return null;
            }
        }
        message = "You can't get this book right now!<br/>";
        return null;
    }
    
    public void putQueue() throws SQLException, ClassNotFoundException{
        
        if (book.getNumberOfCopies()==0){
            
            String sql = "insert into book_requests (userid, isbn, requesteddays) values (?,?,?)";
            Database db=new Database(sql);
            db.getSt().setInt(1, login.getUserID());
            db.getSt().setString(2, book.getIsbn());
            db.getSt().setInt(3, 3);
        
            if(db.update()){
                message="Your request is put on queue.";
            } else {
                message = "The book is available";
            }
        } else {
        message = "The book is available. please loan it directly.";
        }
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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    
    
}
