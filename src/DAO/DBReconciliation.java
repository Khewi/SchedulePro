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
                String sql = "CREATE TABLE reconciliations (Reconciliation_ID INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), description VARCHAR(150), due_by TIMESTAMP, Customer_ID INT, FOREIGN KEY (Customer_ID) REFERENCES customers(Customer_ID));";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.executeQuery();
                insertDummyTasks();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }

    public static void insertDummyTasks(){
        String name = "Task 1";
        String description = "Reconciliation for December";
        LocalDate testDate = LocalDate.of(2023,01,31);
        Date date = Date.valueOf(testDate);
        int customerID = 2;
        try{
            String sql = "INSERT INTO RECONCILIATIONS(name, description, Due_By, Customer_ID) VALUES( ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDate(3, date);
            ps.setInt(4, customerID);

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

