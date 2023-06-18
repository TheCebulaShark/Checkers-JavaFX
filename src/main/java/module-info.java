module com.example.checkerajavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.checkerajavafx to javafx.fxml;
    exports com.example.checkerajavafx;
}