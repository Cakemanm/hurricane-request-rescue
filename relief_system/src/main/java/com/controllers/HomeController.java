package com.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.relief.App;
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

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    void backWelcome(ActionEvent event) throws IOException {
        App.setRoot("welcome");
    }

    @FXML 
    void login(ActionEvent event) throws IOException {
        String userName = txt_username.getText();
        String password = txt_password.getText();

        ReliefFacade facade = new ReliefFacade();

        if (!facade.login(userName, password)) {
            // facade_error.setText("Invalid username or password");
            return;
        }
        System.out.println("Your username is " +userName+ " and your password is " +password);

        // App.setRoot("user_home");
    }
}