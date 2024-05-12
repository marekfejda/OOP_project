package culinarycompass.culinarycompass.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Abstraktna trieda, ktora poskytuje zakladnu funkcionalitu pre zobrazovanie sprav v GUI.
 */
public abstract class BaseController {
    @FXML
    protected Label messageLabel;
    protected Color messageTextColor = Color.BLACK;

    /**
     * Zobrazi spravu v prislusnom grafickom prvku.
     *
     * @param message Text spravy, ktora sa ma zobrazit.
     */
    protected void showMessage(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setTextFill(messageTextColor);
            messageLabel.setVisible(!message.isEmpty());
        }
    }

    /**
     * Nastavi farbu textu zobrazenej spravy.
     *
     * @param color Farba, ktorou sa ma sprava zobrazit.
     */
    protected void setMessageTextColor(Color color) {
        this.messageTextColor = color;
    }
}
