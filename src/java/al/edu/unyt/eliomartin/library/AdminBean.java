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
    String userType;
    String pendingRequestEmail;
    
    Database db = null;
    ResultSet rs= null;

    Book book= new Book();
  
    public AdminBean()
    {
      
    }
    
    public ArrayList<String> getUserTypes() throws SQLException, ClassNotFoundException
    {
        ArrayList<String> usertypes= new ArrayList();
        
        db=new Database("Select * from user_types");
        rs=db.getSelect();
        while(rs.next())
        { 
            usertypes.add("ID "+rs.getInt("userTypeCode")+" : "+rs.getString("name"));
        }
        rs.close();
        db.close();
        return usertypes;       
           
    }
    
    public ArrayList<String> getPendingRequests() throws SQLException, ClassNotFoundException
    {
        ArrayList<Integer> id= new ArrayList();
        ArrayList<String> emails=new ArrayList();
        db=new Database("Select * from system_access_requests");
       
        rs=db.getSelect();
        while(rs.next())
        {
            id.add(rs.getInt("userid"));
        }
        
        db.close();
        rs.close();
        
        for(int i:id)
        {
            db=new Database("Select email from logins where userTypeCode>0 and userid="+i);
            rs=db.getSelect();
            if(rs.next())
            emails.add(rs.getString("email"));
            db.close();
            rs.close();
            
        }
        
        return emails;
    }
    
    public void validateRequest() throws SQLException, ClassNotFoundException
    {
        db=new Database("UPDATE logins SET active=1 where email=?");
        
        db.getSt().setString(1, this.pendingRequestEmail);
        if(db.update())
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User was succesfully validated!"));
        else
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User validation failed!"));            
        
        db.close();
        db=new Database("Delete from system_access_requests  where userid=(select userid from logins where email=?)");
        db.getSt().setString(1, this.pendingRequestEmail);
        db.update();
        db.close();
    }
    
    public void insertBook() throws SQLException, ClassNotFoundException
    {
        
        db=new Database("Select * from books where isbn=?");
        db.getSt().setString(1, book.getIsbn());
        
        if(db.getSelect().next())
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("This book already exists!"));
        }
        
        else
        {
        db.close();
        db=new Database("INSERT INTO books (ISBN, Title, Description, SubcategoryID, NumberOfCopies, HoldFlag, EditionNo) VALUES (?, ?, ?, ?, ? , ?, ?)");
        book.setSubcategoryID(Integer.parseInt(bookSubcategoryName.substring(2, bookSubcategoryName.indexOf(":")).replaceAll("\\s","")));
          
        db.getSt().setString(1, book.getIsbn());
        db.getSt().setString(2, book.getTitle());
        db.getSt().setString(3, book.getDescription());
        db.getSt().setInt(4, book.getSubcategoryID());
        db.getSt().setInt(5, book.getNumberOfCopies());
        db.getSt().setString(6, book.getHoldFlag());
        db.getSt().setInt(7, book.getEditionNo());       
        if(db.update())
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book was succesfully inserted!"));
        else
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book insertion failed!"));            
        }
       
        db.close();
        bookSubcategoryName=""; 
              
    }
    
        public void createUser () throws SQLException, ClassNotFoundException
    {
             int userTypeCode=Integer.parseInt(userType.substring(2, userType.indexOf(":")).replaceAll("\\s",""));    
             
             db=new Database("Select * from logins where email=?");
             db.getSt().setString(1, email);
             if(db.getSelect().next())
             {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("This user already exists!"));
             }
             else
             {
             db.close();
             db=new Database("INSERT INTO logins (Email, Password, UserTypeCode, active) VALUES (?, ?, ?, 0)");          
             db.getSt().setString(1, this.email);
             db.getSt().setString(2, this.password);            
             db.getSt().setInt(3, userTypeCode);
             
             if(db.update())
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Instructor was created succesfully!"));
             else
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Instructor creation failed!"));
             }
           
             db.close();
    }
    
    public void createBookSubcategory() throws SQLException, ClassNotFoundException
    {
        
        int categoryid=Integer.parseInt(bookCategoryName1.substring(2, bookCategoryName1.indexOf(":")).replaceAll("\\s",""));    
        
        db=new Database("Insert into subcategories (name, categoryid) values (?, ?)");
        db.getSt().setString(1, this.bookSubcategoryName);
        db.getSt().setInt(2, categoryid);
      
        if(db.update())
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book Subategory was created succesfully!"));
        else
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book Subcategory creation failed!")); 
             
        db.close();
        bookCategoryName1="";
        
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
    
    public ArrayList<String> getBookSubcategories() throws SQLException, ClassNotFoundException
    {
        ArrayList<String> bookSubcategories=new ArrayList();
      
        db=new Database("Select subcategoryID, name from subcategories");
        rs=db.getSelect();
        while(rs.next())
        {

              bookSubcategories.add("ID "+rs.getInt("subcategoryid")+" : "+rs.getString("name"));
        }
        rs.close();
        db.close();
        return bookSubcategories;       
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
    
     public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
        
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

        public String getPendingRequestEmail() {
        return pendingRequestEmail;
    }

    public void setPendingRequestEmail(String pendingRequestEmail) {
        this.pendingRequestEmail = pendingRequestEmail;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        LibrarianBean a = new LibrarianBean();
        System.out.println(a.getUserTypes().size());  
    }
      
      
    
}
