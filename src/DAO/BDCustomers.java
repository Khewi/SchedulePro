package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointment;
import model.customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BDCustomers {


    //Not working
    public static ObservableList<customer> getCustomers() {
        ObservableList<customer> customersList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from Customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                customer a = new customer(customerID, customerName, address, postalCode, phone, divisionID);
                customersList.add(a);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }
}
