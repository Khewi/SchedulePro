package controller;


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
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the new appointment scene.
 */
public class newAppController<localZone> implements Initializable {
    public TextField IDTF;
    public TextField titleTF;
    public TextField descriptionTF;
    public TextField locationTF;
    public TextField typeTF;
    public ComboBox startTimeComboBox;
    public DatePicker datePicker;
    public ComboBox endTimeCombobox;
    public DatePicker endDatePicker;
    public ComboBox<customer> customerIDCombo;
    public ComboBox<contact> contactCombo;
    public ComboBox<user> userCombo;
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter dateTimeConcatFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private ZoneId localZone = ZoneId.systemDefault();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customer> customerList = BDCustomers.getCustomers();
        ObservableList<user> userList = DBUser.getAllUsers();
        ObservableList<contact> contactList = DBContact.getContact();

        customerIDCombo.setItems(customerList);
        contactCombo.setItems(contactList);
        userCombo.setItems(userList);
    }


    /***
     * This method saves the customer data to the database and returns the user to the customers scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionSaveAppointment(ActionEvent actionEvent) throws IOException {

        String title = titleTF.getText();
        String description = descriptionTF.getText();
        String location = locationTF.getText();
        String type = typeTF.getText();
        /*
        int customerID = Integer.valueOf(String.valueOf(customerIDCombo.getSelectionModel()));
        int userID = Integer.valueOf(String.valueOf(userCombo.getSelectionModel()));
        int contactId = Integer.valueOf(String.valueOf(contactCombo.getSelectionModel()));


         */

        //Pulling Date and time information from the combo boxes in the specified format for concat
        LocalDate localDate = datePicker.getValue();

        //NEED TO CODE TIME COMBO BOX FOR FUNCTIONALITY - PROGRAM BREAKS AT LACK OF INPUT
        LocalTime localStartTime = LocalTime.parse((CharSequence) startTimeComboBox.getSelectionModel().getSelectedItem(), timeFormat);
        LocalTime localEndTime = LocalTime.parse((CharSequence) endTimeCombobox.getSelectionModel().getSelectedItem(), timeFormat);

        System.out.println("Local start time grabbed from combo box: " + localStartTime + "\nLocal end time grabbed from combo box: "
                + localEndTime + "\nLocal Date grabbed from date picker: " + localDate);
        // date/time concat in users local time for appointment class
        LocalDateTime startLDTC = LocalDateTime.of(localDate, localStartTime);
        LocalDateTime endLDTC = LocalDateTime.of(localDate, localEndTime);
        System.out.println("Concat local date and time- \nStart: " + startLDTC + "\nEnd: " + endLDTC);
        //Convert local user concat date/time to UTC for database entry
        ZonedDateTime startUTC = startLDTC.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp DBStart = Timestamp.valueOf(startUTC.toLocalDateTime());
        ZonedDateTime endUTC = endLDTC.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp DBEnd = Timestamp.valueOf(endUTC.toLocalDateTime());
        System.out.println("converted UTC date/time:\n Start: " + startUTC + "\n End: " + endUTC);

        //Insert new appointment into DB
        try{
            int appID = -1;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO appointments(Title, Description, Location, Type, Start, End) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, DBStart);
            ps.setTimestamp(6, DBEnd);
            System.out.println(ps);
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                appID = rs.getInt(1);
                System.out.println(appID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Back button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
        System.out.println("mainMenu.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
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
     * This method logs the user out of the application and returns the user to the login scene.
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
     * This method cancels the modificaiton of customer data and returns the user to the customer scene.
     * @param actionEvent
     * @throws IOException
     */
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

    public void customerIDCombo(ActionEvent actionEvent) {

    }

    public void userIDCombo(ActionEvent actionEvent) {
    }

    public void contactCombo(ActionEvent actionEvent) {
    }

    public void startTimeCombo(ActionEvent actionEvent) {
    }

    public void endTimeCombo(ActionEvent actionEvent) {
    }


}
