package controller;

import dao.CustomerQuery;
import dao.FirstLevelDivisionsQuery;
import helper.CountriesList;
import helper.InformationAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
/**
 * This class contains all the logic for the add customer form
 */
public class AddCustomerController implements Initializable {
    /**
     * The combo box that holds the first level divisions
     */
    public ComboBox<FirstLevelDivisions> acDivisionIdCbox;
    /**
     * The combo box that holds the countries from the CountriesListEnum
     */
    public ComboBox<CountriesList> acCountryIdCbox;
    @FXML
    private TextField acAddTxt;
    @FXML
    private Button acCancelB;
    @FXML
    private TextField acIdTxt;
    @FXML
    private TextField acNameTxt;
    @FXML
    private TextField acPcTxt;
    @FXML
    private TextField acPnTxt;
    @FXML
    private Button acSaveB;

    /**
     * This method initializes the add customer form by populating all the combo boxes and text fields in the proper format
     * @param url root path for the view customers list form
     * @param resourceBundle resources used to localize the view customers list form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acCountryIdCbox.setPromptText("Select a Country...");
        acDivisionIdCbox.setPromptText("Please select a Country First.");

        ObservableList<CountriesList> myCountries = FXCollections.observableArrayList();
        myCountries.addAll(Arrays.asList(CountriesList.values()));

        acCountryIdCbox.setItems(myCountries);
    }

    /**
     * This method takes the user to the view customers list form without saving any changes made to any data
     * @param actionEvent The cancel button
     * @throws IOException input/output exception
     */
    public void onAcCancelB(ActionEvent actionEvent) throws IOException {
        Parent viewCustomerListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewCustomerListForm.fxml"));
        Scene viewCustomerListScene = new Scene(viewCustomerListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewCustomerListScene);
        window.show();
    }

    /**
     * This method populates the first level divisions combo box based on the Country chosen using a first level divisions
     *  query with the country ID that matches the country selected
     * @param actionEvent a selection made on the country combo box
     * @throws SQLException sql exception
     */
    public void onAcCountryIdCbox(ActionEvent actionEvent) throws SQLException {
        acDivisionIdCbox.valueProperty().set(null);
        acDivisionIdCbox.getItems().removeAll(acDivisionIdCbox.getItems());

        CountriesList cl = acCountryIdCbox.getSelectionModel().getSelectedItem();
        long countryId = cl.getCountryId();
        acDivisionIdCbox.setItems(FirstLevelDivisionsQuery.selectFLDivision(countryId));
    }

    /**
     * This method performs validation logic on all input data and saves all correct data input from the combo boxes
     * and text fields into the database by calling the insertCustomer query
     * @param actionEvent The save button
     * @throws IOException input/output exception
     */
    public void onAcSaveB(ActionEvent actionEvent) throws IOException {
        String name = acNameTxt.getText();
        String address = acAddTxt.getText();
        String postalCode = acPcTxt.getText();
        String phoneNumber = acPnTxt.getText();
        long divId = acDivisionIdCbox.getValue().getDivisionID();
        if(name.equals("")) {
            new InformationAlert(
                    "Please enter a valid name.",
                    "Error");
            return;
        }
        if(address.equals("")) {
            new InformationAlert(
                    "Please enter a valid address.",
                    "Error");
            return;
        }
        if(postalCode.equals("")) {
            new InformationAlert(
                    "Please enter a valid postal code.",
                    "Error");
            return;
        }
        if(phoneNumber.equals("")) {
            new InformationAlert(
                    "Please enter a valid phone number.",
                    "Error");
            return;
        }
        if(acCountryIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid country.",
                    "Error");
            return;
        }
        if(acDivisionIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid division.",
                    "Error");
            return;
        }

        CustomerQuery.insertCustomer(name, address, postalCode, phoneNumber, divId);

        new InformationAlert("Customer information saved.","");

        Parent viewCustomerListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewCustomerListForm.fxml"));
        Scene viewCustomerListScene = new Scene(viewCustomerListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewCustomerListScene);
        window.show();
    }

}
