package model;

/**
 * This class creates contact objects
 */
public class Contacts {
    /**
     * contact ID
     */
    private long contactID;
    /**
     * contact name
     */
    private String contactName;
    /**
     * contact email
     */
    private String email;

    /**
     * This is a constructor that creates contact objects with contact data from the database
     * @param contactID contact ID
     * @param contactName contact name
     * @param email contact email
     */
    public Contacts(long contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * This method returns the contact ID
     * @return the contact ID
     */
    public long getContactID() {
        return contactID;
    }

    /**
     * This method sets the contact ID
     * @param contactID the contact ID
     */

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * This method gets the contact name
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method sets the contact name
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * This method gets the contact email
     * @return the contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method sets the contact email
     * @param email the contact email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method overrides the hidden default display of combo box contact items and reformats it into a more readable and clear way
     * @return the reformatted contact ID and contact Name
     */
    @Override
    public String toString() {
        return("ID: " + contactID + ", " + contactName);
    }
}
