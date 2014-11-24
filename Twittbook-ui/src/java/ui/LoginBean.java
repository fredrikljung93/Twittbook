package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    
    public String Login() {
        StoredUser user = new StoredUser("kalle","password",1);
        
        if(username.equals("kalle")){
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user);
            return "success";
        }
        else{
            return "failure";
        }
    }
    
       public String Logout() {
          FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
          return "logout";
    }


}
