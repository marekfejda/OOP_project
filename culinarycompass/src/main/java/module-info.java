module culinarycompass.culinarycompass {
    requires javafx.controls;
    requires javafx.fxml;


    opens culinarycompass.culinarycompass to javafx.fxml;
    exports culinarycompass.culinarycompass;
}