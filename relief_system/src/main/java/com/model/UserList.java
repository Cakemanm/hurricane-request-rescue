package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * Manages user accounts through a singleton instance.
 */
public class UserList {
    private static UserList USER_LIST;
    private ArrayList<User> users;

    /**
     * Loads the users from storage and prints the number loaded.
     */
    private UserList(){
        users = DataLoader.getUsers();
        System.out.println("Users loaded: " + users.size());
    }

    /**
     * Retrieves the singleton instance, creating it if necessary.
     * @return the shared UserList instance
     */
    public static UserList getInstance(){
        if(USER_LIST == null)
            USER_LIST = new UserList();
        return USER_LIST;
    }

    /**
     * Finds a user with the specified username.
     * @param username the username to search for
     * @return the matching user, or {@code null} if none is found
     */
    public User getUser(String username){
        for(User user : users){
            if(username.equals(user.getUsername()))
                return user;
        }
        return null;
    }

    /**
     * Finds a user whose credentials match the supplied username and password.
     * Prints an error message if no matching user is found.
     *
     * @param username the account username
     * @param password the account password
     * @return the matching user, or {@code null} if the credentials do not match
     */
    public User getUser(String username, String password){
        for(User user : users){
            if(user.isMatch(username, password))
                return user;
        }
        System.out.println("Your username or password is incorrect");
        return null;
    }

    /**
     * Finds a user with the specified ID.
     * @param id the user ID to search for
     * @return the matching user, or {@code null} if none is found
     */
    public User getUserById(UUID id){
        for(User user : users){
            if(user.getID().equals(id))
                return user;
        }
        return null;
    }

    /**
     * Adds a user to the list without saving it to storage.
     * @param user the user to add
     */
    public void addUser(User user){
            users.add(user);
    }

    /**
     * Retrieves the underlying list of users.
     * @return the user list
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Saves the current user list to storage.
     */
    public void save() {
        DataWriter.saveUsers(users);
    }
}
