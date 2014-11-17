/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author jonas_000
 */
public class Helper {

    public Helper() {
    }

    public List getAllUsers() {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();

        Iterator result = session.createQuery("from TUser").list().iterator();
        session.getTransaction().commit();

        ArrayList<PublicUser> list = new ArrayList<>();
        User user;
        PublicUser pu;
        while (result.hasNext()) {
            user = (User) result.next();
            pu = new PublicUser(user.getId(), user.getUsername());

            list.add(pu);

        }
        return list;
    }

    public static boolean loginUser(String username, String password) {
        List result;
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        result = session.createQuery("from User as user where user.username='" + username + "' and user.password ='" + password + "'").list();
        if (result.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean registerUser(String username, String password) {
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        Iterator result = session.createQuery("from TUser as user where user.username='" + username + "' and user.password ='" + password + "'").list().iterator();

        User user = new User(username, password);
        User tmp;
        while (result.hasNext()) {
            tmp = (User) result.next();

            if (tmp.getUsername().equals(user.getUsername())) {
                return false;
            }

        }
        //TODO: Create tuple with new user data.
        session.save(user);
        session.getTransaction().commit();

        return true;
    }

    public boolean sendPrivateMessage(int sender, int receiver, Date date, String message) {
        try {
            TMessage msg;

            msg = new TMessage(sender, receiver, date, message);

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

    public boolean postMessage(String user, Date date, String message) {

        try {
            Post post;
            post = new Post(user, date, message);

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

}
