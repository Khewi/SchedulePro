package main;

import database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
        stage.show();
    }

    /***
     * This method launches the application.
     * @param args
     */
    public static void main(String[] args) {

        DBConnection.startConnection();

        launch(args);

        DBConnection.closeConnection();


    }
}
