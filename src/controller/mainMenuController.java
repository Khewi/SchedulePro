package controller;

import DAO.DBAppointments;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the main menu scene.
 */
public class mainMenuController implements Initializable {
    public Button reportsButton;
    public Button logoutButton;
    public Button customersButton;
    public Button appointmentsButton;
    public TableColumn endCol;
    public TableColumn startCol;
    public TableColumn appIDcol;
    public TableView appointmentTable;
    int userID;





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



    /***
     * This method logs the user out of the application.
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    private void setTable(int userID){
        appointmentTable.setItems(DBAppointments.getAppsByUserID(userID));

        appIDcol.setCellFactory(new PropertyValueFactory<>("appID"));
        startCol.setCellFactory(new PropertyValueFactory<>("Start"));
        endCol.setCellFactory(new PropertyValueFactory<>("end"));
    }

    public void sendUser(int userID) {
        this.userID = userID;
        System.out.println(userID);
    }
}
