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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * This class contains all the logic for the update customer form
 */
public class UpdateCustomerController {
    /**
     * Combo box holding the first level divisions
     */
    public ComboBox<FirstLevelDivisions> ucDivisionIdCbox;
    /**
     * Combo box holding the countries
     */
    public ComboBox<CountriesList> ucCountryIdCbox;
    @FXML
    private TextField ucAddressTxt;
    @FXML
    private Button ucCancelB;
    @FXML
    private TextField ucIdTxt;
    @FXML
    private TextField ucNameTxt;
    @FXML
    private TextField ucPostalcodeTxt;
    @FXML
    private TextField ucPhonenumberTxt;
    @FXML
    private Button ucSaveB;

    /**
     * This method receives data passed in from the view customers lists form and inputs it into the combo boxes and
     * text fields
     * @param selectedItem the selected customer
     */
    public void sendCustomer(Customers selectedItem) {
        try {
            //refreshes the division combobox values everytime the page is loaded
            ucDivisionIdCbox.valueProperty().set(null);
            ucDivisionIdCbox.getItems().removeAll(ucDivisionIdCbox.getItems());
            //list for setting the correct division
            ObservableList<FirstLevelDivisions> allFLD = FirstLevelDivisionsQuery.selectAllFLDivisions();

            //sets country cbox and div cbox values
            ucCountryIdCbox.setValue(CountriesList.getCountryByDivId(selectedItem.getDivisionID()));
            for (FirstLevelDivisions fld : allFLD) {
                if (fld.getDivisionID() == selectedItem.getDivisionID()) {
                    ucDivisionIdCbox.setValue(fld);
                }
            }
            //populates country cbox
            ObservableList<CountriesList> myCountries = FXCollections.observableArrayList();
            myCountries.addAll(Arrays.asList(CountriesList.values()));
            ucCountryIdCbox.setItems(myCountries);
            //populates division cbox
            CountriesList cl = ucCountryIdCbox.getSelectionModel().getSelectedItem();
            long countryId = cl.getCountryId();
            ucDivisionIdCbox.setItems(FirstLevelDivisionsQuery.selectFLDivision(countryId));
            //sets rest of values in text fields
            ucIdTxt.setText(String.valueOf(selectedItem.getCustomerID()));
            ucNameTxt.setText(selectedItem.getCustomerName());
            ucAddressTxt.setText(selectedItem.getAddress());
            ucPostalcodeTxt.setText(selectedItem.getPostalCode());
            ucPhonenumberTxt.setText(selectedItem.getPhoneNumber());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the division Id combo box based on the chosen country
     * @param actionEvent country Id box value chosen
     * @throws SQLException sql exception
     */
    public void onUcCountryIdCbox(ActionEvent actionEvent) throws SQLException {
        ucDivisionIdCbox.valueProperty().set(null);
        ucDivisionIdCbox.getItems().removeAll(ucDivisionIdCbox.getItems());

        CountriesList cl = ucCountryIdCbox.getSelectionModel().getSelectedItem();
        long countryId = cl.getCountryId();
        ucDivisionIdCbox.setItems(FirstLevelDivisionsQuery.selectFLDivision(countryId));
    }

    /**
     * This method performs validation logic on all input data and saves all correct data input from the comboboxes and
     * text fields into the database by calling the updateCustomer query
     * @param actionEvent the save button
     * @throws IOException input/output exception
     */
    public void onUcSaveB(ActionEvent actionEvent) throws IOException {
        long customerId = Long.parseLong(ucIdTxt.getText());
        String name = ucNameTxt.getText();
        String address = ucAddressTxt.getText();
        String postalCode = ucPostalcodeTxt.getText();
        String phoneNumber = ucPhonenumberTxt.getText();
        long divId = ucDivisionIdCbox.getValue().getDivisionID();

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
        if(ucDivisionIdCbox.getSelectionModel().getSelectedItem() == null) {
            new InformationAlert(
                    "Please select a valid division.",
                    "Error");
            return;
        }

        CustomerQuery.updateCustomer(name, address, postalCode, phoneNumber, divId, customerId);

        new InformationAlert("Customer information saved.","");

        Parent viewCustomerListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewCustomerListForm.fxml"));
        Scene viewCustomerListScene = new Scene(viewCustomerListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewCustomerListScene);
        window.show();
    }

    /**
     * This method takes the user back to the view apointments list form without saving any data
     * @param actionEvent cancel button clicked
     * @throws IOException input/output exception
     */
    public void onUcCancelB(ActionEvent actionEvent) throws IOException {
        Parent viewCustomerListForm = FXMLLoader.load(getClass().getResource("/main/software2/ViewCustomerListForm.fxml"));
        Scene viewCustomerListScene = new Scene(viewCustomerListForm);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(viewCustomerListScene);
        window.show();
    }
}
