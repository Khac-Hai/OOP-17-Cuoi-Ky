module com.example.cuoikyy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cuoikyy to javafx.fxml;
    exports com.example.cuoikyy;
}