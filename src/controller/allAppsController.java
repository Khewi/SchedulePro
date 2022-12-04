package controller;

import DAO.DBAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<appointment> allAppsList = DBAppointments.getAllApps();

        appTable.setItems(allAppsList);

        PropertyValueFactory<appointment, Integer> appIDFact = new PropertyValueFactory<>("appID");
        PropertyValueFactory<appointment, String> titleFact = new PropertyValueFactory<>("appTitle");
        PropertyValueFactory<appointment, String> descFact = new PropertyValueFactory<>("appDescription");
        PropertyValueFactory<appointment, String> locationFact = new PropertyValueFactory<>("appLocation");
        PropertyValueFactory<appointment, Integer> contactFact = new PropertyValueFactory<>("contactID");
        PropertyValueFactory<appointment, String> typeFact = new PropertyValueFactory<>("appType");
        PropertyValueFactory<appointment, LocalDateTime> startFact = new PropertyValueFactory<>("appStart");
        PropertyValueFactory<appointment, LocalDateTime> endFact = new PropertyValueFactory<>("appEnd");
        PropertyValueFactory<appointment, Integer> customerIDFact = new PropertyValueFactory<>("contactID");
        PropertyValueFactory<appointment, Integer> userIDFact = new PropertyValueFactory<>("userID");



        appIDCol.setCellValueFactory(appIDFact);
        titleCol.setCellValueFactory(titleFact);
        descriptionCol.setCellValueFactory(descFact);
        locationCol.setCellValueFactory(locationFact);
        contactCol.setCellValueFactory(contactFact);
        typeCol.setCellValueFactory(typeFact);
        startCol.setCellValueFactory(startFact);
        endCol.setCellValueFactory(endFact);
        customerIDCol.setCellValueFactory(customerIDFact);
        userIDCol.setCellValueFactory(userIDFact);




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
        System.out.println("Add button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/modifyApp.fxml"));
        System.out.println("addApp.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Modify Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method deletes the selected appointment from the database.
     * @param actionEvent
     */
    public void onActionDeleteApp(ActionEvent actionEvent) {
    }

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

        LocalDateTime weekStart = LocalDateTime.now().plusWeeks(1);
        LocalDateTime weekEnd = LocalDateTime.now().minusWeeks(1);

        if (allAppsList != null){
            allAppsList.forEach(appointment -> {
             if (appointment.getAppEnd().isAfter(weekStart) && appointment.getAppEnd().isBefore(weekEnd)){
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

        LocalDateTime monthStart = LocalDateTime.now().plusMonths(1);
        LocalDateTime monthEnd = LocalDateTime.now().minusMonths(1);

        if (allAppsList != null){
            allAppsList.forEach(appointment -> {
                if (appointment.getAppEnd().isAfter(monthStart) && appointment.getAppEnd().isBefore(monthEnd)){
                    appByMonth.add(appointment);
                }
                appTable.setItems(appByMonth);
            });
        }
    }


}
