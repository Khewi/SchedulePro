package DAO;


import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointment;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * This class provides access to the MySQL database and returns information on appointments.
 */
public class DBAppointments {
    /**
     * This method accesses the MySQL DB appointment table. It pulls all appointment information and stores it in an observable list array called appList.
     * @return appList
     */

    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static ZoneId localZone = ZoneId.systemDefault();
    static ZoneId UTCZone = ZoneId.of("UTC");

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

                //getting UTC time from DB
                LocalDateTime UTCStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime UTCEnd = rs.getTimestamp("End").toLocalDateTime();

                //
                ZonedDateTime UTCZoneStart = UTCStart.atZone(UTCZone);
                ZonedDateTime UTCZoneEnd = UTCEnd.atZone(UTCZone);

                ZonedDateTime convertedStart = UTCZoneStart.withZoneSameInstant(localZone);
                ZonedDateTime convertedEnd = UTCZoneEnd.withZoneSameInstant(localZone);

                LocalDateTime localStart = LocalDateTime.from(convertedStart);
                LocalDateTime localEnd = LocalDateTime.from(convertedEnd);

                int customerID = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                appointment b = new appointment(appID, title, description,location, type, UTCStart, UTCEnd, customerID, userId, contactID);
                userAppList.add(b);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAppList;
    }

    public static String convertToLocalTimeZone(String dateTime) {
        String convertedDate = "";
        try{
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = utcFormat.parse(dateTime);

            DateFormat currentTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentTimeFormat.setTimeZone(TimeZone.getTimeZone(getUserTimeZone()));

            convertedDate = currentTimeFormat.format(date);
            System.out.println("user zone date/time: " + convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getUserTimeZone(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        System.out.println(tz.getDisplayName());
        return tz.getID();
    }
}

