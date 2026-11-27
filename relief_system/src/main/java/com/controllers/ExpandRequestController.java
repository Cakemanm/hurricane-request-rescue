package com.controllers;

import java.io.IOException;
import java.util.UUID;

import com.model.ReliefFacade;
import com.model.Request;
import com.model.RequestFeed;
import com.model.RequestStatus;
import com.model.User;
import com.relief.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class ExpandRequestController {
    ReliefFacade reliefFacade = ReliefFacade.getInstance();
    RequestFeed requestFeed = RequestFeed.getInstance();
    Request request = requestFeed.getRequest(UUID.fromString("88eb85df-d238-4a83-a0f5-e5aa5948d6ff"));
    User user = reliefFacade.getCurrentUser();;

    @FXML
    private Text title;
    @FXML
    private Text status;
    @FXML
    private Text urgencyText;
    @FXML
    private ProgressBar urgencyBar;
    @FXML
    private Text address;
    @FXML
    private Text phoneNumber;
    @FXML
    private Text volunteer;
    @FXML
    private Text description;
    @FXML
    private FlowPane requestTypesPane;
    @FXML private Label claimRequest;

    @FXML
    void initialize() throws IOException, InterruptedException {
        if(user == null){
            user = reliefFacade.getUser("kyled");
        }
        title.setText(request.getTitle());
        status.setText(request.getStatus().label);
        address.setText(request.getAddress().toString());
        phoneNumber.setText(request.getRequester().getPhoneNumber());
        volunteer.setText(request.getResponder() == null ? "Unassigned"
                : request.getResponder().getFullName() + ", " + request.getResponder().getPhoneNumber());
        description.setText(request.getCommentsFull());

        String urgencyTextString = "";
        int urgency = request.getRequestPriority();
        if (urgency >= 8) {
            urgencyTextString = "High Urgency - " + urgency;
        } else if (urgency >= 5) {
            urgencyTextString = "Medium Urgency - " + urgency;
        } else {
            urgencyTextString = "Low Urgency - " + urgency;
        }
        urgencyText.setText(urgencyTextString);
        urgencyBar.setProgress(request.getRequestPriority() / 10.0);
        String urgencyColor;

        if (urgency >= 8) {
            urgencyColor = "#e44436";
        } else if (urgency >= 5) {
            urgencyColor = "#e5ab42";
        } else {
            urgencyColor = "#68b984";
        }

        urgencyText.setFill(javafx.scene.paint.Color.web(urgencyColor));
        urgencyBar.setStyle("-fx-accent: " + urgencyColor + "; -fx-padding: 0;");

        // Remove the example badges defined in the FXML.
        requestTypesPane.getChildren().clear();

        if (request.getRequestTypes() != null) {
            for (var requestType : request.getRequestTypes()) {
                Label badge = new Label(requestType.label);

                badge.setStyle(
                        "-fx-background-color: #223f70;" +
                                "-fx-background-radius: 10;" +
                                "-fx-border-color: #4269a2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-padding: 6 9;" +
                                "-fx-text-fill: #edf2fc;" +
                                "-fx-font-size: 12px;");

                badge.setWrapText(true);
                badge.maxWidthProperty().bind(requestTypesPane.widthProperty());

                requestTypesPane.getChildren().add(badge);
            }
        }
        if(!user.isVolunteer()){
            claimRequest.setVisible(false);
            claimRequest.setManaged(false);
        }
    }

    @FXML
    void home(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    void backRequests(ActionEvent event) throws IOException {
        App.setRoot("my-requests");
    }

    @FXML 
    void claimRequest(ActionEvent event) throws IOException {
        if(user.isVolunteer()){
            request.setResponder(user);
            request.setStatus(RequestStatus.ASSIGNED);
            user.addToRequestList(request);
            App.setRoot("my-requests");
        }
    }

}
