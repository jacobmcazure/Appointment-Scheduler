package dao;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This class contains performs read queries on contact data from the contacts table in the database
 */
public class ContactsQuery extends DBConnection {
    /**
     * PreparedStatement attribute for all database connections and operations
     */
    private static PreparedStatement ps;

    /**
     * Gets all contacts and their info and puts it into an observable list
     * @return all contacts
     */
    public static ObservableList<Contacts> selectAllContacts() {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        try {
            ps = getConnection("SELECT * FROM contacts");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long contactId = rs.getLong("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts c = new Contacts(contactId, contactName, contactEmail);
                allContacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

}
