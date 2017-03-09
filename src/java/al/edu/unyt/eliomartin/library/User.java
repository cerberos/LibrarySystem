/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package al.edu.unyt.eliomartin.library;

import java.util.Date;
import java.util.Calendar;


/**
 *
 * @author Coding
 */
public class User {
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Date birthDate = new Date(Calendar.getInstance().getTime().getTime());
    private String gender = "m";
    
    public User()
    {
        
    }
    public User(String name, String surname, String address, 
            String phone, Date birthDate, String gender){
        this.setName(name);
        this.setSurname(surname);
        this.setAddress(address);
        this.setPhone(phone);
        this.setBirthDate(birthDate);
        this.setGender(gender);
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
}
