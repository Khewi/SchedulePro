package model;


import java.time.LocalDateTime;

/**
 *  This class contains getter and setter functions for appointments.
 */
public class appointment {
    private int appID;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private LocalDateTime appStart;
    private LocalDateTime appEnd;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * constructor method for new appointments.
     * @param appID
     * @param appTitle
     * @param appDescription
     * @param appType
     * @param appLocation
     * @param appStart
     * @param appEnd
     * @param customerID
     * @param userID
     * @param contactID
     */
    public appointment(int appID, String appTitle, String appDescription, String appType, String appLocation,
                       LocalDateTime appStart, LocalDateTime appEnd, int customerID, int userID, int contactID){
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appType = appType;
        this.appLocation = appLocation;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }

    /**
     * getter function for appID.
     * @return appID
     */
    public int getAppID() {
        return appID;
    }

    /**
     * setter function for appID.
     * @param appID
     */
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /**
     * getter function for appTitle.
     * @return appTitle
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * setter function for appTitle.
     * @param appTitle
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * getter function for appDescription.
     * @return appDescription
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     * setter function for appDescription.
     * @param appDescription
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     * getter function for appLocation
     * @return appLocation
     */
    public String getAppLocation() {
        return appLocation;
    }

    /**
     * setter function for appLocation.
     * @param appLocation
     */
    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    /**
     * getter function for appType.
     * @return appType
     */
    public String getAppType() {
        return appType;
    }

    /**
     * setter function for appType.
     * @param appType
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }


    /**
     * getter function for appStart.
     * @return appStart
     */
    public LocalDateTime getAppStart() {
        return appStart;
    }

    /**
     * setter function for appStart.
     * @param appStart
     */
    public void setAppStart(LocalDateTime appStart) {
        this.appStart = appStart;
    }

    /**
     * getter function for appEnd.
     * @return appEnd
     */
    public LocalDateTime getAppEnd() {
        return appEnd;
    }

    /**
     * setter function for appEnd.
     * @param appEnd
     */
    public void setAppEnd(LocalDateTime appEnd) {
        this.appEnd = appEnd;
    }

    /**
     * getter function for customerID.
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }


    /**
     * setter function for customerID.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    /**
     * getter function for userID.
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * setter function for userID.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * getter function for contactID.
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * setter function for contactID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


}
