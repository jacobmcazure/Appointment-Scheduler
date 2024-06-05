package controller;

import DBConnection.DBConnection;
import dao.AppointmentQuery;
import dao.ContactsQuery;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provides logic for the reports screen
 */
public class ReportsScreenController implements Initializable {
    /**
     * Combo box that holds all Months
     */
    public ComboBox<Month> monthCbox;
    /**
     * Combo box that holds all appointment types
     */
    public ComboBox<String> typeCbox;
    public Button enterB;
    public TextField totalNumberTF;
    /**
     * Combo box that holds all contacts
     */
    public ComboBox<Contacts> contactsCbox;
    public TableColumn<Appointments,Long> aptIdCol;
    public TableColumn<Appointments,String> titleCol;
    public TableColumn<Appointments,String> descriptionCol;
    public TableColumn<Appointments,String> typeCol;
    public TableColumn<Appointments, LocalDateTime> startCol;
    public TableColumn<Appointments, LocalDateTime> endCol;
    public TableColumn<Appointments, Long> customerIdCol;
    public TableView<Appointments> reportsTable;
    public TextField customerDelTF;
    public Button customerDelB;
    @FXML
    private Button reportsBackB;

    /**
     * This is an ObservableList of Appointments that collects all appointment data from a query to the database
     */
    public static ObservableList<Appointments> aList = AppointmentQuery.selectAllAppointments();
    /**
     * This is an ObservableList of contacts that collects contact data from a query to the database
     */
    public static ObservableList<Contacts> contactsList = ContactsQuery.selectAllContacts();
    /**
     * This variable is a counter of how many times a customer was deleted during the current session. This is for
     * the third report of your choice requirement
     */
    public static int customerDeleteCount = 0;

    /**
     * This initializes the tableview and all the combo boxes
     * @param url root path for the view reports list form
     * @param resourceBundle resources used to localize the reports form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        //clears table
        reportsTable.getItems().clear();

        //refreshes contacts cbox in the event a contact was added
        contactsCbox.getItems().clear();
        contactsCbox.setItems(contactsList);

        ObservableList<String> allTypes = FXCollections.observableArrayList();
        if(!monthCbox.getItems().isEmpty()) {
            //do nothing
        } else {
            ObservableList<Month> allMonths = FXCollections.observableArrayList();
            for (Month m : Month.values()) {
                m.getDisplayName(TextStyle.FULL, Locale.getDefault());
                allMonths.add(m);
            }
            monthCbox.setItems(allMonths);
        }
        //refreshes type box everytime page is loaded
        typeCbox.getItems().clear();
        for(Appointments a : aList) {
            allTypes.add(a.getType());
        }
        typeCbox.setItems(allTypes);
    }

    /**
     * This method searches the appointments table in the database for matching appointments based on the data gathered
     * from the combo boxes, and provides an error if incomplete information was gathered
     * @param actionEvent the enter button clicked
     * @throws SQLException
     */
    public void onEnterB(ActionEvent actionEvent) throws SQLException {
        totalNumberTF.setText("");
            if ((monthCbox.getValue() == null && typeCbox.getValue() == null) || (monthCbox.getValue() == null) || (typeCbox.getValue() == null)) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Please select both a month and a type to generate a count of matching appointments.");
                a.setTitle("");
                a.showAndWait();
                return;
            }
            Month selectedMonth = monthCbox.getSelectionModel().getSelectedItem();
            int monthNum = selectedMonth.getValue();
            String type = typeCbox.getSelectionModel().getSelectedItem();

            String matches = Integer.toString(AppointmentQuery.getNumberOfMatches(monthNum, type));
            totalNumberTF.setText(matches);
    }

    /**
     * This method takes the user back to the main menu
     * @param actionEvent back button clicked
     * @throws IOException input/output exception
     */
    @FXML
    void onReportsBackB(ActionEvent actionEvent) throws IOException {
        Parent welcomeScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/MainMenuScreenForm.fxml"));
        Scene welcomeScreenScene = new Scene(welcomeScreenForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(welcomeScreenScene);
        window.show();
    }

    /**
     * This method uses a lambda to filter and sort through matching appointments by the chosen contact information
     * @param actionEvent a filtered list
     */
    public void onContactsCbox(ActionEvent actionEvent) {
        ObservableList<Appointments> filteredList = FXCollections.observableArrayList();
        long contactId = contactsCbox.getValue().getContactID();
        aList.stream().
                filter(appointments -> contactId == appointments.getContactID())
                .forEach(filteredList::add);
        reportsTable.refresh();
        reportsTable.setItems(filteredList);
    }

    /**
     * This method sets text telling the user how many customers have been deleted so far. This is for the third report
     * @param actionEvent Show Me! button clicked
     */
    public void onCustomerDelB(ActionEvent actionEvent) {
        customerDelTF.setText(customerDeleteCount + " customer(s) have been deleted so far!");
    }

}
