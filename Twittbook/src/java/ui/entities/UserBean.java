package ui.entities;

import bo.Helper;
import bo.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "UserBean")
@SessionScoped 
public class UserBean implements Serializable {

    private int id;
    private String username;

    public UserBean() {
    }

    public UserBean(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ArrayList<UserBean> getAllUsers() {
        List<User> list = Helper.getAllUsers();
        ArrayList<UserBean> returnList = new ArrayList();
        
        for(User u:list){
            returnList.add(new UserBean(u.getId(),u.getUsername()));
        }
        
        return returnList;
    }

    public static UserBean getUser(int userId) {
        User user = Helper.getUser(userId);
        return new UserBean(user.getId(),user.getUsername());
    }

}
