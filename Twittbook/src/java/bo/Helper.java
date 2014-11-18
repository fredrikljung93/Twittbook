/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import ui.UserBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author jonas_000
 */
//TODO: Make querys that returns 1 unique result when possible / best.
public class Helper {

    public Helper() {
    }

    public static User getUser(String username) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        Iterator result = session.createQuery("from User as user where user.username='" + username + "'").list().iterator();

        User user;
        while (result.hasNext()) {
            user = (User) result.next();

            if (user.getUsername().equals(username)) {
                return user;
            }

        }

        return null;
    }

    public static User getUser(int id) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        Iterator result = session.createQuery("from User as user where user.Id='" + id + "'").list().iterator();

        User user;
        while (result.hasNext()) {
            user = (User) result.next();

            if (user.getId() == id) {
                return user;
            }

        }

        return null;
    }

    public static UserBean getPublicUser(String username) {
        UserBean user;

        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from User").list().iterator();
        session.getTransaction().commit();

        User tmpUser;
        while (result.hasNext()) {
            tmpUser = (User) result.next();

            if (tmpUser.getUsername().equals(username)) {
                user = new UserBean(tmpUser.getId(), tmpUser.getUsername());
                return user;
            }
        }

        return null;
    }

    public static UserBean getPublicUser(int id) {
        UserBean user;

        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from User").list().iterator();
        session.getTransaction().commit();

        User tmpUser;
        while (result.hasNext()) {
            tmpUser = (User) result.next();

            if (tmpUser.getId() == id) {
                user = new UserBean(tmpUser.getId(), tmpUser.getUsername());
                return user;
            }
        }

        return null;
    }

    public static List getAllUsers() {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from User").list().iterator();
        session.getTransaction().commit();

        ArrayList<UserBean> list = new ArrayList<>();
        User user;
        UserBean pu;
        while (result.hasNext()) {
            user = (User) result.next();
            pu = new UserBean(user.getId(), user.getUsername());

            list.add(pu);

        }
        return list;
    }

    public static UserBean loginUser(String username, String password) {
        List result;
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        result = session.createQuery("from User as user where user.username='" + username + "' and user.password ='" + password + "'").list();

        if (result.size() > 0) {
            User tUser = (User) result.get(0);
            UserBean user = new UserBean(tUser.getId(), tUser.getUsername());
            return user;
        } else {
            return null;
        }

    }

    public static UserBean registerUser(String username, String password) {
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

        return getPublicUser(user.getUsername());
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

    public static List getMyInbox(int userId) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from Message as message where message.receiver ='" + userId + "'").list().iterator();
        session.getTransaction().commit();

        ArrayList<Message> list = new ArrayList<>();

        Message message;
        while (result.hasNext()) {
            message = (Message) result.next();
            list.add(message);

        }
        return list;
    }

    public static List getMySentMessages(int userId) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from Message as message where message.sender ='" + userId + "'").list().iterator();
        session.getTransaction().commit();

        ArrayList<Message> list = new ArrayList<>();

        Message message;
        while (result.hasNext()) {
            message = (Message) result.next();
            list.add(message);

        }
        return list;
    }

    public static List getFeed(int userId) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from Post as post where post.user ='" + userId + "'").list().iterator();
        session.getTransaction().commit();

        ArrayList<Post> list = new ArrayList<>();

        Post post;
        while (result.hasNext()) {
            post = (Post) result.next();
            list.add(post);

        }
        return list;
    }

}
