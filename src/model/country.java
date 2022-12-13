package model;


/***
 * This class contains getter and setter functions for countries.
 */
public class country {

    private int countryID;
    private String countryName;

    /***
     * Constructor method for new countries.
     * @param countryID
     * @param countryName
     */
    public country(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }


    /***
     * getter function for countryID.
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * setter function for countryID.
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * getter function for countryName.
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /***
     * setter function for countryName.
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String toString(){
        return (countryID + " " +countryName);
    }


}
