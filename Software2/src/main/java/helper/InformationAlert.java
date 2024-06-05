package helper;

import javafx.scene.control.Alert;


/**
 * This class provides logic for information alerts used throuhout the project
 */
public class InformationAlert {
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
    Alert alert;

    /**
     * This constructor creates information alert objects
     * @param strAlertMsg the alert message
     * @param header the alert header
     */
    public InformationAlert(String strAlertMsg, String header) {
        this.strAlertMsg = strAlertMsg;
        this.header = header;
        alert = new Alert(Alert.AlertType.INFORMATION, strAlertMsg);
        alert.setTitle("");
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}
