package controller;


import DAO.BDCustomers;
import DAO.DBAppointments;
import DAO.DBContact;
import DAO.DBUser;
import database.DBConnection;
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
import java.sql.*;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
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
    public DatePicker datePicker;
    public ComboBox<customer> customerIDCombo;
    public ComboBox<contact> contactCombo;
    public ComboBox<user> userCombo;
    public ComboBox startTimeCombo;
    public ComboBox endTimeCombo;
    private final ZoneId localZone = ZoneId.systemDefault();
    private final ZoneId ESTZone = ZoneId.of("America/New_York");
    private final ZoneId UTCZone = ZoneId.of("UTC");
    ObservableList<appointment> allAppsList = DBAppointments.getAllApps();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customer> customerList = BDCustomers.getCustomers();
        ObservableList<user> userList = DBUser.getAllUsers();
        ObservableList<contact> contactList = DBContact.getContact();

        customerIDCombo.setItems(customerList);
        contactCombo.setItems(contactList);
        userCombo.setItems(userList);

        generateTimeList();
    }


    /***
     * This method saves the customer data to the database and returns the user to the customers scene.
     * @param actionEvent save button
     * @throws IOException
     */
    public void onActionSaveAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        if (allFieldsValid() == true) {
            int appID = -1;
            String title = titleTF.getText();
            String description = descriptionTF.getText();
            String location = locationTF.getText();
            String type = typeTF.getText();
            int customerID = customerIDCombo.getValue().getCustomerID();
            int userID = userCombo.getValue().getUserID();
            int contactID = contactCombo.getValue().getContactID();

            //Pulling Date and time information from the combo boxes in the specified format for concat
            LocalDate localDate = datePicker.getValue();
            LocalTime localStartTime = (LocalTime) startTimeCombo.getSelectionModel().getSelectedItem();
            LocalTime localEndTime = (LocalTime) endTimeCombo.getSelectionModel().getSelectedItem();
            System.out.println("Local start time grabbed from combo box: " + localStartTime + "\nLocal end time grabbed from combo box: "
                    + localEndTime + "\nLocal Date grabbed from date picker: " + localDate);

            // date/time concat in users local time for appointment class
            LocalDateTime startLDTC = LocalDateTime.of(localDate, localStartTime);
            LocalDateTime endLDTC = LocalDateTime.of(localDate, localEndTime);
            System.out.println("Concat local date and time- \nStart: " + startLDTC + "\nEnd: " + endLDTC);

            if (noOverlap(customerID, startLDTC, endLDTC) == false) {

                //Insert new appointment into DB
                try {
                    String sql = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.setString(3, location);
                    ps.setString(4, type);
                    ps.setTimestamp(5, Timestamp.valueOf(startLDTC));
                    ps.setTimestamp(6, Timestamp.valueOf(endLDTC));
                    ps.setInt(7, customerID);
                    ps.setInt(8, userID);
                    ps.setInt(9, contactID);

                    System.out.println(ps);

                    int insertSuccess = ps.executeUpdate();
                    if (insertSuccess > 0) {
                        System.out.println("appointment insertion successful");
                    } else {
                        System.out.println("appointment insertion failed");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }


                System.out.println("Back button clicked");
                Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
                System.out.println("mainMenu.fxml path recognized");
                Scene scene = new Scene(mainMenu);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointments");
                stage.setScene(scene);
                stage.show();
            }
        }else {
            System.out.println("unable to save appointment");
        }
    }




    public boolean allFieldsValid(){

        //grabbing date/time from fields and creating start and end concat date/Times
        LocalDate date = datePicker.getValue();
        LocalTime start = (LocalTime) startTimeCombo.getSelectionModel().getSelectedItem();
        LocalTime end = (LocalTime) endTimeCombo.getSelectionModel().getSelectedItem();
        LocalDateTime localStart = LocalDateTime.of(date, start);
        LocalDateTime localEnd = LocalDateTime.of(date, end);

        //converting LocalDateTime to ZonedDateTime
        ZonedDateTime ZonedStart = ZonedDateTime.of(localStart, localZone);
        ZonedDateTime ZonedEnd = ZonedDateTime.of(localEnd, localZone);
        System.out.println("Local zone time: \nStart: " + ZonedStart + "\n End: " + ZonedEnd);

        //Converting local zone date/time to EST time
        ZonedDateTime ESTStart = ZonedStart.withZoneSameInstant(ESTZone);
        ZonedDateTime ESTEnd = ZonedEnd.withZoneSameInstant(ESTZone);

        System.out.println("EST converted zone time: \nStart: " + ESTStart + "\n End: " + ESTEnd);

        //Setting EST business hours for logic check
        LocalTime businessStart = LocalTime.of(8,0,0);
        LocalTime businessEnd = LocalTime.of(22, 0, 0);
        //Converting business hours to LocalDateTime for logic check
        LocalDateTime businessDTStart = LocalDateTime.of(date, businessStart);
        LocalDateTime businessDTEnd = LocalDateTime.of(date, businessEnd);
        //converting to ZonedDateTime for time comparison in logic check
        ZonedDateTime ESTBusinessStart = ZonedDateTime.of(businessDTStart, ESTZone);
        ZonedDateTime ESTBusinessEnd = ZonedDateTime.of(businessDTEnd,ESTZone);

        int customerID = customerIDCombo.getSelectionModel().getSelectedIndex();
        int appID = -1;




        if(titleTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || titleTF.getText().isEmpty() ||
                    typeTF.getText().isEmpty() || locationTF.getText().isEmpty() || contactCombo.getItems().isEmpty() ||
                    userCombo.getItems().isEmpty() || contactCombo.getItems().isEmpty() || datePicker.getValue() == null ||
                    startTimeCombo.getSelectionModel().isEmpty() || endTimeCombo.getSelectionModel().isEmpty()) {
                info.error("SAVE ERROR", "Empty field found while trying to save appointment. Please enter a value in all fields.");
                return false;
            } else if(start.isAfter(end)){
                info.error("START TIME ERROR", "The start time cannot begin after the end time. Select a START time that begins before the END time.");
                return false;
            }
            else if(ZonedStart.isBefore(ESTBusinessStart) || ZonedStart.isAfter(ESTBusinessEnd) || ZonedEnd.isBefore(ZonedStart) || ZonedEnd.isAfter(ESTBusinessEnd)){
                info.error("SCHEDULE ERROR", "Appointment time is outside of business hours. Please select a time during the hours of 8am and 10pm EST.");
                return false;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SAVE APPOINTMENT");
                alert.setHeaderText("Do you want to save the current appointment?");
                alert.setContentText("Click OK to save appointment.");
                Optional<ButtonType> input = alert.showAndWait();
                if (input.get() == ButtonType.OK){
                    return true;
                }
                else {
                    return false;
                }
            }
    }



    public boolean noOverlap(int customerID, LocalDateTime start, LocalDateTime end) {
        System.out.println("\nnoOverlap method used\n---------------");
        int appID = -1;
        boolean hasConflict = false;
        for (appointment appointments : allAppsList) {
            System.out.println("CustomerID Being compared: " + customerID +"\nLocal start for new appointment: " + start + "Local end for new appointment: " + end);

            LocalDateTime verifyStart = appointments.getAppStart();
            System.out.println("Start Time of app in system: " + verifyStart);

            LocalDateTime verifyEnd = appointments.getAppEnd();
            System.out.println("End Time of app in system: " + verifyEnd);
            System.out.println("for loop to check app time starting \n ------------------");

            int customerIDCheck = appointments.getCustomerID();
            int appIDCheck = appointments.getAppID();
            System.out.println("CustomerIDCheck: " + customerIDCheck + "\nappIDCheck: " + appIDCheck);

            if ((customerID == customerIDCheck) && (appID != appIDCheck)
                    && (start.isBefore(verifyStart) && end.isBefore(verifyStart)) || (start.isAfter(verifyEnd) && end.isAfter(verifyEnd))) {
                hasConflict = false;
                System.out.println("This appointment does not overlap any existing appointments with the same customer.");
            } else if(customerID != customerIDCheck){
                hasConflict = false;
                System.out.println("No appointment conflict. The customer ID does not match any existing appointments in the system.");
            }
            else{
                info.error("OVERLAPPING APPOINTMENT", "The time of this appointment " +
                        "overlaps an existing appointment in the system for this customer.");
                return true;
            }
        } return hasConflict;
    }

    /**
     * This method generates the time selections for the start and end combo boxes in the new appointment scene.
     */
    private void generateTimeList(){
        LocalTime start = LocalTime.of(8, 0,0);
        LocalTime end = LocalTime.of(22, 0, 0);

        while (start.isBefore(end.plusMinutes(1))){
            startTimeCombo.getItems().add(start);
            endTimeCombo.getItems().add(start);

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
     * This method cancels the modification of customer data and returns the user to the customer scene.
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
