package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShelterCardController {

    @FXML private Label nameLabel;
    @FXML private Label travelTimeLabel;
    @FXML private Label spacesLabel;
    @FXML private Label petFriendlyLabel;
    @FXML private Label medicalLabel;
    @FXML private Label accessibleLabel;
    public void setData(
            String name,
            String travelTime,
            int openSpaces,
            boolean petFriendly,
            boolean medical,
            boolean accessible) {

        nameLabel.setText(name);
        travelTimeLabel.setText(travelTime);
        spacesLabel.setText(openSpaces + " open spaces");

        petFriendlyLabel.setVisible(petFriendly);
        petFriendlyLabel.setManaged(petFriendly);

        medicalLabel.setVisible(medical);
        medicalLabel.setManaged(medical);

        accessibleLabel.setVisible(accessible);
        accessibleLabel.setManaged(accessible);
    }
}