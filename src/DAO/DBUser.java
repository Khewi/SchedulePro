package DAO;


import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.User;

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
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> uList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                User a = new User(userID, username, password);
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
    public static User getUser(int userID) {
        ObservableList<User> customersList = FXCollections.observableArrayList();
        User a = null;

        try{
            String sql = "select User_ID, User_Name From Users WHERE User_ID = " + userID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int uID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");

                a = new User(uID, username);
                customersList.add(a);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

}

