package controller;

import DAO.DBAppointments;
import DAO.DBContact;
import DAO.DBCustomers;
import DAO.DBReconciliation;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the main menu scene.
 */
public class MainMenuController implements Initializable {
    public Button reportsButton;
    public Button logoutButton;
    public Button customersButton;
    public Button appointmentsButton;
    public TableColumn endCol;
    public TableColumn startCol;
    public TableView appointmentTable;
    public TableColumn typeCol;
    public TableColumn appIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TextField contactSearchBar;
    public TextField customerSearchBar; //new
    public TableColumn taskDueByCol;//new
    public TableColumn taskCustomerCol;//new
    public TableColumn taskDescrCol;//new
    public TableColumn taskNameCol;//new
    public TableView taskTableView;//new
    public TextField taskNameTF;
    public TextField taskDescrTF;
    public ComboBox<Customer> customerCombo;
    public DatePicker dueByDatePicker;
    int contactID;
    int userID;
    ObservableList<Appointment> allApps = DBAppointments.getAllApps();
    ObservableList<Appointment> dailyApps = FXCollections.observableArrayList();
    ObservableList<Contact> allContacts = DBContact.getContact();
    ObservableList<Appointment> contactSearch = FXCollections.observableArrayList();
    ObservableList<Reconciliation> allRecs = DBReconciliation.getReconciliations();
    ObservableList<Customer> customerList = DBCustomers.getCustomers();


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
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * This method loads the appointments scene.
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


    /***
     * This method loads the reports scene.
     * @param actionEvent
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


    /***
     * This method logs the user out of the application.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("Logout button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        System.out.println("login.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Schedule Pro");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appEnd"));

        taskNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        taskDueByCol.setCellValueFactory(new PropertyValueFactory<>("dueBy"));
        taskTableView.setItems(allRecs);

        customerCombo.setItems(customerList);

        setAppTable();

    }

    private void setAppTable() {
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);


        for (Appointment appointment : allApps) {
            if (appointment.getAppStart().toLocalDate().equals(today)) {
                Appointment newDaily = appointment;
                System.out.println("New daily app added to list: " + newDaily);
                dailyApps.add(newDaily);
            }
            appointmentTable.setItems(dailyApps);
        }
    }


    /**
     * search bar for filtering contact names
     *
     * @param actionEvent
     */
    public void onActionSearchContacts(ActionEvent actionEvent) {
        LocalDate today = LocalDate.now();
        Contact contact = null;
        String search = contactSearchBar.getText();
        Contact contactResult = checkContName();
        Appointment newDaily = null;

        try{
            if(contactResult == null){
                Info.error("Search Error", "No search results returned for " + search + " in today's appointments.");
            }else {
                for (Appointment appointment : allApps) {
                    if ((appointment.getAppStart().toLocalDate().equals(today)) && (contactResult.getContactID() == appointment.getContactID())) {
                        newDaily = appointment;
                        contactSearch.add(newDaily);
                        System.out.println("New daily app added: " + appointment.getAppID());

                    } else {
                        System.out.println("custID/date did not match");
                    }
                }
                appointmentTable.getItems().clear();
                appointmentTable.setItems(contactSearch);
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Contact checkContName() {
        String searchName = contactSearchBar.getText();
        Contact result = null;
        for (Contact c: allContacts){
            if (c.getContactName().contains(searchName)){
                System.out.println("Contact match found: " + c.getContactName());
                result = c;
            }
        } return result;
    }


    private Customer checkCustomerName() {
        String searchCustomer = customerSearchBar.getText();
        Customer result = null;
        if (customerSearchBar.getText().isEmpty()) {
            refreshTable();
        } else {
            for (Customer c : customerList) {
                if (c.getCustomerName().contains(searchCustomer)) {
                    System.out.println("Customer match found: " + c.getCustomerName());
                    result = c;
                }
            }
        }
        return result;
    }

    /**
     * Search bar to filter customer tasks
     * @param actionEvent
     */
    public void onActionSearchCustomers(ActionEvent actionEvent) {
        String search = customerSearchBar.getText();
        Customer result = checkCustomerName();
        ObservableList<Reconciliation> filterRecs = FXCollections.observableArrayList();
       try {
           if (result == null) {
               Info.error("Search Error", "No results found for " + search);
               refreshTable();
           }
           else {
               for (Reconciliation r : allRecs) {
                   System.out.println("r.getCustomer: " + r.getCustomer() + " result.getCustomerName: " + result.getCustomerName());

                   if (r.getCustomer().equals(result.getCustomerName())) {
                       Reconciliation match = r;
                       filterRecs.add(match);
                       System.out.println("match was added to filtered list");
                   }else {
                       System.out.println("Customer did not match");
                   }
               }
               taskTableView.getItems().clear();
               taskTableView.setItems(filterRecs);
               }
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    public void onActionSaveTask(ActionEvent actionEvent) {
        String name = taskNameTF.getText();
        String description = taskDescrTF.getText();
        int customerID = customerCombo.getValue().getCustomerID();
        LocalDate dueBy = dueByDatePicker.getValue();
        ObservableList<Reconciliation> updatedList = DBReconciliation.getReconciliations();

        if(taskNameTF.getText().isEmpty() || taskDescrTF.getText().isEmpty() || customerCombo.getSelectionModel().isEmpty() || dueByDatePicker.getValue() == null){
            Info.error("Save Error", "Please enter a value into all field top save the task.");
        }
        else{
            try{
                String sql = "INSERT INTO RECONCILIATIONS(name, description, Due_By, Customer_ID) VALUES( ?, ?, ?, ?)";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setDate(3, Date.valueOf(dueBy));
                ps.setInt(4, customerID);

                System.out.println(ps);
                clearValues();
                refreshTable();

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
        refreshTable();
    }

    private void clearValues(){
        taskNameTF.clear();
        taskDescrTF.clear();
        customerCombo.getSelectionModel().clearSelection();
        dueByDatePicker.setValue(null);
        System.out.println("Values cleared after task was saved.");
    }
    private void refreshTable(){
        allRecs.removeAll();
        allRecs = DBReconciliation.getReconciliations();
        taskTableView.setItems(allRecs);
        System.out.println("Table has been updated with new insert");
    }

    public void onActionDeleteTask(ActionEvent actionEvent) {

        if(taskTableView.getSelectionModel().getSelectedItem() != null){
            Reconciliation completeRec = (Reconciliation) taskTableView.getSelectionModel().getSelectedItem();
            int recID = completeRec.getID();
            String recName = completeRec.getName();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Customer Task");
            alert.setHeaderText("Do you want to complete " + recName + "?");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.get() == ButtonType.OK){
                completeTask(recID);
                allRecs.removeAll();
                allRecs = DBReconciliation.getReconciliations();
                taskTableView.setItems(allRecs);

            } else {
                System.out.println("Action canceled"); }
        }
        else {
            Info.error("Complete Task", "Nothing selected. \n Please select a task to complete");
        }
    }

    public void completeTask (int recID){

        try{
            String sql = "DELETE FROM RECONCILIATIONS WHERE RECONCILIATION_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, recID);
            ps.executeUpdate();
            System.out.println("Reconciliation " + recID + " removed from system.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
