package com.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Optional;

public class UserList {
    private static UserList USER_LIST;
    private ArrayList<User> users = DataLoader.getUsers();

    private UserList(){
        users = DataLoader.getUsers();
    }

    public static UserList getInstance(){
        if(USER_LIST == null)
            USER_LIST = new UserList();
        return USER_LIST;
    }

    public User getUser(String username){
        for(User user : users){
            if(username.equals(user.getUsername()))
                return user;
        }
        return null;
    }

    public User getUser(String username, String password){
        for(User user : users){
            if(user.isMatch(username, password))
                return user;
        }
        System.out.println("Your username or password is incorrect");
        return null;
    }

    public User getUserById(UUID id){
        for(User user : users){
            if(user.getID().equals(id))
                return user;
        }
        return null;
    }

    public void addUser(User user){
            users.add(user);
    }
}
