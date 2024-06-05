package dao;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains performs read queries on FirstLevelDivisions data from the FirstLevelDivisions table in the database
 */
public class FirstLevelDivisionsQuery extends DBConnection {
    /**
     * PreparedStatement attribute for all database connections and operations
     */
    private static PreparedStatement ps;

    /**
     * This method selects all the first level divisions from the first_level_divisions table in the database,
     * creates FirstLevelDivisions objects, adds them to an ObservableList, and returns that list
     * @return list of first level divisions
     */
    public static ObservableList<FirstLevelDivisions> selectAllFLDivisions() {
        ObservableList<FirstLevelDivisions> allFLDivisions = FXCollections.observableArrayList();
        try {
            ps = getConnection("SELECT * FROM first_level_divisions");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long divisionId = rs.getLong("Division_ID");
                String division = rs.getString("Division");
                long countryId = rs.getLong("Country_ID");

                //create object instance
                FirstLevelDivisions fld = new FirstLevelDivisions(divisionId, division, countryId);

                allFLDivisions.add(fld);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFLDivisions;
    }

    /**
     * This method selects all first level divisions according to a specific country ID, creates FirstLevelDivisions objects,
     * adds them to an ObservableList, then returns that list
     * @param countryId the country ID
     * @return list of all matching first level divisions
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> selectFLDivision(long countryId) throws SQLException {
        ObservableList<FirstLevelDivisions> allFLDivisionsById = FXCollections.observableArrayList();
        try {
            ps = getConnection("SELECT * FROM first_level_divisions WHERE Country_ID = ?");
            ps.setLong(1, countryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long divisionId = rs.getLong("Division_ID");
                String division = rs.getString("Division");

                //create object instance
                FirstLevelDivisions fld = new FirstLevelDivisions(divisionId, division, countryId);

                allFLDivisionsById.add(fld);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFLDivisionsById;
    }

}
