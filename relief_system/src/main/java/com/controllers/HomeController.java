package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import com.model.*;

public class HomeController {
    @FXML private Text display_firstName;
    @FXML private Text display_lastName;
    @FXML private Text display_username;

    private ReliefFacade facade;
    private User user;

    @FXML
    private void initialize() {
        facade = ReliefFacade.getInstance();
        user = facade.getCurrentUser();

        if (user == null) {
            System.out.println("No user is currently logged in.");
            return;
        }

        display_firstName.setText("Welcome " + user.getFirstName() + "!");
        display_lastName.setText("Your last name is " + user.getLastName() + ".");
        display_username.setText("Your username is " + user.getUsername() + ".");

        System.out.println("Your username is " + user.getUsername() + " and your password is " + user.getPassword());
    }
}