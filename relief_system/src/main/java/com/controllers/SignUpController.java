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

        // ReliefFacade facade = new ReliefFacade();

        // if (!facade.login(userName, password)) {
        //     // facade_error.setText("Invalid username or password");
        //     return;
        // }
        System.out.println("Your name is " +firstName+ " " +lastName+ ", username is " +userName+ " and your password is " +password);

        App.setRoot("home");
    }
}