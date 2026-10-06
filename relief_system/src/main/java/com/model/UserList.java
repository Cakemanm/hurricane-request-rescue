package com.model;

import java.util.ArrayList;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList(){
        users = DataLoader.getUsers();
    }
    public static UserList getInstance(){
        return userList;
    }

    public User getUser(String username){
        for(User user : users){
            if(username == user.getUsername())
                return user;
        }
        return null;
    }

}
