/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import al.edu.unyt.eliomartin.library.databaseconnection.Database;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Coding
 */
@ManagedBean(name="instructorAccessApplyBean")
public class InstructorAccessApplyBean implements Serializable {
    
    
    String sql = "select count(*) from system_access_requests where UserID=?";
    private Login login = null;
    
    public String haveRequested(){
        return "";
    }
    
    

}
