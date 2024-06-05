package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class provides logic for confirmation alerts used throughout the project
 */
public class ConfirmationAlert {
    /**
     * The alert message
     */
    String strAlertMsg;
    /**
     * The alert header
     */
    String header;
    /**
     * The alert object
     */
    private Alert alert;

    /**
     * This lambda creates the alertOMatic code from the IAlertOMatic interface
     */
    public static IAlertOMatic alertOMatic = (String strAlertMsg, String header) ->{
        return new ConfirmationAlert(strAlertMsg,header);
    };

    /**
     * This constructor creates confirmation alert objects
     * @param strAlertMsg the alert message
     * @param header the alert header
     */
    public ConfirmationAlert(String strAlertMsg, String header) {
        this.strAlertMsg = strAlertMsg;
        this.header = header;
        try {
        alert = new Alert(Alert.AlertType.CONFIRMATION, strAlertMsg);
        alert.setTitle("");
        alert.setHeaderText(header);
        alert.setContentText(strAlertMsg);
    } catch (NullPointerException e) {
        e.printStackTrace();
        }
    }

    /**
     * This method returns an Optional for the alerts generated
     * @return an optional button type
     */
    public Optional<ButtonType> getTheResult() {
        return alert.showAndWait();
    }

}
