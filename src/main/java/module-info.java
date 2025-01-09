module org.example.whatsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens org.example.whatsapp to javafx.fxml;
    exports org.example.whatsapp;
    exports org.example.whatsapp.Controllers;
    opens org.example.whatsapp.Controllers to javafx.fxml;
    exports org.example.whatsapp.Models;
    opens org.example.whatsapp.Models to javafx.fxml;
}