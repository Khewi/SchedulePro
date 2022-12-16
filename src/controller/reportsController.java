package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ComboBox<String> typeCombo;
    public AnchorPane customerPane;
    public TableColumn custCol;
    public TableColumn countryID;
    public TableColumn divCol;
    public TableView scheduleTable;
    public ComboBox<String> divCombo;
    public ComboBox<country> countryCombo;
    public TableView countryTable;
    public Text descText;
    public Text totalText;
    ObservableList<contact> allContacts = DBContact.getContact();
    ObservableList<appointment> allApps = DBAppointments.getAllApps();
    ObservableList<country> allCountries = DBCountries.getCountries();
    ObservableList<FLD> allDiv = DBDivision.getDivision();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(allContacts);
        countryCombo.setItems(allCountries);

        ObservableList<String> lc = FXCollections.observableArrayList();
        lc.addAll("January","February", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");

        /**
         * Lambda #2
         * This lambda was built to take the above data list and turn all String values into an uppercase list.
         * The list needs to contain all uppercased letters to equal the same String format as the month that is taken from the Start dates in the appointment.
         * To return the appropriate number of appointments by type in that month, the list needs to contain all uppercase values to equal the same value the time API functions use to get the month.
         */
        Stream<String> stream = lc.stream().map(s -> s.toUpperCase());
        // Collect the elements of the Stream into a new ObservableList
        ObservableList<String> uppercaseList = stream.collect(Collectors.toCollection(FXCollections::observableArrayList));
        monthCombo.setItems(uppercaseList);



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

        for (appointment appointment : allApps) {
            if (appointment.getContactID() == contactID) {
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


        for (customer customer : allCusts) {
            if (customer.getDivision().equals(divName)) {

                String country = String.valueOf(customer.getCountryName());
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
        /**
         *This Lambada expression is using the .ForEach expression to iterate through the allDiv observable list to check the country ID.
         *It is validating the country name against the country ID to set the values for the Country report int the report dashboard on the report scene.
         */
        allDiv.forEach(FLD -> {
            if (FLD.getCountryID() == 1) {
                US.add(FLD.getDivisionName());
            } else if (FLD.getCountryID() == 2) {
                UK.add(FLD.getDivisionName());
            } else if (FLD.getCountryID() == 3) {
                Canada.add(FLD.getDivisionName());
            }
        });

        if (countrySelection.equals("1 U.S")) {
            divCombo.setItems(US);
        } else if (countrySelection.equals("3 Canada")) {
            divCombo.setItems(Canada);
        } else if (countrySelection.equals("2 UK")) {
            divCombo.setItems(UK);
        }
    }

    /**
     * This method loads the customer screen.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionCustomersScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("Customer button clicked");
        Parent cust = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        System.out.println("customers.fxml path recognized");
        Scene scene = new Scene(cust);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method loads the appointments home screen.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("Appointments button clicked");
        Parent apps = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
        System.out.println("allApps.fxml path recognized");
        Scene scene = new Scene(apps);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method loads the reports screen.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionReportsScreen(ActionEvent actionEvent) throws IOException {
        System.out.println("Reports button clicked");
        Parent login = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        System.out.println("reports.fxml path recognized");
        Scene scene = new Scene(login);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method logs the user out of the application and returns to login screen.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("Logout button clicked");
        Parent login = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        System.out.println("login.fxml path recognized");
        Scene scene = new Scene(login);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Schedule Pro");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionLoadTotal(ActionEvent actionEvent) {

        String selectedType = typeCombo.getValue();
        String selectedMonth = monthCombo.getValue();
        int total = 0;
        for(appointment app: allApps){
            String appMonth = String.valueOf(app.getAppStart().getMonth());
            System.out.println(appMonth);
            String appType = app.getAppType();
            if(appMonth.equals(selectedMonth) && appType.equals(selectedType)) {
                total = total + 1;
            }
        }
        totalText.setText(String.valueOf(total));
    }

    public void onSelectionAppTab(Event event) {
        ObservableList<String> appType = FXCollections.observableArrayList();
       String type;



       for(appointment appT: allApps){
          type = appT.getAppType();
          appType.add(type);
          }





        String[] distinctItems = appType.stream().distinct().toArray(String[]::new);
        ObservableList<String> somethingUnique = FXCollections.observableArrayList(distinctItems);

        typeCombo.setItems(somethingUnique);
       }

}

