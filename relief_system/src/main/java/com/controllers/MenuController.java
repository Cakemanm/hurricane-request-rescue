package com.controllers;

import java.io.IOException;

import com.model.ReliefFacade;
import com.model.User;
import com.model.UserList;
import com.relief.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuController {
    @FXML private Text nameOfUser;
    ReliefFacade reliefFacade;
    User user;

    @FXML
    private void initialize(){
        UserList users = UserList.getInstance();
        reliefFacade = ReliefFacade.getInstance();
        user = users.getUser("kyled");
        //user = reliefFacade.getCurrentUser();

        nameOfUser.setText(user.getFullName()); 
    }
    

    @FXML
    void home(ActionEvent event) throws IOException{
        App.setRoot("home");
    }

    @FXML
    void myRequests(ActionEvent event) throws IOException{
        //App.setRoot("");
    }

    @FXML
    void logout(ActionEvent event) throws IOException{
        reliefFacade.logout();
        App.setRoot("welcome");
    }

        /*@FXML 
    void login(ActionEvent event) throws IOException {
        String userName = txt_username.getText();
        String password = txt_password.getText();

        ReliefFacade facade = ReliefFacade.getInstance();

        if (!facade.login(userName, password)) {
            // facade_error.setText("Invalid username or password");
            return;
        }
        System.out.println("Valid login attempt for user: " + userName + " with password: " + password);

        App.setRoot("home");
    }*/

}
