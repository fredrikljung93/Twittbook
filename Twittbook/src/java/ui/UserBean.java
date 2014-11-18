/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.Helper;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jonas_000
 */
@ManagedBean(name="user")
@SessionScoped
public class UserBean {
    private int id;
    private String username;
    
    public UserBean(int id, String username){
        this.id=id;
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
    
      public static ArrayList<UserBean> getLista(){
       return (ArrayList<UserBean>) Helper.getAllUsers();
    }
}
