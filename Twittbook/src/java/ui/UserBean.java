/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.Helper;
import bo.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jonas_000
 */
@ManagedBean(name = "user")
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

        List<User> list = Helper.EMGetAllUsers();
        ArrayList<UserBean> returnList = new ArrayList();
      
        UserBean user;
        for (User u : list) {
            user = new UserBean(u.getId(),u.getUsername());
            returnList.add(user);

        }
        return returnList;
        //return (ArrayList<UserBean>) Helper.getAllUsers();
    }

    public static UserBean getUser(int userid) {
        User user = Helper.getUser(userid);
        if (user == null) {
            System.out.println("User är null!");
            return null;
        }
        System.out.println("User är inte null!");
        UserBean bean = new UserBean();
        bean.setId(user.getId());
        bean.setUsername(user.getUsername());
        return bean;
    }

}
