package bo;
// Generated 2014-nov-12 11:07:51 by Hibernate Tools 4.3.1



/**
 * TFriends generated by hbm2java
 */
public class TFriends  implements java.io.Serializable {


     private Integer id;
     private int user;
     private int friend;
     private Boolean accepted;

    public TFriends() {
    }

	
    public TFriends(int user, int friend) {
        this.user = user;
        this.friend = friend;
    }
    public TFriends(int user, int friend, Boolean accepted) {
       this.user = user;
       this.friend = friend;
       this.accepted = accepted;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getUser() {
        return this.user;
    }
    
    public void setUser(int user) {
        this.user = user;
    }
    public int getFriend() {
        return this.friend;
    }
    
    public void setFriend(int friend) {
        this.friend = friend;
    }
    public Boolean getAccepted() {
        return this.accepted;
    }
    
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }




}

