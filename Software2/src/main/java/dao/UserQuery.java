package dao;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains performs read queries on users data from the users table in the database and
 * associated contact information in the database
 */
public class UserQuery extends DBConnection {
    /**
     * PreparedStatement attribute for all database connections and operations
     */
    private static PreparedStatement ps;

    /**
     * This method selects all users data from the users table in the database, creates Users objects, adds them to
     * an ObservableList, and returns that list
     * @return list of all users
     */
    public static ObservableList<Users> selectAllUsers() {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        try {
            ps = getConnection("SELECT * FROM Users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users u = new Users(userId, username, password);
                allUsers.add(u);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allUsers;
    }

    /**
     * This method selects users by a specific user ID, creates Users objects, adds them to an ObservableList, and
     * returns that list
     * @param userId the user ID
     * @return list of all matching Users
     * @throws SQLException
     */
    public static ObservableList<Users> selectByUserID(int userId) throws SQLException {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        ps = getConnection("SELECT * FROM users WHERE User_ID = ?");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");

            Users u = new Users(userID, username, password);
            allUsers.add(u);
        }
        return allUsers;
    }

}
