package DAO;


import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * This class provides access to the MySQL database and returns information on appointments.
 */
public class DBAppointments {
    /**
     * This method accesses the MySQL DB appointment table. It pulls all appointment information and stores it in an observable list array called appList.
     * @return appList
     */

    public static ObservableList<appointment> getAllApps() {
        ObservableList<appointment> appList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from Appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();


                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();


                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");

                appointment a = new appointment(appID, title, description,location, type, start, end, customerID, userID, contact_ID);
                appList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }


    public static ObservableList<appointment> getAppsByUserID(int userID) {
        ObservableList<appointment> userAppList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from Appointments WHERE User_ID = '"+ userID +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                appointment b = new appointment(appID, title, description,location, type, start, end, customerID, userId, contactID);
                userAppList.add(b);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAppList;
    }
}

