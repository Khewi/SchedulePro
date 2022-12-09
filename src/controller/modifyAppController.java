package controller;

import DAO.DBAppointments;
import DAO.BDCustomers;
import DAO.DBContact;
import DAO.DBUser;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.appointment;
import model.contact;
import model.customer;
import model.user;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the modify application scene.
 */
public class modifyAppController implements Initializable {
    public TextField IDTF;
    public TextField titleTF;
    public TextField descriptionTF;
    public TextField locationTF;
    public TextField typeTF;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox1;
    public DatePicker datePicker;
    public ComboBox<customer> customerComboBox;
    public ComboBox<user> userComboBox;
    public ComboBox<contact> contactCombobox;
    int selectedIndex;
    private ZoneId localZone = ZoneId.systemDefault();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customer> customerList = BDCustomers.getCustomers();
        ObservableList<user> userList = DBUser.getAllUsers();
        ObservableList<contact> contactList = DBContact.getContact();

        customerComboBox.setItems(customerList);
        userComboBox.setItems(userList);
        contactCombobox.setItems(contactList);

        generateTimeList();
    }


    public void sendAppointment(appointment appointment){
        appointment loadAppointment = appointment;

        int customerID = loadAppointment.getCustomerID();
        int userID = loadAppointment.getUserID();
        int contactId = loadAppointment.getContactID();

        IDTF.setText(String.valueOf(loadAppointment.getAppID()));
        titleTF.setText(String.valueOf(loadAppointment.getAppTitle()));
        descriptionTF.setText(String.valueOf(loadAppointment.getAppDescription()));
        locationTF.setText(String.valueOf(loadAppointment.getAppLocation()));
        typeTF.setText(String.valueOf(loadAppointment.getAppType()));


        datePicker.setValue(loadAppointment.getAppStart().toLocalDate());
        startTimeComboBox.setValue((loadAppointment.getAppStart().toLocalTime()));
        endTimeComboBox1.setValue(loadAppointment.getAppEnd().toLocalTime());



    }



    /**
     ** This method generates the time selections for the start and end combo boxes in the modify appointment scene.
     */
    private void generateTimeList(){
        LocalTime start = LocalTime.of(8, 0,0);
        LocalTime end = LocalTime.of(17, 0, 0);

        while (start.isBefore(end.plusMinutes(1))){
            startTimeComboBox.getItems().add(start);
            endTimeComboBox1.getItems().add(start);

            start = start.plusMinutes(30);
        }
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
     * @throws IOException
     */
    public void onActionAppointmentsScreen(ActionEvent actionEvent) throws IOException {
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
     * This method logs the user out of the application and return the user to the login scene.
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


    /***
     * This method saves the modified customer data and returns the user to the appointments scene.
     * @param actionEvent
     */
    public void onActionSaveCustomer(ActionEvent actionEvent) throws IOException {
        String id = IDTF.getText();
        String title = titleTF.getText();
        String description = descriptionTF.getText();
        String location = locationTF.getText();
        String type = typeTF.getText();
        int custID = customerComboBox.getValue().getCustomerID();
        int userID = userComboBox.getValue().getUserID();
        int contactID = contactCombobox.getValue().getContactID();
        LocalDate localDate = datePicker.getValue();
        LocalTime localStartTime = (LocalTime) startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime localEndTime = (LocalTime) endTimeComboBox1.getSelectionModel().getSelectedItem();

        LocalDateTime startLDTC = LocalDateTime.of(localDate, localStartTime);
        LocalDateTime endLDTC = LocalDateTime.of(localDate, localEndTime);

        //Convert local user concat date/time to UTC for database entry
        ZonedDateTime startUTC = startLDTC.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp DBStart = Timestamp.valueOf(startUTC.toLocalDateTime());
        ZonedDateTime endUTC = endLDTC.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp DBEnd = Timestamp.valueOf(endUTC.toLocalDateTime());

        System.out.println("converted UTC date/time:\n Start: " + startUTC + "\n End: " + endUTC);

        //Insert new appointment into DB
        try{
            String sql = ("UPDATE appointments " +
                    "SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = ?");
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, DBStart);
            ps.setTimestamp(7, DBEnd);
            ps.setInt(8, custID);
            ps.setInt(9, userID);
            ps.setInt(10, contactID);
            ps.setInt(11, Integer.parseInt(id));

            System.out.println(ps);

            int insertSuccess = ps.executeUpdate();
            if (insertSuccess > 0){
                System.out.println("appointment insertion successful");
            }else {
                System.out.println("appointment insertion failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        Parent apps = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
        System.out.println("allApps.fxml path recognized");
        Scene scene = new Scene(apps);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        System.out.println("Back button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
        System.out.println("mainMenu.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

}
