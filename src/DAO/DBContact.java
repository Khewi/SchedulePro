package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contact;
import model.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContact {

    public static ObservableList<contact> getContact(){
        ObservableList<contact> contactList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int ID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                contact a = new contact(ID, name, email);
                contactList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}