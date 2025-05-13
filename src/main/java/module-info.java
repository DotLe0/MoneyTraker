module com.example.moneytraker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.moneytraker to javafx.fxml;
    exports com.example.moneytraker;
}