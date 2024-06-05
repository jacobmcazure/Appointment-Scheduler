package model;

/**
 * This class creates First Level Division objects
 */
public class FirstLevelDivisions {
    /**
     * division ID
     */
    private long divisionID;
    /**
     * division name
     */
    private String division;
    /**
     * country ID
     */
    private long countryID;

    /**
     * This is a constructor that creates FirstLevelDivisions objects with FirstLevelDivision data from the database
     * @param divisionID the division ID
     * @param division the division name
     * @param countryID the country ID
     */
    public FirstLevelDivisions(long divisionID, String division, long countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * This method returns the division ID
     * @return the division ID
     */
    public long getDivisionID() {
        return divisionID;
    }

    /**
     * This method sets the division ID
     * @param divisionID the division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * This method returns the division name
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * This method sets the division name
     * @param division the division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * This method returns the country ID
     * @return the country ID
     */
    public long getCountryID() {
        return countryID;
    }

    /**
     * This method sets the country ID
     * @param countryID the country ID
     */
    public void setCountryID(long countryID) {
        this.countryID = countryID;
    }
    /**
     * This method overrides the hidden default display of combo box FirstLevelDivisions items and reformats it into a more readable and clear way
     * @return the reformatted division ID and division Name
     */
    @Override
    public String toString() {
        return("ID: " + divisionID + ", " + division);
    }
}
