package controller;

import DAO.DBAppointments;
import DAO.DBContact;
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
import model.Appointment;
import model.Contact;
import model.Info;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    public TextField searchBar;
    int contactID;
    int userID;
    ObservableList<Appointment> allApps = DBAppointments.getAllApps();
    ObservableList<Appointment> dailyApps = FXCollections.observableArrayList();
    ObservableList<Contact> allContacts = DBContact.getContact();
    ObservableList<Appointment> contactSearch = FXCollections.observableArrayList();


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

        setTable();

    }

    private void setTable() {
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
    public void onActionSearch(ActionEvent actionEvent) {
        LocalDate today = LocalDate.now();
        Contact contact = null;
        String search = searchBar.getText();
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
        String searchName = searchBar.getText();
        Contact result = null;
        for (Contact c: allContacts){
            if (c.getContactName().contains(searchName)){
                System.out.println("Contact match found: " + c.getContactName());
                result = c;
            }
        } return result;
    }



}
