module main.software2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens main.software2 to javafx.fxml;
    exports main.software2;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    exports helper;

    //opens javafx.base;
}