package controller;

import dao.AppointmentQuery;
import dao.ContactsQuery;
import dao.CustomerQuery;
import dao.UserQuery;
import helper.AppointmentTimes;
import helper.InformationAlert;
import helper.TimezoneConversions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contacts;
import model.Customers;
import model.Users;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * This class contains all the logic for the add appointment form
 */
public class AddAppointmentController extends TimezoneConversions implements Initializable {
    /**
     * The combo box that holds the start times in LocalTime
     */
    @FXML
    public ComboBox<LocalTime> aaStartTimeCbox;
    /**
     * The combo box that holds the end times in LocalTime
     */
    @FXML
    public ComboBox<LocalTime> aaEndTimeCbox;
    /**
     * The combo box that holds the customer IDs
     */
    @FXML
    public ComboBox<Customers> aaCustomerIdCbox;
    /**
     * The combo box that holds the user IDs
     */
    @FXML
    public ComboBox<Users> aaUserIdCbox;
    /**
     * The DatePicker that holds the available dates to choose from
     */
    public DatePicker aaDatepicker;
    @FXML
    private TextField aaAptidTxt;
    @FXML
    private Button aaCancelB;
    /**
     * The combo box that holds the contacts name and ID
     */
    @FXML
    private ComboBox<Contacts> aaContactCbox;
    @FXML
    private TextField aaDescTxt;
    @FXML
    private TextField aaLocationTxt;
    @FXML
    private Button aaSaveB;
    @FXML
    private TextField aaTitleTxt;
    @FXML
    private TextField aaTypeTxt;

    /**
     * This method initializes the add appointment form by populating all the combo boxes and text fields in the proper format
     * @param url root path for the view appointments list form
     * @param resourceBundle resources used to localize the view appointments list form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //variable that grabs the start time from the timezoneConversions class to prevent the core value from changing
        LocalTime aaBeginningTime = beginningTime;
        ObservableList<Contacts> contactsList = ContactsQuery.selectAllContacts();
        ObservableList<Customers> customersList = CustomerQuery.selectAllCustomers();
        ObservableList<Users> usersList = UserQuery.selectAllUsers();

        aaContactCbox.setItems(contactsList);
        aaCustomerIdCbox.setItems(customersList);
        aaUserIdCbox.setItems(usersList);

        aaEndTimeCbox.setPromptText("Please choose a start time first..");
        //clears start and end time cbox everytime the page is loaded before it is populated with available times
        aaStartTimeCbox.valueProperty().set(null);
        aaStartTimeCbox.getItems().removeAll(aaStartTimeCbox.getItems());
        aaEndTimeCbox.valueProperty().set(null);
        aaEndTimeCbox.getItems().removeAll(aaEndTimeCbox.getItems());
        while(aaBeginningTime.isBefore(endingTime.minusMinutes(30).plusSeconds(1))) {
            aaStartTimeCbox.getItems().add(aaBeginningTime);
            aaBeginningTime = aaBeginningTime.plusMinutes(30);
        }
    }

    /**
     * This method populates the end time combobox based on the start time selected
     * @param actionEvent a selection made on the start time combobox
     */
    public void onAaStartTimeCbox(ActionEvent actionEvent) {
        aaEndTimeCbox.valueProperty().set(null);
        aaEndTimeCbox.getItems().removeAll(aaEndTimeCbox.getItems());
        LocalTime startTime = aaStartTimeCbox.getSelectionModel().getSelectedItem();
        while(startTime.isBefore(endingTime.minusMinutes(30).plusSeconds(1))) {
            aaEndTimeCbox.getItems().add(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
        }
    }

    /**
     * This method takes the user back to the view appointments list form and does not save any data
     * @param actionEvent The cancel button
     * @throws IOException input/output exception
     */
    @FXML
    void onAaCancelB(ActionEvent actionEvent) throws IOException {
        Parent viewAppointmentListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewAppointmentListForm.fxml"));
        Scene viewAppointmentListScene = new Scene(viewAppointmentListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewAppointmentListScene);
        window.show();
    }

    /**
     * This method performs validation logic on all input data and saves all correct data input from the comboboxes,
     * text fields, and datepicker into the database by calling the insertAppointment query
     * @param event the save button
     * @throws IOException input/output exception
     */
    @FXML
    void onAaSaveB(ActionEvent event) throws IOException {
        //gets information from all fields
        String title = aaTitleTxt.getText();
        String desc = aaDescTxt.getText();
        String location = aaLocationTxt.getText();
        String type = aaTypeTxt.getText();
        //datepicker and time validation before converting to localdatetime
        if(aaDatepicker.getValue() == null) {
            new InformationAlert(
                    "Please select a valid date.",
                    "Error");
            return;
        }
        if(aaStartTimeCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please pick a valid start time.",
                    "Error");
            return;
        }
        if(aaEndTimeCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please pick a valid end time.",
                    "Error");
            return;
        }
        LocalDate date = aaDatepicker.getValue();
        //convert start and end times to LocalDateTime(include the date with the time)
        LocalTime startTime = aaStartTimeCbox.getValue();
        LocalTime endTime = aaEndTimeCbox.getValue();
        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = LocalDateTime.of(date, endTime);
        //combo boxes validation before grabbing the selected item
        if(aaCustomerIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid customer ID.",
                    "Error");
            return;
        }
        if(aaUserIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid user ID.",
                    "Error");
            return;
        }
        if(aaContactCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid contact.",
                    "Error");
            return;
        }
        long customerId = aaCustomerIdCbox.getValue().getCustomerID();
        long userId = aaUserIdCbox.getValue().getUserID();
        long contactId = aaContactCbox.getValue().getContactID();
        //appointment text fields validation
        if(title.equals("")) {
            new InformationAlert(
                    "Please enter a valid title.",
                    "Error");
            return;
        }
        if(desc.equals("")) {
            new InformationAlert(
                    "Please enter a valid description.",
                    "Error");
            return;
        }
        if(location.equals("")) {
            new InformationAlert(
                    "Please enter a valid location.",
                    "Error");
            return;
        }
        if(type.equals("")) {
            new InformationAlert(
                    "Please enter a valid type.",
                    "Error");
            return;
        }
        //appointment time and date validation
        LocalDate today = LocalDateTime.now().toLocalDate();
        int compareDate = date.compareTo(today);
        if(compareDate < 0){
            Alert pastDate = new Alert(Alert.AlertType.ERROR);
            pastDate.setContentText("Appointments cannot be scheduled before today's date.");
            pastDate.showAndWait();
            return;
        }
        int compareTime = startTime.compareTo(endTime);
        if(compareTime > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The start time must be before the end time.");
            alert.showAndWait();
            return;
        }
        if(!estConversion(start) || !estConversion(end)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Appointment times are not within company hours.");
            alert.showAndWait();
            return;
        }
        if(AppointmentTimes.isApptOverlapping(start, end, customerId, Long.parseLong(aaAptidTxt.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: An appointment within this timeframe already exists. Please choose a different time.");
            alert.showAndWait();
            return;
        }

        AppointmentQuery.insertAppointment(title, desc, location, type, start, end, customerId, userId, contactId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Appointment info saved.");
        alert.show();
        //directs user back to main appointment form
        Parent viewAppointmentListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewAppointmentListForm.fxml"));
        Scene viewAppointmentListScene = new Scene(viewAppointmentListForm);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewAppointmentListScene);
        window.show();
    }

}
