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
@Table(name = "t_message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findBySender", query = "SELECT m FROM Message m WHERE m.sender = :sender"),
    @NamedQuery(name = "Message.findByReceiver", query = "SELECT m FROM Message m WHERE m.receiver = :receiver"),
    @NamedQuery(name = "Message.findByDate", query = "SELECT m FROM Message m WHERE m.date = :date"),
    @NamedQuery(name = "Message.findByMessage", query = "SELECT m FROM Message m WHERE m.message = :message")})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sender")
    private int sender;
    @Basic(optional = false)
    @Column(name = "receiver")
    private int receiver;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "message")
    private String message;
    public Message() {
    }

    public Message(int sender, int receiver, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
    }

    public Message(int sender, int receiver, Date date, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.message = message;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSender() {
        return this.sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return this.receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
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
