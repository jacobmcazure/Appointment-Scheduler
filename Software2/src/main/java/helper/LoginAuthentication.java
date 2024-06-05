package helper;

import dao.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Users;

/**
 * This class provides logic for authenticating the data in the Login Screen form
 */
public class LoginAuthentication extends UserQuery {
    /**
     * This is an ObservableList of all Users objects from a query to the database
     */
    public static ObservableList<Users> currentUsers = selectAllUsers();
    /**
     * This holds the value of the userID entered for the 15-minute alert
     */
    public static long userID;

    /**
     * This method takes in a String username and password, checks if they are found in the database,
     * adds them to a list, and returns a boolean value based on the list being populated or empty
     * @param username the username entered
     * @param password the password entered
     * @return a boolean value
     */
    public static boolean validateUserAndPw(String username, String password){
        ObservableList<Users> filteredList = FXCollections.observableArrayList();
        for(Users u : currentUsers) {
            if (u.getUserName().equals(username) && (u.getPassword().equals(password))) {
                filteredList.add(u);
                setValidUserId(u.getUserID());
            }
        }
        return !filteredList.isEmpty();
        }

    public static void setValidUserId(long userId) {
        userID = userId;
    }
    public static long getValidUserId() {
        return userID;
    }

}

