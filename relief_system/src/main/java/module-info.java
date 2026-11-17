module com.relief {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires junit;
    requires javafx.base;

    opens com.relief to javafx.fxml;
    exports com.relief;

    opens com.model to javafx.fxml;
    exports com.model;

    opens com.controllers to javafx.fxml;
}
