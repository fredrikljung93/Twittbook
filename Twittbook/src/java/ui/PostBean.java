/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.Helper;
import bo.Post;
import bo.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "PostBean")
@RequestScoped
public class PostBean {
    

    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
    private int id;
    private String message;
    private Date date;
    private int user;

    /**
     * Creates a new instance of PostBean
     */
    public PostBean() {
    }
    
    public PostBean(int id,Date date,int user,String message){
        this.id = id;
        this.date=date;
        this.user=user;
        this.message=message;
    }

    public String createPost(String message) {
        if (message == null) {
            System.out.println("Create post received null");
            return "";
        }
        System.out.println("Create post received " + message);
        if (message.equals("")) {
            return "";
        }
        date = new Date(System.currentTimeMillis());
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        System.out.println(user.getUsername() + ": " + message);
        Helper.publishPost(user.getId(), date, message);
        return "success";
    }

    public static List<PostBean> getPostsFromUser(int userId) {
        ArrayList<PostBean> returnList = new ArrayList();
        List<Post> list = Helper.getFeed(userId);

        PostBean post;
        for (Post p : list) {
            post = new PostBean(p.getId(),p.getDate(),p.getUser(),p.getMessage());
            returnList.add(post);

        }
        Collections.reverse(returnList);
        return returnList;

    }

}
