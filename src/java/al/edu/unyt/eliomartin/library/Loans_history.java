/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Martin
 */
public class Loans_history {
    private int userid;
    private String isbn;
    private Date dateLoaned;
    private Date deadline;
    private float feePaid;
    private Date dateReturned;
    
    public Loans_history(int user, String isbn, Date dateLoaned, Date deadline, float deePaid, Date dateReturned) {
        
            this.userid=userid;
            this.isbn=isbn;
            this.dateLoaned=dateLoaned;
            this.deadline=deadline;
            this.feePaid=feePaid;
            this.dateReturned=dateReturned;
    }
    
    public Loans_history(int user, String isbn) throws SQLException, ClassNotFoundException{
        String sql = "select * from loans_history where userid=? and isbn=? and datereturned is NULL";
        Database db=new Database(sql);
        db.getSt().setInt(1, user);
        db.getSt().setString(2, isbn);
        ResultSet rs = db.getSelect();
        
        if(rs.next()){
            this.userid=rs.getInt("userid");
            this.isbn=rs.getString("isbn");
            this.dateLoaned=rs.getDate("dateloaned");
            this.deadline=rs.getDate("deadline");
            this.feePaid=rs.getFloat("feepaid");
            this.dateReturned=rs.getDate("datereturned");
            
        }
        
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

    public Date getDateLoaned() {
        return dateLoaned;
    }

    public void setDateLoaned(Date dateLoaned) {
        this.dateLoaned = dateLoaned;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public float getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(float feePaid) {
        this.feePaid = feePaid;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }
    
    
    
    
    
    
}
