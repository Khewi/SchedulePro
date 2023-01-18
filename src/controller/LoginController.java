package controller;


import DAO.DBAppointments;
import DAO.DBReconciliation;
import database.DBConnection;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Info;
import model.Reconciliation;
import model.User;
import util.DateTime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;



/***
 * This controller class holds the methods required for the login scene.
 */
public class LoginController implements Initializable {
    public Text genZoneTxt;
    public Button loginButton;
    public Button exitButton;
    public PasswordField passField;
    public TextField userField;
    public Text zoneIDLabel;
    public Text passwordLabel;
    public Text usernameLabel;
    public Text scheduleProlabel;
    ObservableList<Appointment> appReminderList = DBAppointments.getAllApps();
    ResourceBundle rb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("loginController initialized");

        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
        ZoneId zone = ZoneId.systemDefault();

        genZoneTxt.setText(String.valueOf(zone));


        ResourceBundle rb = ResourceBundle.getBundle("language/language");

        scheduleProlabel.setText(rb.getString("Title"));
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        loginButton.setText(rb.getString("Login"));
        exitButton.setText(rb.getString("Exit"));

        if(DBReconciliation.checkRecTableExist() == true){
            ObservableList<Reconciliation> allRecs = DBReconciliation.getReconciliations();
            System.out.println("Reconciliation table exists in the database");
            if(allRecs.isEmpty()){
                DBReconciliation.insertDummyTasks();
                System.out.println("dummy tasks inserted to reconciliation table.");
            }
        }else{
            try{
                DBConnection.getConnection();
                String sql = "CREATE TABLE reconciliations (Reconciliation_ID INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), description VARCHAR(150), due_by TIMESTAMP, Customer_ID INT, FOREIGN KEY (Customer_ID) REFERENCES customers(Customer_ID));";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.executeUpdate();
                System.out.println("Reconciliation table was inserted into the database");
                DBReconciliation.insertDummyTasks();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


    }



    /***
     * This method logs the user into the main menu scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException {
        ResourceBundle rb = ResourceBundle.getBundle("language/language");

        String usernameText = userField.getText();
        String passwordText = passField.getText();
        int userID = getUserID(usernameText);
        User u = new User();
        u.setUserID(userID);
        u.setUsername(usernameText);
        u.setPassword(passwordText);

        ObservableList<Appointment> getAllUserApps = DBAppointments.getAppsByUserID(userID);
        LocalDateTime appStart;
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime plus15min = LocalDateTime.now().plusMinutes(15);
        LocalDateTime minus15min = LocalDateTime.now().minusMinutes(15);
        LocalDateTime appTimeReminder = null;
        int getAppID;
        Boolean appIn15Min = false;


        if (validateUserPass(userID, passwordText)) {
            activityLog(u.getUsername(), true);


            Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            System.out.println("mainMenu.fxml path recognized");
            Scene scene = new Scene(mainMenu);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            for (Appointment appointment : getAllUserApps) {
                System.out.println("Logged in userID: " + userID);
                appStart = appointment.getAppStart();
                getAppID = appointment.getAppID();
                int checkAppUserId = appointment.getUserID();
                System.out.println("Appointment Check - userID: " + checkAppUserId + "  AppID: " + getAppID + " appStart: " + appStart);

                if (userID == checkAppUserId && (appStart.isBefore(plus15min) || appStart.isEqual(plus15min)) && (appStart.isAfter(minus15min)) || appStart.isEqual(minus15min)) {
                    System.out.println("time check is running");
                    appIn15Min = true;
                    getAppID = appointment.getAppID();
                    appStart = appointment.getAppStart();
                    Info.confirm("Upcoming Appointment", "You have an an upcoming appointment", "Appointment " + getAppID + " starts at " + appStart);
                }
                if(appIn15Min != false){
                    System.out.println("Upcoming appointment identified" + getAppID + " at " + appStart);
                }
            }
            if(appIn15Min == false){
                Info.confirm("Time to relax", "You do not have any upcoming appointments.", "");
                System.out.println("No upcoming appointments found.");
            }

    }
        else if (userField.getText().isEmpty() || passField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(rb.getString("Error"));
            alert.setTitle(rb.getString("Error"));
            alert.setContentText(rb.getString("Message"));
            alert.showAndWait();
            activityLog(u.getUsername(), false);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("Error"));
            alert.setContentText(rb.getString("SecMessage"));
            alert.showAndWait();
            activityLog(u.getUsername(), false);
        }

    }


    /**
     * Private method is used to assign the correct userID against the username input.
     * @param usernameText
     * @return userID
     * @throws SQLException
     */
    public int getUserID(String usernameText) throws SQLException {
        int userID= -1;

        String sql = "SELECT User_ID FROM users WHERE User_Name='"+ usernameText +"'";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            userID = rs.getInt("User_ID");
        }
        System.out.println("UserID pulled from database: " + userID);
        return userID;

    }

    /**
     *This private method checks to see if the userID and password match a user in the MySQL database.
     * @param userID
     * @param password
     * @return boolean
     * @throws SQLException
     */
    private Boolean validateUserPass(int userID, String password) throws SQLException {
        String sql = "SELECT Password FROM users WHERE User_ID = '" + userID + "'";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            if (rs.getString("Password").equals(password)) {
                return true;
            }

        }
        return false;
    }

    /***
     * method logs the users attempt to log into the application. It records to status of the attempt in the file login_activity.txt.
     * @param user
     * @param attempt
     */
    public void activityLog(String user, Boolean attempt){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
            System.out.println("file created");
            if (attempt == true){
                writer.append(user + " login attempt was successful at " + DateTime.getTime() + "\n");
                System.out.println("New login attempt recorded in login_activity.txt");
                writer.flush();
                writer.close();
            } else {
                writer.append(user + " login attempt failed at " + DateTime.getTime() + "\n");
                System.out.println("New login attempt recorded in login_activity.txt");
                writer.flush();
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Exit Button closes the program
     * @param actionEvent
     */
    public void onActionExit(ActionEvent actionEvent) {
        System.out.println("Exit button clicked");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        System.out.println("Program closed");
    }
}
