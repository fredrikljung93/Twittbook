package bo;
// Generated 2014-nov-12 11:07:51 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "t_post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
    @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id"),
    @NamedQuery(name = "Post.findByUser", query = "SELECT p FROM Post p WHERE p.user = :user"),
    @NamedQuery(name = "Post.findByDate", query = "SELECT p FROM Post p WHERE p.date = :date"),
    @NamedQuery(name = "Post.findByMessage", query = "SELECT p FROM Post p WHERE p.message = :message")})
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user")
    private int user;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "message")
    private String message;

    public Post() {
    }

	
    public Post(Integer user, Date date) {
        this.user = user;
        this.date = date;
    }
    public Post(Integer user, Date date, String message) {
       this.user = user;
       this.date = date;
       this.message = message;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUser() {
        return this.user;
    }
    
    public void setUser(Integer user) {
        this.user = user;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }




}


