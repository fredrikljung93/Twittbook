/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.Helper;
import bo.PublicUser;
import bo.PublicUser;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "TestBean")
@SessionScoped
public class Test {
    
    
    public static ArrayList<PublicUser> getLista(){
       return (ArrayList<PublicUser>) Helper.getAllUsers();
    }
}
