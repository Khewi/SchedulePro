package model;


import DAO.BDCustomers;
import database.DBConnection;
import javafx.collections.ObservableList;

/***
 * This class contains getter and setter functions for customers.
 */
public class customer {

    public static ObservableList getAllCustomers;
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNum;
    private int divisionID;

    /**
     * constructor method for customers.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNum
     * @param divisionID
     */
    public customer(int customerID, String customerName, String address, String postalCode, String phoneNum, int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionID = divisionID;
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
     * getter function for customerName.
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }


    /**
     * setter function for customerName.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    /**
     * getter function for address
     * @return address
     */
    public String getAddress() {
        return address;
    }


    /**
     * setter function for address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * getter function for postalCode.
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }


    /**
     * setter function for postalCode.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * getter function for phoneNum.
     * @return phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * setter function for phoneNum.
     * @param phoneNum
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * getter function for divisionID.
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }


    /**
     * setter function for divisionID.
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public ObservableList<customer> getAllCustomers(){
        ObservableList<customer> allcust = BDCustomers.getCustomers();
        return allcust;
    }

}
