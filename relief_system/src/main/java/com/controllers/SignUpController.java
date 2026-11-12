package com.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.relief.App;
import com.model.*;

public class SignUpController {

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    private TextField txt_firstName;

    @FXML
    private TextField txt_lastName;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    void backWelcome(ActionEvent event) throws IOException {
        App.setRoot("welcome");
    }

    @FXML 
    void signup(ActionEvent event) throws IOException {
        String firstName = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String userName = txt_username.getText();
        String password = txt_password.getText();

        ReliefFacade facade = new ReliefFacade();
        User newUser;

        // newUser = facade.createAccount(userName, firstName, lastName, null, null, password, null, null, false, 0, null);
        // System.out.println("New user created: " + newUser.getUsername() + ", " + newUser.getFirstName() + " " + newUser.getLastName());
        System.out.println(firstName + " " + lastName + ": Valid signup attempt for user: " + userName + " with password: " + password);

        App.setRoot("home");
    }
}