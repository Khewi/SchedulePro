package model;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/***
 * This class provides error message templates that will be displayed to the user.
 */
public class info {

    /***
     * This is a standard error message error that will be used to alert the user of input or user action issues.
     * @param title
     * @param content
     */
    public static void error(String title, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /***
     * This is a standard confirm alert method that is used to confirm user actions.
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static Boolean confirm(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> input = alert.showAndWait();
        if (input.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }
}
