package bo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import service.Constants;

public class Helper {
    
    private static final String API_KEY = Constants.getAPIKey();
    private static final String GCM_URL = "https://android.googleapis.com/gcm/send";
    
    public static String sendPushNotice(int rId) {

        //httppost.addHeader(message, subject);
        User user = getUser(rId);
        if (user.getDeviceid() != null) {
            String url = GCM_URL;
            
            try {
                HttpClient client = HttpClientBuilder.create().build();
                
                HttpPost httppost = new HttpPost(url);
                List<NameValuePair> nameValuePairs = new ArrayList();

                //httppost.addHeader("Content-Type","json");
                httppost.addHeader("Authorization:", "key=" + API_KEY);
                
                nameValuePairs.add(new BasicNameValuePair("registration_ids", user.getDeviceid()));
                
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "json");
                System.out.println("Chosen content type: " + entity.getContentType());
                httppost.setEntity(entity);
                
                HttpResponse response = null;
                try {
                    response = client.execute(httppost);
                } catch (IOException ex) {
                    System.out.println("IOEXCEPTION");
                    System.out.println("Exception message: " + ex.getMessage());
                }
                HttpEntity resEntity = response.getEntity();
                return response.toString();
            } catch (UnsupportedEncodingException ex) {
                //Logger.getLogger(RestHelper.class.getName()).log(Level.SEVERE, null, ex);
                return "failure";
            }

            /* URL conObj;
             try {
             conObj = new URL(url);
             HttpURLConnection con = (HttpsURLConnection) conObj.openConnection();
             con.setRequestMethod("POST");
             con.setRequestProperty(url, url);
             } catch (MalformedURLException ex) {
             Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
             Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
             }*/
        }
        return null;
    }

    /**
     * @Deprecated this method is not yet complete and should not be used.
     */
    public static String sendPushNotice2(int rId) {
        //TODO fill in body (regid) when doing post and send it to google gcm.
        User user = getUser(rId);
        
        if (user.getDeviceid() != null) {
            
            try {
                HttpURLConnection conn = (HttpURLConnection) getConnection(GCM_URL);
                
                conn.setDoOutput(true);
                conn.setUseCaches(false);

                //byte[] bytes = body.getBytes();
                //conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestMethod("POST");
                //conn.setRequestProperty("Content-Type", API_KEY);
                conn.setRequestProperty("Authorization", "key=" + API_KEY);
                
                OutputStream out = conn.getOutputStream();
            } catch (SQLException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ProtocolException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    public static boolean updateDeviceId(String username, String deviceid) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            User user = getUser(username);
            
            user.setDeviceid(deviceid);
            
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            
            entityManager.getTransaction().commit();
            
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
    
    public Helper() {
    }

    /**
     * Method to register a new user user.
     *
     * @param username
     * @param password
     * @return returns the reigstered user if successful
     */
    public static User registerUser(String username, String password) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            
            User user = new User(username, password);
            
            List<User> list = getAllUsers();
            
            for (User u : list) {
                if (username.toUpperCase().equals(u.getUsername().toUpperCase())) {
                    return null;
                }
            }
            
            entityManager.getTransaction().begin(); //Prepares transaction
            entityManager.persist(user); //choose what to save

            entityManager.getTransaction().commit(); //commits transaction

            return getUser(username); //returns a new instance of the registered user.
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) { //does rollback if commit fails.
                entityManager.getTransaction().rollback();
                return null;
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * @param userId User's id in DB.
     * @param description User's description to be updated.
     * @return boolean returns true if successful.
     */
    public static boolean changeUserDescription(int userId, String description) {
        
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            User user = getUser(userId);
            
            user.setDescription(description);
            
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            
            entityManager.getTransaction().commit();
            
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Method to commit a post to User feed
     *
     * @param userId FK in db
     * @param date current date set by Web Service
     * @param message message being posten at userId's wall
     * @return returns true if successfull.
     */
    public static boolean publishPost(Integer userId, Date date, String message) {
        
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            Post post = new Post(userId, date, message);
            entityManager.getTransaction().begin();
            entityManager.persist(post);
            
            entityManager.getTransaction().commit();
            
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Method to commit a private message.
     *
     * @param senderId id PK of user sending message.
     * @param receiverId id PK of user receiving message.
     * @param date date set by Web Service.
     * @param msg message being sent by senderId.
     * @return returns true if successful.
     */
    public static boolean sendPrivateMessage(int senderId, int receiverId, Date date, String msg) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            Message message = new Message(senderId, receiverId, date, msg);
            entityManager.getTransaction().begin();
            entityManager.persist(message);
            
            entityManager.getTransaction().commit();
            
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            throw e;
        } finally {
            entityManager.close();
        }
        
    }
    
    public static boolean sendPrivateMessage(int senderId, int receiverId, Date date, String msg, String subject) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            
            Message message = new Message(senderId, receiverId, date, msg, subject);
            entityManager.getTransaction().begin();
            entityManager.persist(message);
            
            entityManager.getTransaction().commit();
            
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            throw e;
        } finally {
            entityManager.close();
        }
        
    }

    /**
     * @param receiverId
     * @return List of User Method to get a list of Users.
     */
    public static List<User> getMessageSenders(int receiverId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<User> list;
        
        @SuppressWarnings("JPQLValidation")
        Query q = entityManager.createQuery("from Message as m where m.receiver ='" + receiverId + "'"); //Creates query
        list = q.getResultList(); //Gets list from query to db
        entityManager.close(); // closing connection.
        return list;
        
    }

    /**
     * Returns a list of all users.
     *
     * @return List of User
     */
    public static List<User> getAllUsers() {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<User> list;
        
        Query q = entityManager.createQuery("from User u");
        list = q.getResultList();
        entityManager.close();
        return list;
        
    }

    /**
     * Returns posts from a specific user.
     *
     * @param userId
     * @return List of Post
     */
    public static List getFeed(int userId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        List<Post> list = entityManager.createQuery("from Post as p where p.user ='" + userId + "'").getResultList();
        entityManager.close();
        return list;
        
    }

    /**
     * @param userId
     * @return User This method returns a User with the specified userId
     */
    public static User getUser(int userId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        User user = (User) entityManager.createQuery("from User as u where u.id=" + userId + "").getSingleResult();
        entityManager.close();
        return user;
    }

    /**
     * @param messageId ID of Message in DB.
     * @return Message object instanced from DB. Method used to recover a single
     * Message from DB.
     */
    public static Message getMessage(int messageId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        Message message = (Message) entityManager.createQuery("from Message as m where m.id=" + messageId + "").getSingleResult();
        entityManager.close();
        return message;
    }

    /**
     * @param username
     * @return User This method returns a User with the specified username.
     */
    public static User getUser(String username) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        List<User> userlist = (List<User>) entityManager.createQuery("from User as user where user.username='" + username + "'").getResultList();
        entityManager.close();
        if (userlist.isEmpty()) {
            return null;
        }
        return userlist.get(0);
    }

    /**
     * @param userId
     * @return List of Message. returns a list of Messages by a specified user.
     */
    public static List<Message> getMyInbox(int userId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        List<Message> returnList = (List<Message>) entityManager.createQuery("from Message as message where message.receiver ='" + userId + "'").getResultList();
        entityManager.close();
        return returnList;
        
    }

    /**
     * @param userId
     * @return List of Message. returns a list of Messages sent or received by
     * specific user.
     */
    public static List<Message> getNewMessages(int userId, int minid) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        List<Message> returnList = (List<Message>) entityManager.createQuery("from Message as message where message.id>" + minid + " and (message.receiver ='" + userId + "' or message.sender='" + userId + "')").getResultList();
        entityManager.close();
        return returnList;
        
    }

    /**
     * @param userId
     * @return List of Message. Returns of Messages sent by a specified user.
     */
    public static List<Message> getMyOutbox(int userId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        @SuppressWarnings("JPQLValidation")
        List<Message> returnList = (List<Message>) entityManager.createQuery("from Message as message where message.sender ='" + userId + "'").getResultList();
        entityManager.close();
        return returnList;
    }

    /**
     * @param username
     * @param password
     * @return User Returns a User object if login is successful.
     */
    public static User loginUser(String username, String password) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        User user = (User) entityManager.createQuery("from User as user where user.username='" + username + "' and user.password ='" + password + "'").getSingleResult();
        entityManager.close();
        
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * @param userId User's id in DB.
     * @param deviceid User's description to be updated.
     * @return boolean returns true if successful.
     */
    public static boolean setGcmRegId(String username, String deviceid) {
        
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            User user = getUser(username);
            
            user.setDeviceid(deviceid);
            
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
