module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    exports com.example.Controller;
    exports com.example.Models;
    exports com.example.DTO;
    exports com.example;

    exports com.example.Helper;
    exports com.example.Utils;
    exports com.example.Common;
    exports com.example.Exception;

    opens com.example to javafx.fxml;
    opens com.example.Controller to javafx.fxml;
    opens com.example.DTO to javafx.fxml;
    opens com.example.Helper to javafx.fxml;
    opens com.example.Utils to javafx.fxml;
    opens com.example.Common to javafx.fxml;
    opens com.example.Exception to javafx.fxml;
    opens com.example.Models to javafx.fxml;

}
