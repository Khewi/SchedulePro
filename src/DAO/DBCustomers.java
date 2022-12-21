package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBCustomers {


    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        try{
            String sql = "select c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, d.Division, co.Country From customers c " +
                    "INNER JOIN first_level_divisions as d on d.Division_ID = c.Division_ID " +
                    "LEFT JOIN countries as co ON co.country_ID = d.COUNTRY_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                Customer a = new Customer(customerID, customerName, address, postalCode, phone, divisionID, division, country);
                customersList.add(a);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }





    public static Customer getCustomer(int custID) {

        try{
            String sql = "select c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, d.Division, co.Country From customers c " +
                    "INNER JOIN first_level_divisions as d on d.Division_ID = c.Division_ID " +
                    "LEFT JOIN countries as co ON co.country_ID = d.COUNTRY_ID and c.Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, custID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                Customer a = new Customer(customerID, customerName, address, postalCode, phone, divisionID, division, country);
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
