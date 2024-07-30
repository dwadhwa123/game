module com.example.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    requires org.controlsfx.controls;
    requires org.mongodb.driver.core;
    requires javafx.graphics;

    opens com.example.game to javafx.fxml;
    exports com.example.game;
}