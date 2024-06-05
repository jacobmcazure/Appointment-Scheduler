package helper;

import javafx.collections.ObservableList;
import model.Countries;

/**
 * This enum contains all Countries and their associated names and IDs that matches the database. This way, the program
 * does not have to reach into the database more times than is has to. I made the choice to hard code the Countries
 * because they fit what an enum is for: few, unchanging values
 */
public enum CountriesList {
    US("U.S", 1),
    UK("UK", 2),
    Canada("Canada", 3);
    /**
     * country name, matching the database
     */
    String countryDBName;
    /**
     * country ID, matching the database
     */
    long countryId;

    /**
     * This is a constructor that creates CountriesList objects
     * @param countryDBName the country name
     * @param countryId the country ID
     */
    CountriesList(String countryDBName, long countryId) {
        this.countryDBName = countryDBName;
        this.countryId = countryId;
    }

    /**
     * This method returns the country name
     * @return the country name
     */
    public String getCountryDBName() {
        return countryDBName;
    }

    /**
     * This method returns the country ID
     * @return the country ID
     */
    public long getCountryId() {
        return countryId;
    }

    /**
     * This method returns a specific country from the enum based on the division ID passed in
     * @param divId the division ID
     * @return the country that matches the division ID
     */
    public static CountriesList getCountryByDivId(long divId) {
        if(divId >= 1 && divId <= 54) return CountriesList.US;
        else if(divId >= 60 && divId <= 72) return CountriesList.Canada;
        else return CountriesList.UK;
    }

}
