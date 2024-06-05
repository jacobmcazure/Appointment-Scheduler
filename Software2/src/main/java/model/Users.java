package model;

/**
 * This class creates Users objects
 */
public class Users {
    /**
     * user ID as derived from the database
     */
    private long userID;
    /**
     * user username
     */
    private String userName;
    /**
     * user password
     */
    private String password;

    /**
     * This is a constructor that creates Users objects with Users data from the database
     * @param userID the user ID
     * @param userName the user username
     * @param password the user password
     */
    public Users(long userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * This method returns the user ID
     * @return the user ID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * This method sets the user ID
     * @param userID the user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This method returns the username
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method sets the username
     * @param userName the username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method returns the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets the password
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method overrides the hidden default display of combo box user items and reformats it into a more readable and clear way
     * @return the reformatted user ID
     */
    @Override
    public String toString() {
        return("ID: " + userID);
    }
}
