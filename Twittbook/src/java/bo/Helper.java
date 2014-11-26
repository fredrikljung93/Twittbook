package bo;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;


//TODO: Check on login method and all commit() methods.
//EM login method is called emLoginMethod() at the moment.
public class Helper {

    public Helper() {
    }

    public static boolean publishPost(Integer userId, Date date, String message) {
        try {
            Post post;
            post = new Post(userId, date, message);

            Session session = (new Configuration().configure().
                    buildSessionFactory()).openSession();
            session.beginTransaction();

            session.save(post);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static User loginUser(String username, String password) {
        List result;
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        result = session.createQuery("from User as user where user.username='" + username + "' and user.password ='" + password + "'").list();

        if (result.size() > 0) {
            User tUser = (User) result.get(0);
            return tUser;
        } else {
            return null;
        }

    }

    public static User registerUser(String username, String password) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        Iterator result = session.createQuery("from User as user where user.username='" + username + "' and user.password ='" + password + "'").list().iterator();

        User user = new User(username, password);
        User tmp;
        while (result.hasNext()) {
            tmp = (User) result.next();

            if (tmp.getUsername().equals(user.getUsername())) {
                return null;
            }

        }

        session.save(user);
        session.getTransaction().commit();

        return getUser(user.getUsername());
    }

    public static boolean sendPrivateMessage(int senderId, int receiverId, Date date, String message) {
        try {
            Message msg;

            msg = new Message(senderId, receiverId, date, message);

            Session session = (new Configuration().configure().
                    buildSessionFactory()).openSession();
            session.beginTransaction();

            session.save(msg);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean postMessage(Integer username, Date date, String message) {

        try {
            Post post;
            post = new Post(username, date, message);

            Session session = (new Configuration().configure().
                    buildSessionFactory()).openSession();
            session.beginTransaction();

            session.save(post);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * @param receiverId
     * @return List of User Method to get a list of Users.
     */
    public static List<User> getMessageSenders(int receiverId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<User> list;// = entityManager.createNamedQuery("User.findAll").getResultList();

        Query q = entityManager.createQuery("from Message as m where m.receiver ='" + receiverId + "'");
        list = q.getResultList();
        entityManager.close();
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
        User user = (User) entityManager.createQuery("from User as u where u.id=" + userId + "").getSingleResult();
        entityManager.close();
        return user;
    }

    /**
     * @param Username
     * @return User This method returns a User with the specified username.
     */
    public static User getUser(String username) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<User> userlist = (List<User>) entityManager.createQuery("from User as user where user.username='" + username + "'").getResultList();
        entityManager.close();
        if(userlist.isEmpty()){
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
        List<Message> returnList = (List<Message>) entityManager.createQuery("from Message as message where message.receiver ='" + userId + "'").getResultList();
        entityManager.close();
        return returnList;

    }

    /**
     * @param userId
     * @return List of Message. Returns of Messages sent by a specified user.
     */
    public static List<Message> getMyOutbox(int userId) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Message> returnList = (List<Message>) entityManager.createQuery("from Message as message where message.sender ='" + userId + "'").getResultList();
        entityManager.close();
        return returnList;
    }

    /**
     * @param username
     * @param password
     * @return User Returns a User object if login is successful.
     */
    public static User emLoginUser(String username, String password) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        User user = (User) entityManager.createQuery("from User as user where user.username='" + username + "' and user.password ='" + password + "'").getSingleResult();
        entityManager.close();

        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
