package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


/***
 * This controller class holds the methods needed for the add customer scene.
 */
public class addCustomerController {
    public Button cancelButton;
    public Button saveButton;
    public TextField IDTF;
    public TextField nameTF;
    public TextField addressTF;
    public TextField phoneNumTF;
    public TextField postalCodeTF;
    public ComboBox countryCombobox;
    public ComboBox FLDCombobox;


    /***
     * This method loafs the customer scene.
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


    /***
     * This method logs the user out of the application.
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
     * This method saves the data entered into the text fields.
     * @param actionEvent
     */
    public void onActionSaveCustomer(ActionEvent actionEvent) throws IOException {


        System.out.println("Cancel button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        System.out.println("customers.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method cancels modifying the selected customer and returns the user to the customer scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        System.out.println("customers.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
}
