package model;

import java.time.LocalDateTime;

/**
 * This class creates appointment objects
 */
public class Appointments {
    /**
     * appointment ID as derived from the database (uneditable)
      */
    private long apptID;

    private String title;

    private String description;

    private String location;

    private String type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private long customerID;

    private long userID;
    /**
     * contact ID associated with the appointment
     */
    private long contactID;
    /**
     * contact name associated with the appointment
     */
    private String contactName;

    /**
     * This is a constructor that creates appointment objects with appointment data and contact data from the database
     * @param apptID The appointment ID
     * @param title The appointment title
     * @param description The appointment description
     * @param location The appointment location
     * @param type The appointment type
     * @param startTime The appointment start date/time
     * @param endTime The appointment end date/time
     * @param contactName The contact name associated with that appointment
     * @param contactID The contact ID associated with that appointment
     * @param customerID The customer ID
     * @param userID The user ID
     */
    public Appointments(long apptID,
                        String title,
                        String description,
                        String location,
                        String type,
                        LocalDateTime startTime,
                        LocalDateTime endTime,
                        String contactName,
                        long contactID,
                        long customerID,
                        long userID) {
        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * This method returns the appointment ID
     * @return appointment ID
     */
    public long getApptID() {
        return apptID;
    }

    /**
     * This method gets the appointment title
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the appointment title
     * @param title appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method gets the appointment description
     * @return appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method sets the appointment description
     * @param description appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method gets the appointment location
     * @return appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method sets the appointment location
     * @param location appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This appointment gets the appointment type
     * @return appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * This method sets the appointment type
     * @param type appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method gets the appointment start time and date
     * @return appointment start time and date
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * This method sets the appointment start time and date
     * @param startTime appointment start time and date
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    /**
     * This method gets the appointment end time and date
     * @return appointment end time and date
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }
    /**
     * This method sets the appointment end time and date
     * @param endTime appointment end time and date
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method gets the customer ID associated with the appointment
     * @return customer ID
     */
    public long getCustomerID() {
        return customerID;
    }

    /**
     * This method sets the customer ID associated with the appointment
     * @param customerID the customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * This method gets the user ID associated with the appointment
     * @return user ID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * This method sets the user ID associated with the appointment
     * @param userID the user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This method gets the contact ID associated with the appointment
     * @return contact ID
     */
    public long getContactID() {
        return contactID;
    }

    /**
     * This method sets the contact ID associated with the appointment
     * @param contactID the contact ID
     */
    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    /**
     * This method gets the contact ID associated with the appointment
     * @return contact ID
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method sets the contact name associated with the appointment
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
