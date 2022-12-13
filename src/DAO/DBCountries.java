package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries {

    public static ObservableList<country> getCountries(){
        ObservableList<country> countryList = FXCollections.observableArrayList();

        try{
            String sql = "Select * FROM Countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                country c = new country(id, name);
                countryList.add(c);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList ;
    }
    }

