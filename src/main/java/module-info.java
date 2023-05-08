module javaFXEmailClientCourse {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens com.email.app;
    opens com.email.app.controller;
    opens com.email.app.model;
}