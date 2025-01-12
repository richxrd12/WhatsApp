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
    requires com.google.gson;

    opens org.example.whatsapp to javafx.fxml;
    opens org.example.whatsapp.Objects to com.google.gson;

    exports org.example.whatsapp;
    exports org.example.whatsapp.Controllers;
    exports org.example.whatsapp.Objects;

    opens org.example.whatsapp.Controllers to javafx.fxml;
}