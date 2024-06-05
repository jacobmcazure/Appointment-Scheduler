package main.software2;

import DBConnection.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class creates an app that manages customers, appointment scheduling, and contacts
 * @author Jacob McEwen
 */
public class MainApplication extends Application {
    /**
     * This is the start method that launches the javafx application's first screen
     * @param stage The stage for loading the form
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginScreenForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method that gets called when you run the java program
     * @param args array of string args
     */
    public static void main(String[] args) {
        DBConnection.openConnection();
        launch();
        DBConnection.closeConnection();

    }
}