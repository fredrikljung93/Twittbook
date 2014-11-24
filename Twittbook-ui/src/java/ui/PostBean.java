/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
<<<<<<< HEAD:Twittbook-ui/src/java/ui/PostBean.java
import java.util.ArrayList;
=======

import bo.Helper;
import bo.Post;
import bo.User;
import java.util.ArrayList;
import java.util.Collections;
>>>>>>> b466a4e971f9f988aa6431ae171f819b2716368e:Twittbook/src/java/ui/PostBean.java
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
        return "success";
    }

<<<<<<< HEAD:Twittbook-ui/src/java/ui/PostBean.java
    public static List<PostBean> getPostsFromUser(int userid) {
        List<PostBean> list = new ArrayList<PostBean>();
        
       PostBean post = new PostBean();
       post.setUser(1);
       post.setMessage("Ett meddelande");
       post.setDate(new Date());
       post.setId(1);
       list.add(post);
        
        return list;
=======
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

>>>>>>> b466a4e971f9f988aa6431ae171f819b2716368e:Twittbook/src/java/ui/PostBean.java
    }

}
