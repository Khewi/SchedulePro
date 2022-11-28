package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.io.IOException;


/***
 * This controller class holds the methods required for the appointments scene.
 */
public class allAppsController {
    public RadioButton monthRB;
    public RadioButton weekRB;
    public RadioButton allRB;
    public Button deleteButton;
    public Button backButton;
    public Button modifyButton;
    public Button addButton;
    public TreeTableColumn phoneNumCol;
    public TreeTableColumn customerID;
    public TreeTableColumn endCol;
    public TreeTableColumn startCol;
    public TreeTableColumn typeCol;
    public TreeTableColumn contactCol;
    public TreeTableColumn locationCol;
    public TreeTableColumn descriptionCol;
    public TreeTableColumn titleCol;
    public TreeTableColumn appIDCol;
    public Button logoutButton;
    public Button reportsButton;
    public Button customersButton;
    public Button appointmentsButton;


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
    public void onActionReportsScreen(ActionEvent actionEvent) {

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
    }

    /***
     * This method only selects the appointments upcoming in the next week.
     * @param actionEvent
     */
    public void onActionWeekApp(ActionEvent actionEvent) {
    }


    /***
     * This method only selects the appointments upcoming in the next month.
     * @param actionEvent
     */
    public void onActionMonthApp(ActionEvent actionEvent) {
    }
}
