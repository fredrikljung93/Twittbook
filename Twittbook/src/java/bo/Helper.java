/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

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
        List<TUser> list = null;

        return list;
    }

    public static boolean loginUser(String username, String password) {
        List result;
        Session session = (new Configuration().configure().
                buildSessionFactory()).openSession();
        session.beginTransaction();
        result = session.createQuery("from TUser as user where user.username='" + username + "' and user.password ='" + password + "'").list();
        if (result.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

}
