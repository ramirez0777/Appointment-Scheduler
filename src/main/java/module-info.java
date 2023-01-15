module c195.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens c195.c195 to javafx.fxml;
    exports c195.c195;
    exports c195.c195.reported;
    opens c195.c195.reported to javafx.fxml;
    opens c195.c195.displayed to javafx.fxml;
    exports c195.c195.displayed;
}