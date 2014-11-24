/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jonas_000
 */
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
<<<<<<< HEAD:Twittbook-ui/src/java/ui/UserBean.java
        ArrayList<UserBean> users= new ArrayList<UserBean>();
        
        UserBean user = new UserBean(1, "kalle");
        users.add(user);
        
        return users;
=======

        List<User> list = Helper.getAllUsers();
        ArrayList<UserBean> returnList = new ArrayList();
      
        UserBean user;
        for (User u : list) {
            user = new UserBean(u.getId(),u.getUsername());
            returnList.add(user);

        }
        return returnList;
        //return (ArrayList<UserBean>) Helper.getAllUsers();
>>>>>>> b466a4e971f9f988aa6431ae171f819b2716368e:Twittbook/src/java/ui/UserBean.java
    }

    public static UserBean getUser(int userid) {
        return new UserBean(1, "kalle");
    }

}
