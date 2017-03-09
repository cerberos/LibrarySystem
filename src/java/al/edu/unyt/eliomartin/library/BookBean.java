/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Coding
 *
 */

 @ManagedBean(name="bookBean")
 @SessionScoped
public class BookBean implements Serializable{
    
    private Database db = null;
    private ResultSet rs;
    private Book book;
    private String loan = "false";
    private String putQueue = "false";    
    private String hold = "false";    
    private String unhold = "false"; 
    private String message;
    private int userid;
    private String isbn = "";
    private String search = "";
    private String sql = "select * from books where title like '%' ? '%'";
    private ArrayList<Book> bookList= new ArrayList();
    private Map<String,String> bookSelect = new LinkedHashMap<String,String>();
    
    public BookBean() throws SQLException{
       
    } 
    
    
    
    
    public String loanBook() throws SQLException, ClassNotFoundException{
        if (book.getNumberOfCopies()>0){
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
    
    
    
    
    
    public String viewBook(){
        if(!isbn.equals(""))
            return "viewBook";
        return null;
    }
    
     
    public ArrayList<Book> getBookList() throws SQLException, ClassNotFoundException
    {
        bookList.clear();
         try
        {
            db = new Database(sql);
            db.getSt().setString(1, search);
            rs= db.getSelect() ;
            while(rs.next())
            {
                Book b=new Book(rs.getString("isbn"),rs.getString("title"),rs.getString("description"),rs.getInt("subcategoryid"),rs.getString("holdflag"),rs.getInt("numberofcopies"),rs.getInt("editionno"));
                bookList.add(b);            
            }
            return bookList;
        } 
         finally 
         {
            if (db != null)
            db.close();
        }     
    }
     
    public String[] getTitles() throws SQLException, ClassNotFoundException
    {
        ArrayList<String> a=new ArrayList();
        ArrayList<Book> books=this.getBookList();
        for(Book b: books)
        {
            a.add(b.getTitle());
        }
        
        return a.toArray(new String[a.size()]);
    }
    
    
    public Map<String, String> getBookSelect() throws SQLException, ClassNotFoundException {
        
        bookSelect.clear();
        
            db = new Database(sql);
            db.getSt().setString(1, search);
            rs= db.getSelect() ;
            
            while(rs.next())
		bookSelect.put(rs.getString("title"), rs.getString("isbn"));
        
        return bookSelect;
    }

    public void setBookSelect(Map<String, String> bookSelect) {
        this.bookSelect = bookSelect;
    }
    

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Book getBook() throws SQLException, ClassNotFoundException {
            
            db = new Database("select * from books where isbn = ?");
            db.getSt().setString(1, isbn);
            rs= db.getSelect() ;
            
            if(rs.next()){
		book = new Book(rs.getString("isbn"),rs.getString("title"),rs.getString("description"),
                        rs.getInt("subcategoryid"),rs.getString("holdflag"),rs.getInt("numberofcopies"),rs.getInt("editionno"));
                
                if(book.getNumberOfCopies()==0){
                    loan = "true";
                    putQueue = "false"; 
                } else {
                    loan = "false";
                    putQueue = "true"; 
                }
                
                
                if (rs.getString("holdflag").equals("t")){
                    hold = "true";
                    unhold = "false";
                    loan = "true";
                    putQueue = "true"; 
                } else {
                    hold = "false";
                    unhold = "true";
                }
                
            }
            else {
                book = new Book();
                loan = "false";
                putQueue = "false";  
                hold = "flase";
                unhold = "flase";
            }
            
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getPutQueue() {
        return putQueue;
    }

    public void setPutQueue(String putQueue) {
        this.putQueue = putQueue;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }

    public String getUnhold() {
        return unhold;
    }

    public void setUnhold(String unhold) {
        this.unhold = unhold;
    }
    
    
    
}
