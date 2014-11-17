/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import bo.Helper;
import bo.PublicUser;
import bo.User;
import java.util.List;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Session session;

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

        if (Helper.loginUser(username, password)) {
            PublicUser user = new PublicUser(1337, "Ljung");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user);
            return "success";
        } else {
            return "failure";
        }
    }


}
