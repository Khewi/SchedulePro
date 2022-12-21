package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries {

    public static ObservableList<Country> getCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try{
            String sql = "Select * FROM Countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country c = new Country(id, name);
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
    public static Country getCountry(int countryID) {

        Country a = null;

        try{
            String sql = "select country_ID, Country From Countries WHERE Country_ID = " + countryID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                a = new Country(countryId, countryName);
                System.out.println(countryId + " " + countryName);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static Country getCountryByDiv(int divId) {

        Country a = null;

        try{
            String sql = "select c.country_ID, c.Country From Countries as c INNER JOIN first_level_divisions as d on c.country_ID = d.country_ID and d.division_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, divId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                a = new Country(countryId, countryName);
                System.out.println(countryId + " " + countryName);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }



    }

