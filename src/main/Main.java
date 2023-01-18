package main;

import DAO.DBReconciliation;
import database.DBConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Reconciliation;

import java.util.Locale;
import java.util.ResourceBundle;


/***
 * This class launches the Schedule Pro application.
 */
public class Main extends Application {
    /***
     * this method sets the login scene.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        stage.setTitle("Schedule Pro");
        stage.setScene(new Scene(root, 400, 200));
        stage.setResizable(false);
        stage.show();
    }

    /***
     * This method launches the application.
     * @param args
     */
    public static void main(String[] args){

        DBConnection.startConnection();

        launch(args);

        DBConnection.closeConnection();


    }
}
