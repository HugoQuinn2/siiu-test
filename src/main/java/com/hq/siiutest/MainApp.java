package com.hq.siiutest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainApp extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("Ejecutando Aplicacion");

        FXMLLoader GuiMain = new FXMLLoader(MainApp.class.getResource("/com/hq/siiutest/statics/GuiMain.fxml"));
        Scene scene = new Scene(GuiMain.load(), 1000, 550);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Siiu Tests");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(550);
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(550);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
