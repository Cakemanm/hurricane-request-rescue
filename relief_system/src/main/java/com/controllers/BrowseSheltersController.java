package com.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.location.MapTools;
import com.model.ReliefFacade;
import com.model.Shelter;
import com.model.ShelterList;
import com.model.ShelterType;
import com.model.User;
import com.relief.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * TODO: fix search function, look into the wonky driving times, implement home button
 */

public class BrowseSheltersController {
    @FXML private ToggleButton petFriendlyButton;
    @FXML private ToggleButton medicalButton;
    @FXML private ToggleButton accessibleButton;

    boolean needsMedicalStaff = false;
    boolean needsPetFriendly = false;
    boolean needsAccessibility = false;
    ReliefFacade reliefFacade = ReliefFacade.getInstance();
    User user = reliefFacade.getCurrentUser();
    int groupSize = 1;
    ShelterList shelterList = ShelterList.getInstance();

    @FXML
    void initialize() throws IOException, InterruptedException{
        if(user == null){
        user = reliefFacade.getUser("kyled");
        }
        groupSize = user.getHouseholdSize();
        ArrayList<Shelter> shelters = shelterList.findShelters(user, groupSize, needsMedicalStaff, needsPetFriendly, needsAccessibility);
        displayShelters(shelters);

        updateCriteriaButton(petFriendlyButton, "Pet Friendly");
        updateCriteriaButton(medicalButton, "Medical Staff");
        updateCriteriaButton(accessibleButton, "Accessible");
    }
    


@FXML
private VBox shelterContainer;

private void displayShelters(ArrayList<Shelter> shelters) throws IOException, InterruptedException {
    // Remove results from the previous search.
    shelterContainer.getChildren().clear();
    
    if (shelters == null || shelters.size() == 0) {
        Label message = new Label(
            "No shelters match your criteria. Try changing your filters."
        );
        message.setWrapText(true);
        message.setStyle("-fx-text-fill: #8eb5d8;");

        shelterContainer.getChildren().add(message);
        return;
    }

    for (Shelter shelter : shelters) {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/relief/helper-files/shelter-card.fxml")
        );

        VBox card = loader.load();

        ShelterCardController controller = loader.getController();

        boolean isPetFriendly = false;
        boolean hasMedicalStaff = false;
        boolean isAccessible = false;
        for(ShelterType type : shelter.getShelterTypes()){
            if(type == ShelterType.PET_FRIENDLY)
                isPetFriendly = true;
            else if(type == ShelterType.DISABILITY_ACCESSIBLE)
                isAccessible = true;
            else if(type == ShelterType.MEDICAL_STAFF)
                hasMedicalStaff = true;
        }
        MapTools mt = MapTools.getInstance();
        int drivingTime = (int)Math.round(mt.getDrivingTime(mt.getCoordinates(user.getAddress()), mt.getCoordinates(shelter.getAddress())));
        //only for testing purposes, delete once screens are all working together

        
        // Fill this card using the current shelter's information.
        // Replace these example getter names with your actual methods.
        controller.setData(
            shelter.getName(),
            drivingTime + " min drive",
            shelter.getCapacity() - shelter.getOccupancy(),
            isPetFriendly,
            hasMedicalStaff,
            isAccessible
        );

        shelterContainer.getChildren().add(card);
    }
}

@FXML
void toggleMedicalStaff(ActionEvent event) throws IOException, InterruptedException{
    needsMedicalStaff = (needsMedicalStaff ? false : true);
    displayShelters(shelterList.findShelters(user, groupSize, needsMedicalStaff, needsPetFriendly, needsAccessibility));
    updateCriteriaButton(medicalButton, "Medical Staff");
}

@FXML
void togglePetFriendly(ActionEvent event) throws IOException, InterruptedException{
    needsPetFriendly = (needsPetFriendly ? false : true);
    displayShelters(shelterList.findShelters(user, groupSize, needsMedicalStaff, needsPetFriendly, needsAccessibility));
    updateCriteriaButton(petFriendlyButton, "Pet Friendly");
}

@FXML
void toggleAccessibility(ActionEvent event) throws IOException, InterruptedException{
    needsAccessibility = (needsAccessibility ? false : true);
    displayShelters(shelterList.findShelters(user, groupSize, needsMedicalStaff, needsPetFriendly, needsAccessibility));
    updateCriteriaButton(accessibleButton, "Accessible");
}

@FXML 
void search(ActionEvent event) throws IOException, InterruptedException {
    String searchKey = ((TextField)event.getSource()).getText();
    ArrayList<Shelter> searchResults = shelterList.searchSheltersByName(searchKey);
    displayShelters(searchResults);
}

@FXML
void home(ActionEvent event) throws IOException{
    App.setRoot("home");
}

@FXML
void menu(ActionEvent event) throws IOException{
    App.setRoot("menu");
}

@FXML
private void updateCriteriaButton(ToggleButton button, String title) {
    boolean selected = button.isSelected();

    button.setText((selected ? "- " : "+ ") + title);

    String background = selected ? "#254b82" : "#181b1b";
    String textColor = selected ? "#ffffff" : "#8eb5d8";

    button.setStyle(
        "-fx-background-color: " + background + ";" +
        "-fx-text-fill: " + textColor + ";" +
        "-fx-background-radius: 18;" +
        "-fx-font-size: 15;" +
        "-fx-cursor: hand;"
    );
}

}
