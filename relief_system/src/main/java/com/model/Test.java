package com.model;

public class Test {
    public static void main(String[] args) {
        UserList userList = UserList.getInstance();
        System.out.println(userList.getUser("kyled").getAddress());
    }
}
