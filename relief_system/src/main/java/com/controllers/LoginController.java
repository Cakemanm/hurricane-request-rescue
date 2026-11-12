package com.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.relief.App;
import com.model.*;

public class LoginController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("welcome");
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
        System.out.println("Valid login attempt for user: " + userName + " with password: " + password);

        App.setRoot("home");
    }
}