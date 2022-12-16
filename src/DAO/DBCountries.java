package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.country;
import model.user;

import java.lang.constant.Constable;
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

    /**
     * This method is used to return the country name and id in the modify user and modify customer combo boxes.
     * @param countryID
     * @return
     */
    public static country getCountry(int countryID) {

        country a = null;

        try{
            String sql = "select country_ID, Country From Countries WHERE Country_ID = " + countryID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                a = new country(countryId, countryName);
                System.out.println(countryId + " " + countryName);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }





    }

