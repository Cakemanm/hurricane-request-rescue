package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.relief.App;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


public class WelcomeController {

    @FXML
    void switchToLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchToSignUp(ActionEvent event) throws IOException {
        App.setRoot("signup");
    }
}
