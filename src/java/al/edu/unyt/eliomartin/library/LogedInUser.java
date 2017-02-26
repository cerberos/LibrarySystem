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
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Coding
 */
@ManagedBean(name="logedInUser")
@ReferencedBean
public class LogedInUser implements Serializable {
    int userID;
    int userTypeCode;
    String email;
    String name;
    String surname;
    String address;
    String phone;
    Date birthdate;
    String gender;
    boolean isLogin=false;

    public void isLogin() throws IOException {
        if (!this.isLogin) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/test.xhtml");
        }
    }
    
    public void login(int userid, String password) throws SQLException{
        String sql = "select * from logins where userID=? and Password=?";
        String sql2 = "select * from users where userID=?";
        Database db = new Database(sql);
        
        PreparedStatement st = db.getSt();
        st.setInt(1, userid);
        st.setString(2, password);
        ResultSet rs = db.getResults(st);
        
        if (rs.next()){
            this.isLogin = true;
            this.userID = rs.getInt("UserID");
            this.email = rs.getString("Email");
            this.userTypeCode = rs.getInt("UserTypeCode");
            
            db= new Database(sql2);
            st = db.getSt();
            st.setInt(1, this.userID);
            rs = db.getResults(st);
            
            if (rs.next()){
                this.name = rs.getString("Name");
                this.surname = rs.getString("Surname");
                this.address = rs.getString("Address");
                this.phone = rs.getString("Phone");
                this.birthdate = (Date)rs.getDate("Birday");
                this.gender = rs.getString("Gender");
            }
            
        }
        db.close();
    }
}
