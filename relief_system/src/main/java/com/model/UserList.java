package com.model;

import java.util.ArrayList;

public class UserList {
    private static UserList USER_LIST;
    private ArrayList<User> users = DataLoader.getUsers();

    private UserList(){}

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
            if(username.equals(user.getUsername()) && password.equals(user.getPassword()))
                return user;
        }
        System.out.println("Your username or password is incorrect");
        return null;
    }

}
