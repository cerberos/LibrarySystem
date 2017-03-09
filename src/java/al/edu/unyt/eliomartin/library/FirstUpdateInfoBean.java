/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.validator.FacesValidator;

/**
 *
 * @author Coding
 */
@ManagedBean(name="firstUpdateInfoBean")
public class FirstUpdateInfoBean implements Serializable {
    private String password;
    private String confirmPassword;
    private User user = new User();
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
