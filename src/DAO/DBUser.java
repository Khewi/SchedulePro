package DAO;


import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides database access to the users table.
 */
public class DBUser {

    /**
     * This method returns all of the users in the database.
     * @return
     */
    public static ObservableList<user> getAllUsers(){
        ObservableList<user> uList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                user a = new user(userID, username, password);
                uList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uList;
    }

    /**
     * This method is used to validate the userID for displaying th in user combo box on the modify appointments scene.
     * @param userID
     * @return
     */
    public static user getUser(int userID) {
        ObservableList<user> customersList = FXCollections.observableArrayList();
        user a = null;

        try{
            String sql = "select User_ID, User_Name From Users WHERE User_ID = " + userID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int uID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");

                a = new user(uID, username);
                customersList.add(a);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

}

