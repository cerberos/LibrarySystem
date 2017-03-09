/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Coding
 */
@ManagedBean(name="firstUpdateInfoBean")
public class FirstUpdateInfoBean implements Serializable {
    private String password;
    private User user = new User();
    private int userid;
    private String text ="";
    
    public void submit() throws SQLException, ClassNotFoundException{
        String sql1 =  "update logins set password=? where userid=?";
        String sql2 = "insert into users (userid, name, surname, address, phone, birthdate, gender) values (?,?,?,?,?,?,?)";
        Database db=new Database(sql1);
        db.getSt().setString(1, this.password);
        db.getSt().setInt(2, userid);
        if(db.update()){
            Date birth = new Date(user.getBirthDate().getTime());
            db=new Database(sql2);
            db.getSt().setInt(1, userid);
            db.getSt().setString(2, user.getName());
            db.getSt().setString(3, user.getSurname());
            db.getSt().setString(4, user.getAddress());
            db.getSt().setString(5, user.getPhone());
            db.getSt().setDate(6, birth);
            db.getSt().setString(7, user.getGender());
            if(db.update()){
                    text = "Password: " + password + "<br/>";
                    text+= "Name: " + user.getName() + "<br/>";
                    text+= "Surname: " + user.getSurname() + "<br/>";
                    text+= "Address: " + user.getAddress() + "<br/>";
                    text+= "Phone nr.: " + user.getPhone() + "<br/>";
                    text+= "Birth Date: " + user.getBirthDate() + "<br/>";
                    text+= "Gender: " + user.getGender() + "<br/>";
            } else {
                text = "User exist.";
            }
        } else {
            text = "This user do not exist!";
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
