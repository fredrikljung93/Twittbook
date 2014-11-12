/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jonas_000
 */
public class Helper {
     Session session = null;

    public Helper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    
    public List getAllUsers(){
        List<TUser> list = null;
        
        {
           try{
               org.hibernate.Transaction tx = session.beginTransaction();
               Query q = session.createQuery("from TUser");
               
           }catch(Exception e){
               e.printStackTrace();
           }
        }
        return list;
    }
    
}
