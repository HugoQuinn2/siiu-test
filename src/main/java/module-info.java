module com.hq.siiutest {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;


    exports com.hq.siiutest;
    exports com.hq.siiutest.models;
    exports com.hq.siiutest.controller;
    exports com.hq.siiutest.services;
    exports com.hq.siiutest.controller.gui;
    exports com.hq.siiutest.tools;
    exports com.hq.siiutest.config;

    opens com.hq.siiutest.controller to javafx.fxml;
    opens com.hq.siiutest.models to javafx.base;
    opens com.hq.siiutest.controller.gui to javafx.fxml;
    opens com.hq.siiutest to javafx.fxml;


}