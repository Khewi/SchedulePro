package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/***
 * This controller class holds the methods required for the login scene.
 */
public class loginController implements Initializable {
    public Text genZoneTxt;
    public Button loginButton;
    public Button exitButton;
    public PasswordField passField;
    public TextField userField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("loginController initialized");
    }


    /***
     * This method logs the user into the main menu scene.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionLogin(ActionEvent actionEvent) throws IOException {
        System.out.println("Login button clicked");
     Parent mainMenu = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        System.out.println("mainMenu.fxml path recognized");
     Scene scene = new Scene(mainMenu);
     Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
     stage.setTitle("Home");
     stage.setScene(scene);
     stage.show();

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
