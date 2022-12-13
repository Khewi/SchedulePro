package controller;

import DAO.DBCountries;
import DAO.DBDivision;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods that are needed for the modify customer scene.
 */
public class modifyCustomerController implements Initializable {
    public TextField IDTF;
    public TextField nameTF;
    public TextField addressTF;
    public TextField phoneNumTF;
    public TextField postalCodeTF;
    public ComboBox<country> countryCombobox;
    public ComboBox<String> FLDCombobox;
    public ObservableList<FLD> allDivisionsList = DBDivision.getDivision();
    public ObservableList<country> countriesList= DBCountries.getCountries();
    public ObservableList<String> allFLDNamesList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombobox.setItems(countriesList);

    }


    public void sendCustomer(customer customer){
        customer loadCust = customer;

        IDTF.setText(String.valueOf(loadCust.getCustomerID()));
        nameTF.setText(loadCust.getCustomerName());
        addressTF.setText(loadCust.getAddress());
        postalCodeTF.setText(loadCust.getPostalCode());
        phoneNumTF.setText(loadCust.getPhoneNum());

        FLDCombobox.setValue(loadCust.getDivision());

    }


    /***
     * This method loads the customer scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionCustomersScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("Customer button clicked");
        Parent cust = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        System.out.println("customers.fxml path recognized");
        Scene scene = new Scene(cust);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method loads the appointments scene.
     * @param actionEvent
     */
    public void onActionAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("Appointments button clicked");
        Parent apps = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
        System.out.println("allApps.fxml path recognized");
        Scene scene = new Scene(apps);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * This method loads the reports scene.
     * @param actionEvent
     */
    public void onActionReportsScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("Reports button clicked");
        Parent login = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        System.out.println("reports.fxml path recognized");
        Scene scene = new Scene(login);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports Dashboard");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * This method logs the user out of the application and returns to the login scene.
     * @param actionEvent
     */
    public void onActionLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("Logout button clicked");
        Parent login = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        System.out.println("login.fxml path recognized");
        Scene scene = new Scene(login);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Schedule Pro");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * This method save the customer information to the database.
     * @param actionEvent
     */
    public void onActionSaveCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        if(allFieldsValid() == true) {
            int custID = Integer.parseInt(IDTF.getText());
            String name = nameTF.getText();
            String address = addressTF.getText();
            String postalCode = postalCodeTF.getText();
            String phoneNum = phoneNumTF.getText();
            String division = FLDCombobox.getValue();
            String country = String.valueOf(countryCombobox.getSelectionModel().getSelectedItem());

            int divId = getDivID(division);
            System.out.println("divID: " + divId);

            try{
                String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME=?, ADDRESS=?, POSTAL_CODE=?, PHONE=?, DIVISION_ID=? WHERE CUSTOMER_ID = ?";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, postalCode);
                ps.setString(4, phoneNum);
                ps.setInt(5, divId);
                ps.setInt(6, custID);
                System.out.println(ps);

                int insertSuccess = ps.executeUpdate();
                if (insertSuccess > 0){
                    System.out.println("Customer " + name + " was inserted into the database successfully");
                    System.out.println("Cancel button clicked");
                    Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
                    System.out.println("customers.fxml path recognized");
                    Scene scene = new Scene(mainMenu);
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    stage.setTitle("Customers");
                    stage.setScene(scene);
                    stage.show();
                }else{
                    System.out.println("Failed to insert " + name + " into the database.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("Unable to save customer.");
        }
    }

    /**
     * This method returns the division ID when passed a division. It is used to support the save method.
     * @param division
     * @return
     * @throws SQLException
     */
    private int getDivID(String division) throws SQLException {
        ObservableList<FLD> ID = FXCollections.observableArrayList();
        String sql = "SELECT DIVISION_ID FROM first_level_divisions WHERE DIVISION = '"+division+"'";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        while(rs.next()) {
            int divID = rs.getInt("Division_ID");
            FLD d = new FLD(divID);
            ID.add(d);
            id = d.getDivisionID();
        }
        return id;
    }

    /**
     * This method loads the combo box for the first level division combo box.
     * @param actionEvent
     */
    public void onActionSetFLD(ActionEvent actionEvent) {
        String countrySelection = String.valueOf(countryCombobox.getSelectionModel().getSelectedItem());

        ObservableList<String> UK = FXCollections.observableArrayList();
        ObservableList<String> US = FXCollections.observableArrayList();
        ObservableList<String> Canada = FXCollections.observableArrayList();

        allDivisionsList.forEach(FLD -> {
            if(FLD.getCountryID() == 1) {
                US.add(FLD.getDivisionName());
            }
            else if(FLD.getCountryID() == 2){
                UK.add(FLD.getDivisionName());
            }
            else if (FLD.getCountryID() == 3){
                Canada.add(FLD.getDivisionName());
            }
        } );

        if(countrySelection.equals("1 U.S")){
            FLDCombobox.setItems(US);
        }
        else if (countrySelection.equals("3 Canada")){
            FLDCombobox.setItems(Canada);
        }
        else if(countrySelection.equals("2 UK")){
            FLDCombobox.setItems(UK);
        }

    }

    /***
     * This method cancels any loaded information in the text fields and returns the user to the customers scene.
     * @param actionEvent
     */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        System.out.println("Customer button clicked");
        Parent cust = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        System.out.println("customers.fxml path recognized");
        Scene scene = new Scene(cust);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    private boolean allFieldsValid(){
        if(nameTF.getText().isEmpty() || addressTF.getText().isEmpty() || postalCodeTF.getText().isEmpty() ||
                phoneNumTF.getText().isEmpty() || countryCombobox.getSelectionModel().isEmpty() || FLDCombobox.getSelectionModel().isEmpty()){
            info.error("ERROR", "Unable to save this customer. Please ensure there is a value in all fields before saving customer information.");
            return false;
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SAVE CUSTOMER");
            alert.setHeaderText("Do you want to save the current customer?");
            alert.setContentText("Click OK to save customer.");
            Optional<ButtonType> input = alert.showAndWait();
            if (input.get() == ButtonType.OK){
                return true;
            }
            else {
                return false;
            }
        }
    }





}
