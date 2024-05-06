module culinarycompass.culinarycompass {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports culinarycompass.culinarycompass to javafx.graphics, javafx.fxml;
    exports culinarycompass.culinarycompass.controllers to javafx.fxml;
    opens culinarycompass.culinarycompass.controllers to javafx.fxml;

    // If your models are also used in FXML, consider opening them as well
    opens culinarycompass.culinarycompass.models to javafx.fxml;
    exports culinarycompass.culinarycompass.models;
}
