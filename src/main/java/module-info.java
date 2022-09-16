module c195.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens c195.c195 to javafx.fxml;
    exports c195.c195;
}