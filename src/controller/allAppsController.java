package controller;

import DAO.DBAppointments;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointment;
import model.info;
import model.user;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the appointments scene.
 */
public class allAppsController implements Initializable {
    public RadioButton monthRB;
    public RadioButton weekRB;
    public RadioButton allRB;
    public Button deleteButton;
    public Button backButton;
    public Button modifyButton;
    public Button addButton;

    public TableView appTable;
    public TableColumn userIDCol;
    public TableColumn customerIDCol;
    public TableColumn endCol;
    public TableColumn startCol;
    public TableColumn typeCol;
    public TableColumn contactCol;
    public TableColumn locationCol;
    public TableColumn descriptionCol;
    public TableColumn titleCol;
    public TableColumn appIDCol;

    public Button logoutButton;
    public Button reportsButton;
    public Button customersButton;
    public Button appointmentsButton;
    ObservableList<appointment> allAppsList = DBAppointments.getAllApps();


    /**
     * This method initializes the allAppsController
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        appTable.setItems(allAppsList);


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

    /**
     * This method logs the user out of the program and returns to the login screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionLogout(ActionEvent actionEvent) throws IOException {
        System.out.println("Logout button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        System.out.println("login.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Schedule Pro");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * This method loads the new appointment scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionAddApp(ActionEvent actionEvent) throws IOException {
        System.out.println("Add button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/addApp.fxml"));
        System.out.println("addApp.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("New Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method loads the modify appointment scene and transfers data about the selected appointment.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionModifyApp(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/modifyApp.fxml"));
        loader.load();

        modifyAppController mAController = loader.getController();
        mAController.sendAppointment((appointment) appTable.getSelectionModel().getSelectedItem());


        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Modify Appointment");
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /***
     * This method deletes the selected appointment from the database.
     * @param actionEvent
     */
    public void onActionDeleteApp(ActionEvent actionEvent) {
        if(appTable.getSelectionModel().getSelectedItem() != null){
            appointment deleteApp = (appointment) appTable.getSelectionModel().getSelectedItem();
            int appID = deleteApp.getAppID();
            System.out.println(appID + " appointment to be deleted from database");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Select OK to delete appointment.");
            alert.setHeaderText("Do you want to delete appointment " + appID + "?");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.get() == ButtonType.OK){
                deleteApp(appID);
                allAppsList.removeAll();
                allAppsList = DBAppointments.getAllApps();
                appTable.setItems(allAppsList);
                info.inform("DELETE APPOINTMENT", "Appointment " + appID + " has been deleted from the system.");
            } else {
                System.out.println("Appointment Delete canceled"); }
        }
        else {
                info.error("Delete Appointment", "Nothing selected. \n Please select an appointment to delete.");
        }
        }

    /**
     * This method removes appointments from the DB by the selected item in the tableview.
     * @param appID
     */
    public void deleteApp (int appID){

        try{
            String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appID);
            ps.executeUpdate();
            System.out.println("Appointment " + appID + " deleted from system.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method allows the user to travel back to home screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionBackButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Back button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        System.out.println("mainMenu.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Schedule Pro");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method selects all appointments listed in the database.
     * @param actionEvent
     */
    public void onActionSelectAllApp(ActionEvent actionEvent) {
        ObservableList<appointment> allAppsList = DBAppointments.getAllApps();
        appTable.setItems(allAppsList);
    }

    /***
     * This method only selects the appointments upcoming in the next week.
     * @param actionEvent
     */
    public void onActionWeekApp(ActionEvent actionEvent) {
        ObservableList<appointment> allAppsList = DBAppointments.getAllApps();
        ObservableList<appointment> appByWeek = FXCollections.observableArrayList();

        LocalDateTime plus1WK = LocalDateTime.now().plusWeeks(1);
        LocalDateTime minus1WK = LocalDateTime.now().minusWeeks(1);

        System.out.println("week RB clicked");

        if (allAppsList != null){
            allAppsList.forEach(appointment -> {
             if (appointment.getAppStart().isAfter(minus1WK) && appointment.getAppStart().isBefore(plus1WK) || ((appointment.getAppStart().equals(minus1WK) || appointment.getAppStart().equals(plus1WK)))){
                 appByWeek.add(appointment);


             }
             appTable.setItems(appByWeek);
            });
        }
    }

    /***
     * This method only selects the appointments upcoming in the next month.
     * @param actionEvent
     */
    public void onActionMonthApp(ActionEvent actionEvent) {
        ObservableList<appointment> allAppsList = DBAppointments.getAllApps();
        ObservableList<appointment> appByMonth = FXCollections.observableArrayList();

        LocalDateTime plus1Month = LocalDateTime.now().plusMonths(1);
        LocalDateTime minus1Month= LocalDateTime.now().minusMonths(1);

        System.out.println("month RB clicked");

            allAppsList.forEach(appointment -> {
                if (appointment.getAppStart().isAfter(minus1Month) && appointment.getAppStart().isBefore(plus1Month) || ((appointment.getAppStart().equals(minus1Month) || appointment.getAppStart().equals(plus1Month)))) {
                    appByMonth.add(appointment);
                }
                appTable.setItems(appByMonth);
                System.out.println(LocalDateTime.now());
            });
        }




}
