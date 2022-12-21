package controller;

import DAO.DBCustomers;
import DAO.DBAppointments;
import database.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Info;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;


/***
 * This controller class holds the method required for the customer scene.
 */
public class CustomersController implements Initializable {
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
    ObservableList<Customer> allCustomers = DBCustomers.getCustomers();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        cusIDcol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        stateFLDCol.setCellValueFactory(new PropertyValueFactory<>("division"));

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
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("New Customer");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * This method loads the modify customer scene and transfers data of the selected customer.
     * @param actionEvent
     */
    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/modifyCustomer.fxml"));
        loader.load();

        ModifyCustomerController mCController = loader.getController();
        mCController.sendCustomer((Customer) customerTable.getSelectionModel().getSelectedItem());


        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Modify Customer");
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /***
     * This method deletes the selected customer.
     * @param actionEvent
     */
    public void onActionDeleteCustomer(ActionEvent actionEvent) {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Appointment> allAppsCheck = DBAppointments.getAllApps();
            Customer deleteCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            int cusId = deleteCustomer.getCustomerID();
            String custName = deleteCustomer.getCustomerName();
            System.out.println("Customer to be deleted: " + cusId + " " + deleteCustomer.getCustomerName());
            AtomicBoolean hasConflict = new AtomicBoolean(false);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE APPOINTMENT");
            alert.setHeaderText("Do you want to delete " + deleteCustomer.getCustomerName() + " from the system?");
            alert.setContentText("Click OK to delete customer.");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                for (Appointment app : allAppsCheck) {
                    if (app.getCustomerID() == cusId) {
                        hasConflict.set(true);
                        Info.error("ERROR", "Unable to delete customer. There are still active appointments for this customer. Please delete all appointments for this customer before trying again.");
                        break;
                    } else {
                        hasConflict.set(false);
                        try {
                            deleteCustomer(cusId);
                            allCustomers.removeAll();
                            allCustomers = DBCustomers.getCustomers();
                            customerTable.setItems(allCustomers);
                            Info.inform("DELETE SUCCESSFUL", custName + " has been deleted from the system.");
                            break;

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Customer delete canceled.");
            }
        }else{
                Info.error("DELETE CUSTOMER", "Nothing selected. \n Please select a customer to delete.");
            }
        }



    private void deleteCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();
        System.out.println("Customer " + customerID + " has been deleted from the database.");
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
