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
    
    Login login= new Login();
    User user= new User();
    
    boolean isLogin=false;
    
    int loginid;
    String loginpass;
    
    public String tryLogin() throws SQLException{
        if(this.isLogin)
            return "index";
        return null;
    }
    
    public Boolean loginValidation() throws SQLException, ClassNotFoundException
    {
        Database db=new Database("select * from logins where email=? and password=?");
        ResultSet rs=db.getSelect();
        if(rs.next())
        {
            
            login= new Login();
            login.setUserID(rs.getInt("userid"));
            login.setEmail(rs.getString("email"));
            login.setActive(rs.getInt("active"));       
            login.setUserTypeCode(rs.getInt("UserTypeCode"));
            db.close();
            rs.close();
            if(login.getActive()==1)
            {
            db=new Database("select * from users where userid=?");
            db.getSt().setInt(1, login.getUserID());
            rs=db.getSelect();
            user.setAddress(rs.getString("address"));
            user.setBirthDate(rs.getDate("birthdate"));
            user.setGender(rs.getString("gender"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("telephone"));
            user.setSurname(rs.getString("surname"));
            return true;
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your account requires activation!"));
                return false;
            }
            
        }
            
        else
            return false;
    }
    
    public String loginCheck() throws SQLException, ClassNotFoundException
    {
        if(loginValidation())
        {
            if(login.getUserTypeCode()==0)
                return "admin_page";
            if(login.getUserTypeCode()==1)
                return "librarian_page";
            if(login.getUserTypeCode()==2)
                return "instructor_page";
            if(login.getUserTypeCode()==3)
                return "student_page"; 
        }
        
        return null;
            
    }

    public void isLogin() throws IOException {
        if (!this.isLogin) {
            try{
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/test.xhtml");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.err.print(e.hashCode());
            }
        }
        else {
            try{
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.err.print(e.hashCode());
            }
        }
            
    }
    
    private void login(int userid, String password) throws SQLException{
        String sql = "select * from logins where userID=? and Password=?";
        String sql2 = "select * from users where userID=?";
        Database db=null;
        ResultSet rs=null;
        
        try{
        db = new Database(sql);
        PreparedStatement st = db.getSt();
        st.setInt(1, userid);
        st.setString(2, password);
        rs = db.getSelect();
        
        if (rs.next()){
            this.login = new Login(rs.getInt("UserID"), rs.getString("Email"), 
                    rs.getInt("UserTypeCode"), rs.getInt("active"));
            this.isLogin = true;
            
            db= new Database(sql2);
            st = db.getSt();
            st.setInt(1, this.login.getUserID());
            rs = db.getSelect();
            
            if (rs.next())
                this.user = new User(rs.getString("Name"),rs.getString("Surname"),
                        rs.getString("Address"),rs.getString("Phone"),rs.getDate("Birday"),
                        rs.getString("Gender"));

        }
        } catch (Exception e){
            System.err.print(e.hashCode());
        }finally {
            if (rs!=null) rs.close();
            if (db!=null) db.close();
        }
    }
    
    
//    public void isActive(){
//    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        try {
//            ec.redirect(ec.getRequestContextPath()
//                    + "example.xhtml");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            System.err.print(e.hashCode());
//        }
//    }

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
    
    
    
}
