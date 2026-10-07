package com.model;

public class Test {
    public static void main(String[] args) {
        ReliefFacade reliefFacade = new ReliefFacade();

        UserList userList = UserList.getInstance();
        System.out.println(userList.getUser("kyled").getAddress());

        reliefFacade.login("katKane", "brutus51");
        System.out.println(reliefFacade.getCurrentUser().getUsername());

        reliefFacade.logout();
    }
}
