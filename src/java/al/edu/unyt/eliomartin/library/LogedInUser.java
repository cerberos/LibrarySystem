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
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Coding
 */
@ManagedBean(name="logedInUser")
@SessionScoped
public class LogedInUser implements Serializable {
    
    private Login login= new Login();
    private User user= new User();
    private String newPasswd;
    private Boolean isLogin=false;
    private int loginid;
    private String loginpass;
    private boolean isFirstLogin= false;
    
    
    public Boolean loginValidation() throws SQLException, ClassNotFoundException
    {
        
        Database db=new Database("select * from logins where userid=? and password=?");
        db.getSt().setInt(1, this.loginid);
        db.getSt().setString(2, this.loginpass);
        ResultSet rs=db.getSelect();
        if(rs.next())
        {
            
            login= new Login();
            login.setUserID(loginid);
            login.setEmail(rs.getString("email"));
            login.setActive(rs.getInt("active"));       
            login.setUserTypeCode(rs.getInt("UserTypeCode"));
            isLogin=true;
            db.close();
            rs.close();
            if(login.getActive()==1)
            {
                db=new Database("select * from users where userid=?");
                db.getSt().setInt(1, login.getUserID());
                rs=db.getSelect();
                
                if(rs.next()){
                    user.setAddress(rs.getString("address"));
                    user.setBirthDate(rs.getDate("birthdate"));
                    user.setGender(rs.getString("gender"));
                    user.setName(rs.getString("name"));
                    user.setPhone(rs.getString("phone"));
                    user.setSurname(rs.getString("surname"));

                    return true;
                } else {
                    isFirstLogin = true;
                }
                return true;
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your account requires activation!"));
                isLogin=false;
                return false;
            }
            
        }
            
        else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong username or password!"));
            isLogin=false;
            return false;
        }
    }
    
    public String loginCheck() throws SQLException, ClassNotFoundException
    {
        if(loginValidation())
        {   
            if (!isFirstLogin)
                return "index";
            else
                return "firstUpdateInfo";
        }
        
        return null;
            
    }
    
    public void changePasswd() throws SQLException, ClassNotFoundException
    {
       Database db=new Database("UPDATE logins SET password=? where userid=?");
       db.getSt().setString(1, newPasswd);
       db.getSt().setInt(2, loginid);
       
       if(db.update())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password changed succesfully!"));
       else
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password changing failed!"));
    }

    public void isLogin() throws IOException {
        if (!this.isLogin) {
            try{
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.err.print(e.hashCode());
            }
        }
    }
    
    public void isFirstLime() throws IOException {
        if (this.isFirstLogin) {
            try{
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/firstUpdateInfo.xhtml");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.err.print(e.hashCode());
            }
        }
    }
    
    
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }
    
    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }
    

     
    
}
