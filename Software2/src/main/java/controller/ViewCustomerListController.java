package controller;

import dao.CustomerQuery;
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
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class contains logic for the view customers form
 */
public class ViewCustomerListController extends CustomerQuery implements Initializable {
    public Button customerListBackB;
    public Button customerListAddB;
    public Button customerListUpdateB;
    public Button CustomerListDeleteB;
    public Label customerErrorLbl;
    @FXML
    private TableColumn<Customers, String> customerAddressCol;
    @FXML
    private TableColumn<Customers, Long> customerDivisionidCol;
    @FXML
    private TableView<Customers> customerListTable;
    @FXML
    private TableColumn<Customers, String> customerPhonenumberCol;
    @FXML
    private TableColumn<Customers, String> customerZipcodeCol;
    @FXML
    private TableColumn<Customers, Long> customeridCol;
    @FXML
    private TableColumn<Customers, String> customernameCol;

    /**
     * This initializes the view customers list form by loading and populating the table view
     * @param url root path for the view customers list form
     * @param resourceBundle resources used to localize the view customers list form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerListTable.setItems(selectAllCustomers());

        customeridCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customernameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhonenumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerZipcodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerDivisionidCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    /**
     * This method takes the user back to the main menu
     * @param actionEvent back button clicked
     * @throws IOException input/output exception
     */
    public void onCustomerListBackB(ActionEvent actionEvent) throws IOException {
        Parent welcomeScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/MainMenuScreenForm.fxml"));
        Scene welcomeScreenScene = new Scene(welcomeScreenForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(welcomeScreenScene);
        window.show();
    }

    /**
     * This method takes the user to the add customer form
     * @param actionEvent add customer button clicked
     * @throws IOException input/output exception
     */
    public void onCustomerListAddB(ActionEvent actionEvent) throws IOException {
        Parent addCustomerScreenForm = FXMLLoader.load(getClass().getResource("/main/software2/AddCustomerForm.fxml"));
        Scene addCustomerScreenScene = new Scene(addCustomerScreenForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(addCustomerScreenScene);
        window.show();
    }

    /**
     * This method collects data from the selected customer and takes the user to the update customer form
     * @param actionEvent the update customer button clicked
     * @throws IOException input/output exception
     */
    public void onCustomerListUpdateB(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/software2/UpdateCustomerForm.fxml"));
            Parent updateCustomerScene = loader.load();

            UpdateCustomerController ucController = loader.getController();
            ucController.sendCustomer(customerListTable.getSelectionModel().getSelectedItem());

            Scene updateCustomerFormScene = new Scene(updateCustomerScene);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(updateCustomerFormScene);
            window.show();
        } catch (NullPointerException e) {
            new InformationAlert("Please select a valid Customer.", "Error");
        }
    }

    /**
     * This method alerts the user of deleting a customer, and upon confirmation, deletes the chosen customer by ID
     * and all of their appointments
     * @param actionEvent delete button clicked
     * @throws SQLException sql exception
     */
    public void onCustomerListDeleteB(ActionEvent actionEvent) throws SQLException {
        Customers delCustomer = customerListTable.getSelectionModel().getSelectedItem();
        if(delCustomer == null) {
            new InformationAlert("No customer selected.","Selection Error");
            return;
        }
        ConfirmationAlert deleteAlert = new ConfirmationAlert
                ("WARNING: This will delete the selected customer along with all of their appointments. Are you sure?",
                        "Delete Customer");
        Optional<ButtonType> result = deleteAlert.getTheResult();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteCustomer(delCustomer.getCustomerID());
            new InformationAlert
                    (delCustomer.getCustomerName() +
                            " with ID: " +
                            delCustomer.getCustomerID() +
                            " and all of their appointments have been successfully deleted.",
                            "");
        }
        //refreshes table
        customerListTable.setItems(selectAllCustomers());
    }

}
