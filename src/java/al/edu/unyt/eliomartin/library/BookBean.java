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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Coding
 *
 */

 @Named("bookBean")
 @ManagedBean
 @SessionScoped
public class BookBean implements Serializable{
    
    private ArrayList<Book> bookList= new ArrayList();

     public BookBean() throws SQLException{
       
    }    
     Database db = null;
     ResultSet rs;
     
    public ArrayList<Book> getBookList() throws SQLException, ClassNotFoundException
    {
        bookList.clear();
         try
        {
            db = new Database("select * from books");
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
    

    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        BookBean b= new BookBean();
        
        //ArrayList<Book> b1=b.getBookList();
        
        for(String b3:b.getTitles())
        {
        System.out.println(b3);
        }
    }
}
