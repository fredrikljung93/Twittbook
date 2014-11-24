/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author Fredrik
 */
public class StoredUser {
    private String username;
    private String password;
    
    public StoredUser(String username, String password, int id){
        this.username=username;
        this.password=password;
        this.id=id;
    }

    public StoredUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int id;
    
       @Override
    public boolean equals(Object other){
        StoredUser user=(StoredUser)other;
        
        return user.getId()==id;
    }
    
}
