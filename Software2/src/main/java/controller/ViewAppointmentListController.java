package controller;

import helper.ConfirmationAlert;
import helper.InformationAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class contains logic for the view appointments list class
 */
public class ViewAppointmentListController extends AppointmentFilter implements Initializable {
    @FXML
    public TableView<Appointments> AppointmentListTable;
    @FXML
    public TableColumn<Appointments, Integer> aptAppointmentidCol;
    @FXML
    public TableColumn<Appointments, String> aptTitleCol;
    @FXML
    public TableColumn<Appointments, String> aptDescriptionCol;
    @FXML
    public TableColumn<Appointments, String> aptLocationCol;
    @FXML
    public TableColumn<Appointments, Integer> aptContactCol;
    @FXML
    public TableColumn<Appointments, String> aptTypeCol;
    @FXML
    public TableColumn<Appointments, LocalDateTime> aptStartdateCol;
    @FXML
    public TableColumn<Appointments, LocalDateTime> aptEnddateCol;
    @FXML
    public TableColumn<Appointments, String> aptCustomeridCol;
    @FXML
    public TableColumn<Appointments, Integer> aptUseridCol;
    @FXML
    public RadioButton aptAllT;
    @FXML
    public ToggleGroup aptTgroup;
    @FXML
    public RadioButton aptMonthlyT;
    @FXML
    public RadioButton aptWeeklyT;
    @FXML
    public Button onAptListAddAppointmentB;
    @FXML
    public Button aptListUpdateAppointmentB;
    @FXML
    public Button aptListDeleteB;
    @FXML
    public Label aptErrorLbl;
    @FXML
    public Button appointmentsListBackB;
    @FXML
    public Button onAppointmentsListBackB;

    /**
     * This initializes the view appointments list form by loading and populating the table view and setting the
     * radio button toggles
     * @param url root path for the view appointments list form
     * @param resourceBundle resources used to localize the view appointments list form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentListTable.setItems(selectAllAppointments());

        aptAppointmentidCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartdateCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        aptEnddateCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        aptCustomeridCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        aptUseridCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        aptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        //sets the all appointments radio button to selected
        aptAllT.setSelected(true);
    }

    /**
     * This method takes the user back to the main menu
     * @param actionEvent back button clicked
     * @throws IOException input/output exception
     */
    public void onAppointmentsListBackB(ActionEvent actionEvent) throws IOException {
        Parent welcomeScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/MainMenuScreenForm.fxml"));
        Scene welcomeScreenScene = new Scene(welcomeScreenForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(welcomeScreenScene);
        window.show();
    }

    /**
     * This method sets the tableview for all appointments
     * @param actionEvent All toggle button clicked
     */
    public void onAptAllT(ActionEvent actionEvent) {
        AppointmentListTable.setItems(selectAllAppointments());
    }

    /**
     * This method sets the tableview for all appointments in the current month
     * @param actionEvent Monthly toggle button clicked
     */
    public void onAptMonthlyT(ActionEvent actionEvent) {
        AppointmentListTable.setItems(selectMonthly());
    }

    /**
     * This method sets the tableview for all appointments in the current week
     * @param actionEvent Weekly toggle button clicked
     */
    public void onAptWeeklyT(ActionEvent actionEvent) {
        AppointmentListTable.setItems(selectWeekly());
    }

    /**
     * This method takes the user to the add appointment form
     * @param actionEvent add appointment button clicked
     * @throws IOException
     */
    public void onAptListAddAppointmentB(ActionEvent actionEvent) throws IOException {
        Parent addAppointmentScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/AddAppointmentForm.fxml"));
        Scene addAppointmentScreenScene = new Scene(addAppointmentScreenForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(addAppointmentScreenScene);
        window.show();
    }

    /**
     * This method collects data from the selected appointment and takes the user to the update appointment form
     * @param actionEvent the update apopintment button clicked
     * @throws IOException input/output exception
     */
    public void onAptListUpdateAppointmentB(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/software2/UpdateAppointmentForm.fxml"));
            Parent updateAppointmentScene = loader.load();

            UpdateAppointmentController uaController = loader.getController();
            uaController.sendApt(AppointmentListTable.getSelectionModel().getSelectedItem());

            Scene updateApptFormScene = new Scene(updateAppointmentScene);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(updateApptFormScene);
            window.show();
        } catch (NullPointerException e) {
            new InformationAlert("No appointment selected.","Selection Error");
        }
    }

    /**
     * This method alerts the user for deleting an appointment using lambda alertOMatic and upon confirmation,
     * deletes the chosen appointment
     * @param actionEvent delete button clicked
     */
    public void onAptListDeleteB(ActionEvent actionEvent) {
        Appointments delAppt = AppointmentListTable.getSelectionModel().getSelectedItem();
        if(delAppt == null) {
            new InformationAlert("No appointment selected.","Selection Error");
            return;
        }
        ConfirmationAlert deleteAlert = ConfirmationAlert.alertOMatic.makeConfirmationAlert
                ("WARNING: This will delete the selected appointment. Are you sure?",
                    "Delete Appointment");
        Optional<ButtonType> result = deleteAlert.getTheResult();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteAppointment(delAppt.getApptID());
            new InformationAlert
                    ("Appointment of ID: " + delAppt.getApptID() +
                            " and of Type: " + delAppt.getType() +
                            " has been successfully deleted.",
                            "");
        }
        //refreshes table
        AppointmentListTable.setItems(selectAllAppointments());
    }

}