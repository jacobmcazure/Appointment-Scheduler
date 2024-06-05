package controller;

import dao.AppointmentQuery;
import dao.ContactsQuery;
import dao.CustomerQuery;
import dao.UserQuery;
import helper.AppointmentTimes;
import helper.InformationAlert;
import helper.TimezoneConversions;
import javafx.collections.FXCollections;
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
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * This class contains all the logic for the update appointment form
 */
public class UpdateAppointmentController extends TimezoneConversions implements Initializable {
    /**
     * The combo box that holds the start times
     */
    @FXML
    public ComboBox<LocalTime> uaStartTimeCbox;
    /**
     * The combo box that holds the end times
     */
    @FXML
    public ComboBox<LocalTime> uaEndTimeCbox;
    /**
     * The combo box that holds the customer ID
     */
    public ComboBox<Customers> uaCustomerIdCbox;
    /**
     * The combo box that holds the users ID
     */
    public ComboBox<Users> uaUserIdCbox;
    /**
     * The DatePicker that holds the available dates to choose from
     */
    public DatePicker uaDatepicker;
    @FXML
    private TextField uaAptidTxt;
    @FXML
    private Button uaCancelB;
    /**
     * The combo box that holds the contacts name and ID
     */
    @FXML
    private ComboBox<Contacts> uaContactCbox;
    @FXML
    private TextField uaDescTxt;
    @FXML
    private TextField uaLocationTxt;
    @FXML
    private Button uaSaveB;
    @FXML
    private TextField uaTitleTxt;
    @FXML
    private TextField uaTypeTxt;

    /**
     * This method initializes the start and end time combo boxes with times
     * @param url root path for the view appointments list form
     * @param resourceBundle resources used to localize the view appointments list form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalTime uaBeginningTime = beginningTime;
        uaStartTimeCbox.getItems().removeAll(uaStartTimeCbox.getItems());
        uaEndTimeCbox.getItems().removeAll(uaEndTimeCbox.getItems());
        while(uaBeginningTime.isBefore(endingTime.minusMinutes(30).plusSeconds(1))) {
            uaStartTimeCbox.getItems().add(uaBeginningTime);
            uaBeginningTime = uaBeginningTime.plusMinutes(30);
        }

        LocalTime startTime = beginningTime;
        while(startTime.isBefore(endingTime.minusMinutes(30).plusSeconds(1))) {
            uaEndTimeCbox.getItems().add(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
        }
    }

    /**
     * This method transfers the data selected from the appointments table in the ViewAppointmentListController
     * into the text fields, combo boxes and date picker
     * @param selectedItem the item selected from the view appointments list table
     */
    public void sendApt(Appointments selectedItem) {
        ObservableList<Contacts> contactsList = ContactsQuery.selectAllContacts();
        ObservableList<Customers> customersList = CustomerQuery.selectAllCustomers();
        ObservableList<Users> usersList = UserQuery.selectAllUsers();


        uaContactCbox.setItems(contactsList);
        uaCustomerIdCbox.setItems(customersList);
        uaUserIdCbox.setItems(usersList);
        uaAptidTxt.setText(String.valueOf(selectedItem.getApptID()));
        uaTitleTxt.setText(selectedItem.getTitle());
        uaDescTxt.setText(selectedItem.getDescription());
        uaLocationTxt.setText(selectedItem.getLocation());
        uaTypeTxt.setText(selectedItem.getType());
        uaDatepicker.setValue(selectedItem.getStartTime().toLocalDate());
        uaStartTimeCbox.setValue(selectedItem.getStartTime().toLocalTime());
        uaEndTimeCbox.setValue(selectedItem.getEndTime().toLocalTime());
        for(Contacts c: contactsList) {
            if(c.getContactID() == selectedItem.getContactID()) {
                uaContactCbox.setValue(c);
            }
        }
        for(Customers cu: customersList) {
            if(cu.getCustomerID() == selectedItem.getCustomerID()) {
                uaCustomerIdCbox.setValue(cu);
            }
        }
        for(Users u : usersList) {
            if(u.getUserID() == selectedItem.getUserID()) {
                uaUserIdCbox.setValue(u);
            }
        }
    }

    /**
     * This method takes the user back to the view appointments list screen and does not save any data
     * @param actionEvent The cancel button
     * @throws IOException Input/output exception
     */
    @FXML
    void onUaCancelB(ActionEvent actionEvent) throws IOException {
        Parent viewAppointmentListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewAppointmentListForm.fxml"));
        Scene viewAppointmentListScene = new Scene(viewAppointmentListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewAppointmentListScene);
        window.show();
    }

    /**
     * This method performs validation logic on all input data and saves all correct data input from the combo boxes,
     * text fields, and date picker into the database by calling the updateAppointment query
     * @param event the save button
     * @throws IOException input/output exception
     */
    @FXML
    void onUaSaveB(ActionEvent event) throws IOException {
        String title = uaTitleTxt.getText();
        String desc = uaDescTxt.getText();
        String location = uaLocationTxt.getText();
        String type = uaTypeTxt.getText();
        //datepicker and time validation before converting to localdatetime
        if(uaDatepicker.getValue() == null) {
            InformationAlert a = new InformationAlert(
                    "Please select a valid date.",
                    "Error");
            return;
        }
        if(uaStartTimeCbox.getSelectionModel().getSelectedItem() == null) {
            InformationAlert a = new InformationAlert(
                    "Please pick a valid start time.",
                    "Error");
            return;
        }
        if(uaEndTimeCbox.getSelectionModel().getSelectedItem() == null) {
            InformationAlert a = new InformationAlert(
                    "Please pick a valid end time.",
                    "Error");
            return;
        }
        LocalDate date = uaDatepicker.getValue();
        //convert start and end times to LocalDateTime(include the date with the time)
        LocalTime startTime = uaStartTimeCbox.getValue();
        LocalTime endTime = uaEndTimeCbox.getValue();
        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = LocalDateTime.of(date, endTime);
        long customerId = uaCustomerIdCbox.getValue().getCustomerID();
        long userId = uaUserIdCbox.getValue().getUserID();
        long contactId = uaContactCbox.getValue().getContactID();
        long apptId = Long.parseLong(uaAptidTxt.getText());
        //appointment text fields and combo boxes validation
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
        if(uaCustomerIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid customer ID.",
                    "Error");
            return;
        }
        if(uaUserIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid user ID.",
                    "Error");
            return;
        }
        if(uaContactCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid contact.",
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
        if(!TimezoneConversions.estConversion(start) || !TimezoneConversions.estConversion(end)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Appointment times are not within company hours.");
            alert.showAndWait();
            return;
        }
        if(AppointmentTimes.isApptOverlapping(start, end, customerId, Long.parseLong(uaAptidTxt.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: An appointment within this timeframe already exists. Please choose a different time.");
            alert.showAndWait();
            return;
        }

        AppointmentQuery.updateAppointment(title, desc, location, type, start, end, customerId, userId, contactId, apptId);

        new InformationAlert("Appointment information saved.","");

        Parent viewAppointmentListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewAppointmentListForm.fxml"));
        Scene viewAppointmentListScene = new Scene(viewAppointmentListForm);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewAppointmentListScene);
        window.show();
    }

    /**
     * This method populates the end time combo box based on the start time value selected in the start time combo box
     * @param actionEvent start time combo box selection
     */
    public void onUaStartTimeCbox(ActionEvent actionEvent) {
        uaEndTimeCbox.valueProperty().set(null);
        uaEndTimeCbox.getItems().removeAll(uaEndTimeCbox.getItems());
        LocalTime startTime = uaStartTimeCbox.getSelectionModel().getSelectedItem();
        while(startTime.isBefore(endingTime.minusMinutes(30).plusSeconds(1))) {
            uaEndTimeCbox.getItems().add(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
        }
    }
}
