package controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the modify application scene.
 */
public class ModifyAppController implements Initializable {
    public TextField IDTF;
    public TextField titleTF;
    public TextField descriptionTF;
    public TextField locationTF;
    public TextField typeTF;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox1;
    public DatePicker datePicker;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<User> userComboBox;
    public ComboBox<Contact> contactCombobox;
    int selectedIndex;
    private ZoneId localZone = ZoneId.systemDefault();
    private ZoneId ESTZone = ZoneId.of("America/New_York");
    ObservableList<Appointment> allAppsList = DBAppointments.getAllApps();



    /**
     * this method intializes the modifyAppController
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customerList = DBCustomers.getCustomers();
        ObservableList<User> userList = DBUser.getAllUsers();
        ObservableList<Contact> contactList = DBContact.getContact();


        customerComboBox.setItems(customerList);
        userComboBox.setItems(userList);
        contactCombobox.setItems(contactList);

        generateTimeList();
    }

    /**
     * this method receives data to initialize the fields in the modifyApp screen.
     * @param appointment
     */
    public void sendAppointment(Appointment appointment){

        Appointment loadAppointment = appointment;

      userComboBox.setValue(DBUser.getUser(loadAppointment.getUserID()));
      customerComboBox.setValue(DBCustomers.getCustomer(loadAppointment.getCustomerID()));
      contactCombobox.setValue(DBContact.getContact(loadAppointment.getContactID()));

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
        LocalTime end = LocalTime.of(22, 0, 0);

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
        if(allFieldsValid() == true) {
            int appid = Integer.parseInt(IDTF.getText());
            String title = titleTF.getText();
            String description = descriptionTF.getText();
            String location = locationTF.getText();
            String type = typeTF.getText();
            int custID = customerComboBox.getValue().getCustomerID();
            int userID = userComboBox.getValue().getUserID();
            int contactID = contactCombobox.getValue().getContactID();

            //Pulling Date and time information from the combo boxes in the specified format for concat
            LocalDate localDate = datePicker.getValue();
            LocalTime localStartTime = (LocalTime) startTimeComboBox.getSelectionModel().getSelectedItem();
            LocalTime localEndTime = (LocalTime) endTimeComboBox1.getSelectionModel().getSelectedItem();
            // date/time concat in users local time for appointment class
            LocalDateTime startLDTC = LocalDateTime.of(localDate, localStartTime);
            LocalDateTime endLDTC = LocalDateTime.of(localDate, localEndTime);

            if (noOverlap(custID, appid, startLDTC, endLDTC) == false) {
                //Insert new appointment into DB
                try {
                    String sql = ("UPDATE appointments " +
                            "SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                            "WHERE Appointment_ID = ?");
                    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);


                    ps.setInt(1, appid);
                    ps.setString(2, title);
                    ps.setString(3, description);
                    ps.setString(4, location);
                    ps.setString(5, type);
                    ps.setTimestamp(6, Timestamp.valueOf(startLDTC));
                    ps.setTimestamp(7, Timestamp.valueOf(endLDTC));
                    ps.setInt(8, custID);
                    ps.setInt(9, userID);
                    ps.setInt(10, contactID);
                    ps.setInt(11, appid);

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


                Parent apps = FXMLLoader.load(getClass().getResource("/view/allApps.fxml"));
                System.out.println("allApps.fxml path recognized");
                Scene scene = new Scene(apps);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointments");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("unable to save modified appointment to DB");
            }
        }
    }

    /**
     * this method checks to see if the data entered into the fields are valid.
     * @return
     */
    public boolean allFieldsValid(){
        //grabbing date/time from fields and creating start and end concat date/Times
        LocalDate date = datePicker.getValue();
        LocalTime start = (LocalTime) startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime end = (LocalTime) endTimeComboBox1.getSelectionModel().getSelectedItem();
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

        int customerID = customerComboBox.getSelectionModel().getSelectedIndex();
        int appID = -1;




        if(titleTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || titleTF.getText().isEmpty() ||
                typeTF.getText().isEmpty() || locationTF.getText().isEmpty() || contactCombobox.getItems().isEmpty() ||
                userComboBox.getItems().isEmpty() || contactCombobox.getItems().isEmpty()){

                //|| datePicker.getValue() == null ||startTimeComboBox.getSelectionModel().isEmpty() || endTimeComboBox1.getSelectionModel().isEmpty())
            Info.error("SAVE ERROR", "Empty field found while trying to save appointment. Please enter a value in all fields.");
            return false;
        } else if(start.isAfter(end)){
            Info.error("START TIME ERROR", "The start time cannot begin after the end time. Select a START time that begins before the END time.");
            return false;
        }
        else if(ZonedStart.isBefore(ESTBusinessStart) || ZonedStart.isAfter(ESTBusinessEnd) || ZonedEnd.isBefore(ZonedStart) || ZonedEnd.isAfter(ESTBusinessEnd)){
            Info.error("SCHEDULE ERROR", "Appointment time is outside of business hours. Please select a time during the hours of 8am and 10pm EST.");
            return false;
        }
        else if(start == end){
            Info.error("SCHEDULE ERROR", "Appointment start time and end time cannot be the same.");
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

    public boolean noOverlap(int customerID, int appID, LocalDateTime start, LocalDateTime end) {
        System.out.println("\nnoOverlap method used\n---------------");
        boolean hasConflict = false;
        for (Appointment app : allAppsList) {
            System.out.println("CustomerID Being compared: " + customerID +"\nLocal start for new appointment: " + start + "Local end for new appointment: " + end);

            LocalDateTime verifyStart = app.getAppStart();
            System.out.println("Start Time of app in system: " + verifyStart);

            LocalDateTime verifyEnd = app.getAppEnd();
            System.out.println("End Time of app in system: " + verifyEnd);
            System.out.println("for loop to check app time starting \n ------------------");

            int customerIDCheck = app.getCustomerID();
            int appIDCheck = app.getAppID();
            System.out.println("CustomerIDCheck: " + customerIDCheck + "\nappIDCheck: " + appIDCheck);

            if ((customerID == customerIDCheck) && (appID == appIDCheck)){
                System.out.println(appID + " and " +appIDCheck +" are the same appointment. No conflict.");
            }
            else if ((customerID == customerIDCheck) && (appID != appIDCheck)
                    && (start.isBefore(verifyStart) && end.isBefore(verifyStart)) || (start.isAfter(verifyEnd) && end.isAfter(verifyEnd))) {
                hasConflict = false;
                System.out.println("This appointment does not overlap any existing appointments with the same customer.");
            } else if(customerID != customerIDCheck){
                hasConflict = false;
                System.out.println("No appointment conflict. The customer ID does not match any existing appointments in the system.");
            }
            else{
                Info.error("OVERLAPPING APPOINTMENT", "The time of this appointment " +
                        "overlaps an existing appointment in the system for this customer.");
                return true;
            }
        } return hasConflict;
    }



    /**
     * This method cancels the modify appointment and returns to the appointments main screen.
     * @param actionEvent cancel
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

}
