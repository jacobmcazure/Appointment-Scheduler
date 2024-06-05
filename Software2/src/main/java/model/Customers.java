package model;


import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class creates customer objects
 */
public class Customers {
    /**
     * customer ID as derived from the database (uneditable)
     */
    private long customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private long divisionID;

    /**
     * This is a constructor that creates customer objects with customer data from the database
     * @param customerID the customer ID
     * @param customerName the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phoneNumber the customer phone number
     * @param divisionID the customer division ID
     */
    public Customers(long customerID, String customerName, String address, String postalCode, String phoneNumber, long divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    /**
     * This method returns the customer ID
     * @return the customer ID
     */
    public long getCustomerID() {
        return customerID;
    }

    /**
     * This method returns the customer name
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method sets the customer name
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * This method returns the customer address
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method sets the customer address
     * @param address the customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method returns the customer postal code
     * @return the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * This method sets the customer postal code
     * @param postalCode the customer postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This method returns the customer phone number
     * @return the customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method sets the customer phone number
     * @param phoneNumber the customer phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method returns the customer division ID
     * @return the customer division ID
     */
    public long getDivisionID() {
        return divisionID;
    }

    /**
     * This method sets the customer division ID
     * @param divisionID the customer division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method overrides the hidden default display of combo box customer items and reformats it into a more readable and clear way
     * @return the reformatted customer ID
     */
    @Override
    public String toString() {
        return("ID: " + customerID);
    }

}
