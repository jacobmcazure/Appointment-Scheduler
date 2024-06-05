package controller;

import helper.AppointmentTimes;
import helper.LoginAuthentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static helper.TimezoneConversions.sysDefZoneId;

/**
 * This class contains logic for the main menu screen
 */

public class MainMenuScreenController implements Initializable {
    public Button viewCustomerListB;
    public Button viewAppointmentListB;
    public Button logoutB;
    public Button viewReportsB;
    public Label userZoneId;
    @FXML
    private Button appointmentListB;
    @FXML
    private Button customerListB;
    @FXML
    private Text welcomeTxt;

    /**
     * This method initializes the main menu form by setting the zone Id of the user
     * @param url root path for login screen form
     * @param resourceBundle resources used to localize the login screen form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String zoneIdValue = sysDefZoneId.getId();
        userZoneId.setText(zoneIdValue);
    }

    /**
     * This method receives the username entered in the login screen and calls a method, checking for upcoming appointments
     * @param text the username
     */
    public void sendUsername(String text) {
        welcomeTxt.setText("Welcome back, " + text + "!");
        AppointmentTimes.upcomingAppt(text, LoginAuthentication.getValidUserId());
    }

    /**
     * This method navigates the user to the view customers list form
     * @param actionEvent view customer list button
     * @throws IOException input/output exception
     */
    public void onViewCustomerListB(ActionEvent actionEvent) throws IOException {
        Parent viewCustomerListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewCustomerListForm.fxml"));
        Scene viewCustomerScene = new Scene(viewCustomerListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewCustomerScene);
        window.show();
    }

    /**
     * This method takes the user to the view appointments list form
     * @param actionEvent view appointments list form
     * @throws IOException input/output exception
     */
    public void onViewAppointmentListB(ActionEvent actionEvent) throws IOException {
        Parent viewAppointmentListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewAppointmentListForm.fxml"));
        Scene viewAppointmentScene = new Scene(viewAppointmentListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewAppointmentScene);
        window.show();
    }

    /**
     * Upon confirmation, this method takes the user back to the login screen
     * @param actionEvent the logout button
     * @throws IOException input/output exception
     */
    public void onLogoutB(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?");
        alert.setTitle("Log out");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent loginScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/LoginScreenForm.fxml"));
            Scene loginScreenScene = new Scene(loginScreenForm);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(loginScreenScene);
            window.show();
        }
    }

    /**
     * This method takes the user to the view reports screen
     * @param actionEvent view reports button
     * @throws IOException input/output exception
     */
    public void onViewReportsB(ActionEvent actionEvent) throws IOException {
        //switches to reports screen
        Parent reportsScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/ReportsScreenForm.fxml"));
        Scene reportsScreenScene = new Scene(reportsScreenForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(reportsScreenScene);
        window.show();
    }

}
