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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author EN
 */
@Named( "adminBean" )
	
@ManagedBean
@SessionScoped
public class AdminBean {
    
    String email;
    String password;
    String bookCategoryName;   //This is used to create new category
    String bookCategoryName1; //This is used for the listing the categories

    String bookSubcategoryName;

    Database db = null;
    ResultSet rs= null;



    
    public AdminBean()
    {
      
    }
    
    public void createBookSubcategory() throws SQLException, ClassNotFoundException
    {
//        bookCategoryName1="ID 2 :";
//        bookSubcategoryName="lalala";
        int categoryid=Integer.parseInt(bookCategoryName1.substring(2, bookCategoryName1.indexOf(":")).replaceAll("\\s",""));
        
       
        db=new Database("Insert into subcategories (name, categoryid) values (?, ?)");
        db.getSt().setString(1, this.bookSubcategoryName);
        db.getSt().setInt(2, categoryid);
        
        if(db.update())
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book Subategory was created succesfully!"));
        else
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book Subcategory creation failed!")); 
             
        db.close();
        
    }
    
    

    public ArrayList<String> getBookCategories() throws SQLException, ClassNotFoundException
    {
        ArrayList<String> bookCategories=new ArrayList();
        ArrayList<Integer> ids=new ArrayList();
        ArrayList<String> names=new ArrayList();
        db=new Database("Select categoryID, name from categories");
        rs=db.getSelect();
        while(rs.next())
        {
            ids.add(rs.getInt("categoryid"));
            names.add(rs.getString("name"));
            bookCategories.add("ID "+rs.getInt("categoryid")+" : "+rs.getString("name"));
        }
        rs.close();
        db.close();
        return bookCategories;       
    }
    
    
    
    
        public void createBookCategory() throws SQLException, ClassNotFoundException
    {
        db=new Database("Insert into categories (name) values (?)");
        db.getSt().setString(1, bookCategoryName);
        
        if(db.update())
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book Category was created succesfully!"));
        else
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book Category creation failed!")); 
             
        db.close();
        
    }
        
    public ArrayList<String> getLibrariansEmails() throws SQLException, ClassNotFoundException
    {
        ArrayList<String> emails=new ArrayList();
        db=new Database("Select email from logins where usertypecode=1 and password=''");
        rs=db.getSelect();
        while(rs.next())
        {
            emails.add(rs.getString("email"));
        }
        db.close();
        rs.close();
        return emails;       
    }
    
    
    public ArrayList<String>  getInstructorsEmails() throws SQLException, ClassNotFoundException
    {
        ArrayList<String> emails=new ArrayList();
        db=new Database("Select email from logins where usertypecode=2 and password=''");
        rs=db.getSelect();
         while(rs.next())
        {
            emails.add(rs.getString("email"));
        }    
        db.close();
        rs.close();
        return emails;      
    }
    
         public void createInstructor() throws SQLException, ClassNotFoundException
         {
             String email=this.getEmail();
             String password=this.getPassword();
             db=new Database("UPDATE logins SET Password=? where email=?");
             db.getSt().setString(1, this.password);
             db.getSt().setString(2, this.email);
             
             if(db.update())
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Instructor was created succesfully!"));
             else
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Instructor creation failed!")); 
             
             db.close();
         }
         
         public void createLibrarian() throws SQLException, ClassNotFoundException
         {
             String email=this.getEmail();
             String password=this.getPassword();
             db=new Database("UPDATE logins SET Password=? where email=?");
             db.getSt().setString(1, this.password);
             db.getSt().setString(2, this.email);
            
             if(db.update())
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Librarian was created succesfully!"));
             else
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Librarian creation failed!"));   
             db.close();
         }
         
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
        public String getBookCategoryName() {
        return bookCategoryName;
    }

    public void setBookCategoryName(String bookCategoryName) {
        this.bookCategoryName = bookCategoryName;
    }
    
    
    public String getBookSubcategoryName() {
        return bookSubcategoryName;
    }

    public void setBookSubcategoryName(String bookSubcategoryName) {
        this.bookSubcategoryName = bookSubcategoryName;
    }
    
        public String getBookCategoryName1() {
        return bookCategoryName1;
    }

    public void setBookCategoryName1(String bookCategoryName1) {
        this.bookCategoryName1 = bookCategoryName1;
    }
    
      public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        AdminBean b= new AdminBean();
       
        b.createBookSubcategory();
        for(String s: b.getInstructorsEmails())
        {
        System.out.println(s);
        }      
    }
      
      
    
}
