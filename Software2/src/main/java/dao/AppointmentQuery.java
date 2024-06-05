package dao;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class contains performs CRUD(create, read, update, delete) queries on appointment data from the appointments table in the
 * database and associated contact information in the database
 */
public class AppointmentQuery extends DBConnection {
    /**
     * PreparedStatement attribute for all database connections and operations
     */
    private static PreparedStatement ps;

    /**
     * This method selects all the data from the appointments table as well as contact information with matching IDs
     * from the contacts table in the database creates appointment objects, adds them to an ObservableList, and returns that list
     *
     * @return list of all appointments
     */
    public static ObservableList<Appointments> selectAllAppointments() {
        ObservableList<Appointments> allAppts = FXCollections.observableArrayList();
        try {
            ps = getConnection("SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long apptId = rs.getLong("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                String contactName = rs.getString("Contact_Name");
                long contactId = rs.getLong("Contact_ID");
                long customerId = rs.getLong("Customer_ID");
                long userId = rs.getLong("User_ID");

                //create object instance
                Appointments ap = new Appointments(apptId,
                        title,
                        description,
                        location,
                        type,
                        startTime,
                        endTime,
                        contactName,
                        contactId,
                        customerId,
                        userId);

                allAppts.add(ap);
            }
        } catch (SQLException t) {
            t.printStackTrace();
        }
        return allAppts;
    }

    /**
     * This method updates appointment data in the appointments table from the database according to the given appointment ID
     *
     * @param title       appointment title
     * @param description appointment description
     * @param location    appointment location
     * @param type        appointment type
     * @param startTime   appointment start date and time
     * @param endTime     appointment end date and time
     * @param customerId  customer ID
     * @param userId      user ID
     * @param contactId   contact ID
     * @param apptId      appointment ID
     */
    public static void updateAppointment(String title,
                                         String description,
                                         String location,
                                         String type,
                                         LocalDateTime startTime,
                                         LocalDateTime endTime,
                                         long customerId,
                                         long userId,
                                         long contactId,
                                         long apptId) {
        try {
            ps = getConnection("UPDATE appointments " +
                    "SET Title = ?, " +
                    "Description = ?, " +
                    "Location = ?, " +
                    "Type = ?, " +
                    "Start = ?, " +
                    "End = ?, " +
                    "Create_Date = NOW(), " +
                    "Created_By = 'script', " +
                    "Last_Update = NOW(), " +
                    "Last_Updated_By = 'script', " +
                    "Customer_ID = ?, " +
                    "User_ID = ?, " +
                    "Contact_ID = ? " +
                    "WHERE Appointment_ID = ?");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startTime));
            ps.setTimestamp(6, Timestamp.valueOf(endTime));
            ps.setLong(7, customerId);
            ps.setLong(8, userId);
            ps.setLong(9, contactId);
            ps.setLong(10, apptId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a new appointment in the appointments table in the database with the data passed in
     *
     * @param title       appointment title
     * @param description appointment description
     * @param location    appointment location
     * @param type        appointment type
     * @param startTime   appointment start time and date
     * @param endTime     appointment end time and date
     * @param customerId  customer ID
     * @param userId      user ID
     * @param contactId   contact ID
     */
    public static void insertAppointment(String title,
                                         String description,
                                         String location,
                                         String type,
                                         LocalDateTime startTime,
                                         LocalDateTime endTime,
                                         long customerId,
                                         long userId,
                                         long contactId) {
        try {
            ps = getConnection("INSERT INTO appointments (" +
                    "Title, Description, Location, Type, Start, " +
                    "End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
                    "Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "NOW(), " +
                    "'script', " +
                    "NOW(), " +
                    "'script', " +
                    "?, " +
                    "?, " +
                    "?)");
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startTime));
            ps.setTimestamp(6, Timestamp.valueOf(endTime));
            ps.setLong(7, customerId);
            ps.setLong(8, userId);
            ps.setLong(9, contactId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes an appointment from the appointments table in the database according to the appointment ID
     *
     * @param apptId appointment ID
     */
    public static void deleteAppointment(long apptId) {
        try {
            ps = getConnection("DELETE " +
                    "FROM appointments " +
                    "WHERE Appointment_ID = ?");
            ps.setLong(1, apptId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method selects all appointments according to the specific month and type passed in,
     * then returns the number of matching appointments
     *
     * @param month a given month
     * @param type  appointment type
     * @return
     * @throws SQLException
     */
    public static int getNumberOfMatches(int month, String type) throws SQLException {
        int count = 0;
        ps = getConnection("SELECT COUNT(*) FROM appointments WHERE MONTH(Start) = ? AND Type = ?");
        ps.setInt(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            count = rs.getInt("Count(*)");
        }
        return count;
    }

    public static ObservableList<Appointments> selectAllApptsByUserId(long userID) throws SQLException {
        ObservableList<Appointments> allApptsbyUserId = FXCollections.observableArrayList();
        ps = getConnection("SELECT * FROM appointments WHERE User_ID = ?");
        ps.setLong(1, userID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long apptId = rs.getLong("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            String contactName = rs.getString("Contact_Name");
            long contactId = rs.getLong("Contact_ID");
            long customerId = rs.getLong("Customer_ID");
            long userId = rs.getLong("User_ID");

            //create object instance
            Appointments ap = new Appointments(apptId,
                    title,
                    description,
                    location,
                    type,
                    startTime,
                    endTime,
                    contactName,
                    contactId,
                    customerId,
                    userId);


            allApptsbyUserId.add(ap);
        }
        return allApptsbyUserId;
    }

}
