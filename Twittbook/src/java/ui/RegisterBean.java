/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.Helper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "RegisterBean")
@RequestScoped
public class RegisterBean {
    
    private String username;
    private String password;
    private String email;
    private String repeatedPassword;
    
    /**
     * Creates a new instance of RegisterBean
     */
    public RegisterBean() {
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
    
    public String Register() {
        
        if(!password.equals(repeatedPassword)){
          return "unmatchedpasswords";
        }
        return "success";
/*
        if (Helper.registerUser(username,password)) {
            return "success?faces-redirect=true";
        } else {
            return "failure?faces-redirect=true";
        }*/
    }
    
}
