/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;


/**
 *
 * @author EN
 */
@Named( "book" )
	
@ManagedBean
@SessionScoped
public class Book implements Serializable{
    
    public String s="ELIO";

    public  String getS() {
        return s;
    }

    public  void setS(String s) {
        this.s = s;
    }
    
    private String isbn;
    private String title;
    private String description;
    private int subcategoryID;
    private String holdFlag;
    private int numberOfCopies;
    private int editionNo;
    
    public Book ()
    {
       this.setDescription(description);
       this.setIsbn(isbn);
       this.setNumberOfCopies(numberOfCopies);
       this.setHoldFlag(holdFlag);
       this.setTitle(title);
       this.setEditionNo(editionNo);
       this.setSubcategoryID(subcategoryID);
    }
    
    public Book (String isbn, String title, String description, int subcategoryID, String holdFlag, int numberOfCopies, int editionNo)
    {
       this.setDescription(description);
       this.setIsbn(isbn);
       this.setNumberOfCopies(numberOfCopies);
       this.setHoldFlag(holdFlag);
       this.setTitle(title);
       this.setEditionNo(editionNo);
       this.setSubcategoryID(subcategoryID);
    }
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(int subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public String getHoldFlag() {
        return holdFlag;
    }

    public void setHoldFlag(String holdFlag) {
        this.holdFlag = holdFlag;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public int getEditionNo() {
        return editionNo;
    }

    public void setEditionNo(int editionNo) {
        this.editionNo = editionNo;
    }
   
    
    
}
