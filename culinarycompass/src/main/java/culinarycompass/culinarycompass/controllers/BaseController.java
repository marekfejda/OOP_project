package culinarycompass.culinarycompass.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public abstract class BaseController {
    @FXML
    protected Label messageLabel;
    protected Color messageTextColor = Color.BLACK;

    protected void showMessage(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setTextFill(messageTextColor);
            messageLabel.setVisible(!message.isEmpty());
        }
    }

    protected void setMessageTextColor(Color color) {
        this.messageTextColor = color;
    }

}
