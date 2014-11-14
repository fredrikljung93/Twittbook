/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import org.hibernate.Query;

/**
 *
 * @author jonas_000
 */
public class UserController {
    private Object session;
    
    public UserController(){
        
    }
    
    public boolean login(String username, String password) {
        User user = null;

        try {
          //  org.hibernate.Transaction tx = session.beginTransaction();
         //   Query q = session.createQuery("from TUser as user where user.username='" + username + "' and user.password ='password");
            //user = (TUser) q.uniqueResult();
            if (user != null) {
                return true;
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
