/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

/**
 *
 * @author Coding
 */
public class Logins {
    private int userID;
    private String email;
    private int userTypeCode;
    private int active;
    
    public Logins(){
        
    }
    
    public Logins(int userID, String email, int utc, int active){
        this.setUserID(userID);
        this.setEmail(email);
        this.setUserTypeCode(utc);
        this.setActive(active);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserTypeCode() {
        return userTypeCode;
    }

    public void setUserTypeCode(int userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
    
}
