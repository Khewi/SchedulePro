package model;


/***
 * This class contains getter and setter functions for first level divisions.
 */
public class FLD {
    private int divisionID;
    private String divisionName;
    private int countryID;


    /***
     * constructor method for first level divisions.
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public FLD(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }


    /**
     * getter function for divisionID.
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /***
     * setter function for divisionID.
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }


    /***
     * getter function for divisionName.
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /***
     * setter function for divisionName.
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * getter function for countryID.
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }


    /***
     * setter function for countryID.
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }






}
