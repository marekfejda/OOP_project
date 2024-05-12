package culinarycompass.culinarycompass.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Abstraktná trieda, ktorá poskytuje základnú funkcionalitu pre zobrazovanie správ v GUI.
 */
public abstract class BaseController {
    @FXML
    protected Label messageLabel;
    protected Color messageTextColor = Color.BLACK;

    /**
     * Zobrazí správu v príslušnom grafickom prvku.
     *
     * @param message Text správy, ktorá sa má zobraziť.
     */
    protected void showMessage(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setTextFill(messageTextColor);
            messageLabel.setVisible(!message.isEmpty());
        }
    }

    /**
     * Nastaví farbu textu zobrazenej správy.
     *
     * @param color Farba, ktorou sa má správa zobraziť.
     */
    protected void setMessageTextColor(Color color) {
        this.messageTextColor = color;
    }
}
