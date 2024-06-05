package controller;

import helper.LoginAuthentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static helper.TimezoneConversions.sysDefZoneId;

/**
 * This class contains logic for the login screen
 */

public class LoginScreenController implements Initializable {
    /**
     * Resource bundle used for translation information
     */
    ResourceBundle rb;
    @FXML
    public Text welcomeTxt;
    @FXML
    public Button loginExitB;
    @FXML
    public Label userZoneId;
    public Text username;
    public Text password;
    @FXML
    private Button loginB;
    @FXML
    private PasswordField pwTxtField;
    @FXML
    private TextField userTxtField;

    /**
     * This method initializes the login screen with resource bundle translations and sets the zone Id
     * @param url root path for the start of the program
     * @param resourceBundle resources used to localize the form
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
        rb = ResourceBundle.getBundle("Nat",Locale.getDefault());
            welcomeTxt.setText(rb.getString("welcome"));
            loginExitB.setText(rb.getString("Exit"));
            loginB.setText(rb.getString("Login"));
            username.setText(rb.getString("username"));
            password.setText(rb.getString("password"));
        } catch(MissingResourceException e) {
            e.printStackTrace();
        }
        String zoneIdValue = sysDefZoneId.getId();
        userZoneId.setText(zoneIdValue);
    }

    /**
     * This method authenticates the username and password entered, writes login attempts to the login_activity.txt
     * file, and navigates the user to the main menu screen upon a successful login
     * @param event The login button clicked
     * @throws IOException input/output exception
     */
    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        String username = userTxtField.getText();
        String password = pwTxtField.getText();

        if(!LoginAuthentication.validateUserAndPw(username, password)) {
            printWriter.println("unsuccessful login by user: " + username +
                    " and password: " + password +
                    " at date and time of: " + Timestamp.valueOf(LocalDateTime.now()));
            printWriter.close();

            userTxtField.clear();
            pwTxtField.clear();

            Alert e = new Alert(Alert.AlertType.ERROR,rb.getString("Incorrect"));
            e.setTitle("");
            e.setHeaderText(rb.getString("LoginError"));
            e.showAndWait();
            return;
        }

        printWriter.println("successful login by user: " + username +
                " and password: " + password +
                " at date and time of: " + Timestamp.valueOf(LocalDateTime.now()));
        printWriter.close();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/software2/MainMenuScreenForm.fxml"));
        Parent welcomeScreen = loader.load();

        Scene LoginScreenFormScene = new Scene(welcomeScreen);


        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(LoginScreenFormScene);
        window.show();

        MainMenuScreenController WSController = loader.getController();
        WSController.sendUsername(userTxtField.getText());
    }

    /**
     * This method closes the program upon clicking the exit button
     * @param actionEvent exit button clicked
     */
    public void onLoginExitB(ActionEvent actionEvent) {
        Stage exit = (Stage) loginExitB.getScene().getWindow();
        exit.close();
    }

}