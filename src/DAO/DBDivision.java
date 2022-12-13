package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FLD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivision {


    public static ObservableList<FLD> getDivision(){
        ObservableList<FLD> divisionList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT d.Division_ID, d.Division, d.COUNTRY_ID, c.Country FROM first_level_divisions d INNER JOIN countries as c on c.Country_ID = d.COUNTRY_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                String countryName = rs.getString("Country");

                FLD d = new FLD(id, name, countryID, countryName);
                divisionList.add(d);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionList;
    }
    }


