package com.controllers;

import java.io.IOException;
import java.util.UUID;

import com.model.ReliefFacade;
import com.model.Request;
import com.model.RequestFeed;
import com.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ExpandRequestController {
    ReliefFacade reliefFacade = ReliefFacade.getInstance();
    RequestFeed requestFeed = RequestFeed.getInstance();
    Request request = requestFeed.getRequest(UUID.fromString("1a1b7554-1c8b-48e0-9962-5c7c6599ca3b"));

    @FXML private Text title;
    @FXML private Text status;
    @FXML private Text dateSubmitted;
    @FXML private Text helpNeededFor;
    @FXML private Text priority;
    @FXML private Text urgencyText;
    @FXML private ProgressBar urgencyBar;

    @FXML
    void initialize() throws IOException, InterruptedException{
        title.setText(request.getTitle());
        status.setText(request.getStatus().label);
        
        String urgencyTextString = "";
        int urgency = request.getRequestPriority();
        if(urgency >= 8){
            urgencyTextString = "High Urgency - " + urgency;
        } else if(urgency >= 5){
            urgencyTextString = "Medium Urgency - " + urgency;
        } else {
            urgencyTextString = "Low Urgency - " + urgency;
        }
        urgencyText.setText(urgencyTextString);
        urgencyBar.setProgress(request.getRequestPriority() / 10.0);
    }
}
