module com.hq.siiutest {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;

    opens com.hq.siiutest to javafx.fxml;
    exports com.hq.siiutest;
    exports com.hq.siiutest.models;

    opens com.hq.siiutest.controller to javafx.fxml;
    opens com.hq.siiutest.models to javafx.base;
    opens com.hq.siiutest.controller.gui to javafx.fxml;

    exports com.hq.siiutest.controller;
    exports com.hq.siiutest.services;
    exports com.hq.siiutest.controller.gui;
}