/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import java.util.ArrayList;
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

    private int id;

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
    private String message;
    private Date date;
    private int user;

    /**
     * Creates a new instance of PostBean
     */
    public PostBean() {
    }

    public String createPost(String message) {
        if(message==null){
            System.out.println("Create post received null");
            return "";
        }
         System.out.println("Create post received "+message);
        if(message.equals("")){
            return "";
        }
        return "success";
    }

    public static List<PostBean> getPostsFromUser(int userid) {
        List<PostBean> list = new ArrayList<PostBean>();
        
       PostBean post = new PostBean();
       post.setUser(1);
       post.setMessage("Ett meddelande");
       post.setDate(new Date());
       post.setId(1);
       list.add(post);
        
        return list;
    }

}
