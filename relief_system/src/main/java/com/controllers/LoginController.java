package com.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.relief.App;

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
    void back(ActionEvent event) throws IOException {
        App.setRoot("welcome");
    }

    @FXML 
    void login(ActionEvent event) {
        String userName = txt_username.getText();
        String password = txt_password.getText();
        System.out.println("Your username is " +userName+ " and your password is " +password);
    }
}