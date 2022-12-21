package model;


import DAO.DBCustomers;
import javafx.collections.ObservableList;

/***
 * This class contains getter and setter functions for customers.
 */
public class Customer {

    public static ObservableList getAllCustomers;
    private int countryID;
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNum;
    private int divisionID;
    private String division;
    private String countryName;


    public Customer(){
        super();
    }
    /**
     * constructor method for customers.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNum
     * @param divisionID
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNum, int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionID = divisionID;
    }

    public Customer(int ID, String name){
        setCustomerID(ID);
        setCustomerName(name);
    };

    public Customer(String name, String division, String countryName){
        this.customerName = name;
        this.division = division;
        this.countryName = countryName;
    }

    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNum, int divisionID, String division, String countryName){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionID = divisionID;
        this.division = division;
        this.countryName = countryName;
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
     * @return
     */
    public int setCustomerID(int customerID) {
        this.customerID = customerID;
        return customerID;
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

    public ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allcust = DBCustomers.getCustomers();
        return allcust;
    }

    /**
     * getter method for division variable in customer class.
     * @return
     */
    public String getDivision() {return division;}

    /**
     * setter method for division variable in customer class.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Setter method for countryName in customer class.
     * @param countryName
     * @return
     */
    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

    /**
     * getter for country name of customer class.
     * @return
     */
    public String getCountryName(){
        return countryName;
    }


    /**
     * This method overrides Oracles default return style of combo box selections for the customer class.
     * @return customerName
     */
    @Override
    public String toString(){
        return (customerID + " " +customerName);
    }


}
