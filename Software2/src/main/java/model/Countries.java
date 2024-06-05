package model;

/**
 * This class creates country objects and works with the CountriesList enum
 */
public class Countries {
    /**
     * country ID
     */
    private long countryID;
    /**
     * country name
     */
    private String country;

    /**
     * This is a constructor that creates Country objects with country data from the CountriesList enum
     * @param countryID the country ID
     * @param country the country name
     */
    public Countries(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
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
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * This method gets the country name
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method sets the country name
     * @param country the country name
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
