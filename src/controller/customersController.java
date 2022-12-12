package controller;

import DAO.BDCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/***
 * This controller class holds the method required for the customer scene.
 */
public class customersController implements Initializable {
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;
    public TableColumn cusIDcol;
    public TableColumn nameCol;
    public TableColumn addressCol;
    public TableColumn countryCol;
    public TableColumn postalCol;
    public TableColumn phoneNumCol;
    public Button addButton;
    public Button modifyButton;
    public Button deleteButton;
    public Button backButton;
    public TableView customerTable;
    public TableColumn stateFLDCol;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customer> allCustomers = BDCustomers.getCustomers();


        cusIDcol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

        customerTable.setItems(allCustomers);
    }

    /***
     * This method loads the customers scene.
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
     * This method loads the add customer scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        System.out.println("Add button clicked");
        Parent apps = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        System.out.println("addCustomer.fxml path recognized");
        Scene scene = new Scene(apps);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("New Customer");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method loads the modify customer scene and transfers data of the selected customer.
     * @param actionEvent
     */
    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify button clicked");
        Parent login = FXMLLoader.load(getClass().getResource("/view/modifyCustomer.fxml"));
        System.out.println("modifyCustomer.fxml path recognized");
        Scene scene = new Scene(login);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Modify Customer");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * This method deletes the selected customer.
     * @param actionEvent
     */
    public void onActionDeleteCustomer(ActionEvent actionEvent) {
    }

    /***
     * This method returns the user to the main menu scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionBackButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Back button clicked");
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        System.out.println("mainMenu.fxml path recognized");
        Scene scene = new Scene(mainMenu);
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }


}
