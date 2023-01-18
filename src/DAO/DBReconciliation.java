package DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Reconciliation;

import java.sql.*;
import java.time.LocalDate;

public class DBReconciliation {

    public static boolean checkRecTableExist(){
        try{
            DatabaseMetaData dbm = DBConnection.getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "reconciliations", null);
            if(tables.next()){
                System.out.println("Reconciliation table exists");
                return true;
            }else{
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }



    public static void insertDummyTasks() throws SQLException {
        String name1 = "Task 1";
        String name2 = "Task 2";
        String name3 = "Task 3";
        String name4 = "Task 4";

        String description1 = "Reconciliation for December";
        String description2 = "Reconciliation for January";
        String description3 = "Reconciliation for December";
        String description4 = "Reconciliation for February";

        LocalDate testDate1 = LocalDate.of(2023,01,31);
        LocalDate testDate2 = LocalDate.of(2023,02,28);
        LocalDate testDate3 = LocalDate.of(2023,01,31);
        LocalDate testDate4 = LocalDate.of(2023,03,18);

        Date date1 = Date.valueOf(testDate1);
        Date date2 = Date.valueOf(testDate2);
        Date date3 = Date.valueOf(testDate3);
        Date date4 = Date.valueOf(testDate4);


        int customerID1 = 2;
        int customerID2 = 1;
        int customerID3 = 3;
        int customerID4 = 1;
        try{
            String sql1 = "INSERT INTO RECONCILIATIONS(name, description, Due_By, Customer_ID) VALUES( ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql1);
            ps.setString(1, name1);
            ps.setString(2, description1);
            ps.setDate(3, date1);
            ps.setInt(4, customerID1);

            System.out.println(ps);

            int insertSuccess = ps.executeUpdate();
            if (insertSuccess > 0) {
                System.out.println("reconciliation insertion successful");
            } else {
                System.out.println("reconciliation insertion failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            String sql1 = "INSERT INTO RECONCILIATIONS(name, description, Due_By, Customer_ID) VALUES( ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql1);
            ps.setString(1, name2);
            ps.setString(2, description2);
            ps.setDate(3, date2);
            ps.setInt(4, customerID2);

            System.out.println(ps);

            int insertSuccess = ps.executeUpdate();
            if (insertSuccess > 0) {
                System.out.println("reconciliation insertion successful");
            } else {
                System.out.println("reconciliation insertion failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            try{
                String sql1 = "INSERT INTO RECONCILIATIONS(name, description, Due_By, Customer_ID) VALUES( ?, ?, ?, ?)";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql1);
                ps.setString(1, name3);
                ps.setString(2, description3);
                ps.setDate(3, date3);
                ps.setInt(4, customerID3);

                System.out.println(ps);

                int insertSuccess = ps.executeUpdate();
                if (insertSuccess > 0) {
                    System.out.println("reconciliation insertion successful");
                } else {
                    System.out.println("reconciliation insertion failed");
                }
        } catch (SQLException e) {
                e.printStackTrace();
            }
        try{
            String sql1 = "INSERT INTO RECONCILIATIONS(name, description, Due_By, Customer_ID) VALUES( ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql1);
            ps.setString(1, name4);
            ps.setString(2, description4);
            ps.setDate(3, date4);
            ps.setInt(4, customerID4);

            System.out.println(ps);

            int insertSuccess = ps.executeUpdate();
            if (insertSuccess > 0) {
                System.out.println("reconciliation insertion successful");
            } else {
                System.out.println("reconciliation insertion failed");
            }
        }catch (SQLException e) {
                e.printStackTrace();
            }

            }

            public static ObservableList<Reconciliation> getReconciliations(){
        ObservableList<Reconciliation> reconciliationsList = FXCollections.observableArrayList();

        try{
            String sql = "select r.Reconciliation_ID, r.name, r.description, r.due_By, c.customer_name from reconciliations r inner join customers as c on r.Customer_ID = c.Customer_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int ID = rs.getInt("Reconciliation_ID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date dueBy = rs.getDate("due_By");
                String customer= rs.getString("customer_name");

                Reconciliation r = new Reconciliation(ID, name, description, customer, dueBy);
                reconciliationsList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reconciliationsList;
    }


}

