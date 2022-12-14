package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller class hold the action functions for the reports screen.
 */
public class reportsController implements Initializable {
    public AnchorPane appReport;
    public Tab contactReport;
    public TableColumn appIdCol;
    public TableColumn titleCol;
    public TableColumn descrCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn custID;
    public ComboBox<contact> contactCombo;
    public ComboBox<String> monthCombo;
    public ComboBox<appointment> typeCombo;
    public AnchorPane customerPane;
    public TableColumn custCol;
    public TableColumn countryID;
    public TableColumn divCol;
    public TableView scheduleTable;
    public ComboBox<String> divCombo;
    public ComboBox<country> countryCombo;
    public TableView countryTable;
    ObservableList<contact> allContacts = DBContact.getContact();
    ObservableList<appointment> allApps = DBAppointments.getAllApps();
    ObservableList<country> allCountries = DBCountries.getCountries();
    ObservableList<FLD> allDiv = DBDivision.getDivision();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(allContacts);
        countryCombo.setItems(allCountries);

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descrCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        custID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));


        custCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        countryID.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        divCol.setCellValueFactory(new PropertyValueFactory<>("division"));

    }


    public void onActionLoadSchedule(ActionEvent actionEvent) {
        ObservableList<appointment> appInfor = FXCollections.observableArrayList();

        appointment contactAppSchedule;

        int contactID = contactCombo.getValue().getContactID();

        for(appointment appointment: allApps){
            if(appointment.getContactID() == contactID){
                contactAppSchedule = appointment;
                appInfor.add(contactAppSchedule);
            }
            scheduleTable.setItems(appInfor);
        }
    }

    public void onActionSetCountryTable(ActionEvent actionEvent) {
        ObservableList<customer> allCusts = BDCustomers.getCustomers();

        String divName = divCombo.getValue();
        System.out.println("div combo box: " + divName);

        ObservableList<customer> custDivList = FXCollections.observableArrayList();



        for (customer customer : allCusts){
            if(customer.getDivision().equals(divName)){

                String country = (String) customer.getCountryName();
                String custName = customer.getCustomerName();

                customer c = new customer(custName, divName, country);
                custDivList.add(c);

            }
        }

        countryTable.setItems(custDivList);
    }


    public void onActionLoadDivs(ActionEvent actionEvent) {
        String countrySelection = String.valueOf(countryCombo.getSelectionModel().getSelectedItem());

        ObservableList<String> UK = FXCollections.observableArrayList();
        ObservableList<String> US = FXCollections.observableArrayList();
        ObservableList<String> Canada = FXCollections.observableArrayList();

        // Lambda #1
        allDiv.forEach(FLD -> {
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
            divCombo.setItems(US);
        }
        else if (countrySelection.equals("3 Canada")){
            divCombo.setItems(Canada);
        }
        else if(countrySelection.equals("2 UK")){
            divCombo.setItems(UK);
        }
    }

    /**
     * This method loads the customer screen.
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

    /**
     * This method loads the appointments home screen.
     * @param actionEvent
     * @throws IOException
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

    /**
     * This method loads the reports screen.
     * @param actionEvent
     * @throws IOException
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

    /**
     * This method logs the user out of the application and returns to login screen.
     * @param actionEvent
     * @throws IOException
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

}
