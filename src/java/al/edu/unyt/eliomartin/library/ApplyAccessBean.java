/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Coding
 */
@ManagedBean(name="applyAccessBean")
public class ApplyAccessBean implements Serializable {
    
    
    String sql = "select count(`sar`.`UserID`) as count, l.userid as id from system_access_requests sar, logins l"
            + " where l.userid = sar.userid and l.email = ?";
    
    private Logins login = null;
    private String email;
    private String message1;
    private String message2;
    
    Database db=null;
    ResultSet rs=null;
    
    public void request() throws SQLException, ClassNotFoundException{
        try{
            db = new Database(sql);
            PreparedStatement st = db.getSt();
            st.setString(1, email);
            rs = db.getSelect();
            if (rs.next()) {
                if(rs.getInt("count") == 1){
                    this.message1 = "Your request is waiting for administrator to approve it, please be patient.";
                    this.message2 = "";
                } else if (rs.getInt("count") == 0) {
                    int str = rs.getInt("id");
                    if (str != 0){
                        sql = "INSERT INTO `system_access_requests` (`UserID`) VALUES (?)";
                        db = new Database(sql);
                        st = db.getSt();
                        st.setInt(1, str);
                        db.update();

                        this.message1 = "";
                        this.message2 = "Your request was submited and is waiting for administrator to approve it, thank you.";
                    } else {
                        this.message2 = "";
                        this.message1 = "This e-mail don't exist.";
            }
                }
            } 
        } finally {
          if (rs!=null) rs.close();
          if (db!=null) db.close();  
        }
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }
    
    
    
    
    
}
