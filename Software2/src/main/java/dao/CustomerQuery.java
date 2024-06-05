package dao;

import DBConnection.DBConnection;
import controller.ReportsScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains performs CRUD(create, read, update, delete) queries on customer data from the customers table
 * in the database
 */
public class CustomerQuery extends DBConnection {
    /**
     * PreparedStatement attribute for all database connections and operations
     */
    private static PreparedStatement ps;

    /**
     * This method selects all the data from the customers table in the database,
     * creates customer objects, adds them to an ObservableList, and returns that list
     * @return list of all customers
     */
    public static ObservableList<Customers> selectAllCustomers() {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        try {
            ps = getConnection("SELECT * FROM customers");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long customerId = rs.getLong("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                long divisionId = rs.getLong("Division_ID");

                Customers c = new Customers(customerId, customerName, address, postalCode, phone, divisionId);
                allCustomers.add(c);
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * This method creates a customer object in the customers table in the database with the data passed in
     * @param customerName the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customer phone number
     * @param divisionId the division ID
     */
    public static void insertCustomer(String customerName,
                                      String address,
                                      String postalCode,
                                      String phone,
                                      long divisionId) {
        try {
            ps = getConnection("INSERT INTO customers (" +
                    "Customer_Name, Address, " +
                    "Postal_Code, Phone, " +
                    "Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, " +
                    "Division_ID) " +
                    "VALUES(?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "NOW(), " +
                    "'script', " +
                    "NOW(), " +
                    "'script', " +
                    "?)");
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setLong(5, divisionId);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates customer data in the customers table from the database according to the given customer ID
     * @param customerName the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customer phone number
     * @param divId the division ID
     * @param customerId the customer ID
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, long divId, long customerId) {
        try {
            ps = getConnection("UPDATE customers " +
                    "SET Customer_Name = ?, " +
                    "Address = ?, " +
                    "Postal_Code = ?, " +
                    "Phone = ?, " +
                    "Create_Date = NOW(), " +
                    "Created_By = 'script', " +
                    "Last_Update = NOW(), " +
                    "Last_Updated_By = 'script', " +
                    "Division_ID = ? " +
                    "WHERE Customer_ID = ?");
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setLong(5, divId);
            ps.setLong(6, customerId);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes a customer by customer id and its appointments by its foreign key customer id and
     * increments the number of customers deleted by 1
     * @param customerId the customer ID
     */
    public static void deleteCustomer(long customerId) {
        try {
            ps = getConnection("DELETE " +
                    "FROM appointments " +
                    "WHERE Customer_ID = ?");
            ps.setLong(1, customerId);
            ps.execute();

            ps = getConnection("DELETE " +
                    "FROM customers " +
                    "WHERE Customer_ID = ?");
            ps.setLong(1, customerId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ReportsScreenController.customerDeleteCount += 1;
    }

}

