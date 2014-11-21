/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.Helper;
import bo.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "MessageBean")
@RequestScoped
public class MessageBean {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
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
    
    private int id;
    private int sender;
    private List<String> receivers;
    private String message;

    public List<String> getReceiver() {
        return receivers;
    }

    public void setReceiver(List<String> receivers) {
        this.receivers = receivers;
    }
    private Date date;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
    }

    public String CreateMessage() {
        date = new Date(System.currentTimeMillis());
        UserBean sender = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        for(String s:receivers){
            Helper.sendPrivateMessage(sender.getId(), Integer.parseInt(s), date, message);
        }
        return "success";
    }
    
    public List<UserBean> getMessageSenders(){
        UserBean receiver = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        List<User> senders = Helper.getMessageSenders(receiver.getId());
        
        List<UserBean> beanSenders = new ArrayList<UserBean>();
        
        for(User u:senders){
            UserBean bean = new UserBean();
            bean.setId(u.getId());
            bean.setUsername(u.getUsername());
            beanSenders.add(bean);
        }
        return beanSenders;
    }

}
