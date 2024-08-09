package com.hq.siiutest.controller;

import com.hq.siiutest.config.SiiuStyle;
import com.hq.siiutest.models.Row;
import com.hq.siiutest.models.SiiuTest;
import com.hq.siiutest.services.SiiuService;
import com.hq.siiutest.tools.SiiuParse;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class SiiuController {
    private static final Logger logger = LoggerFactory.getLogger(SiiuController.class);

    private SiiuService siiuService;
    private SiiuParse siiuParse;
    private int minRows = 3;
    private String com;

    public SiiuController(String com){
        this.com = com;
        this.siiuService = new SiiuService(
                com,
                115200,
                8
        );

        this.siiuParse = new SiiuParse();
        logger.info("Comunicacion abierta en " + com);
    }

    public String showRows(List<Row> rows) {
        int size = rows.size();

        if (size < minRows) {
            for (int i = 0; i < (minRows - size) ; i++){
                rows.add(new Row("&", "0"));
            }
        }

        String command = siiuParse.rows2Hex(rows);
        byte[] bytesCommand = siiuParse.hex2ByteArray(command);

        String output = siiuService.command(bytesCommand);

        if (output == null){
            logger.error("No se pudo utilizar el puerto " + com);
            alertError("Error en Puerto", "No se pudo enviar el comando de visualisacion, verifica que el puerto " + com + "esta en funcionamiento");
        }

        return output;
    }

    public void shotTestRows(List<SiiuTest> siiuTestList) {
        for (SiiuTest siiuTest : siiuTestList) {
            try {
                style(getSiiuStyle(siiuTest.getStyle()));
                if (showRows(siiuTest.getRows()) == null) {
                    break;
                }
                Thread.sleep( siiuTest.getCooldTime() * 1000L);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void style(SiiuStyle style) {
        logger.info("Ejecutando estilo: " + style);
        byte[] bytesCommand = siiuParse.hex2ByteArray(style.getCommand());
        siiuService.command(bytesCommand);

    }

    public SiiuStyle getSiiuStyle(String styleTxt) {
        SiiuStyle style = null
                ;
        style = Objects.equals(styleTxt, "COLUMNS_BLACK") ? SiiuStyle.COLUMNS_BLACK : style ;
        style = Objects.equals(styleTxt, "COLUMNS_RED") ? SiiuStyle.COLUMNS_RED : style ;
        style = Objects.equals(styleTxt, "ROWS_BLACK") ? SiiuStyle.ROWS_BLACK : style ;
        style = Objects.equals(styleTxt, "ROWS_RED") ? SiiuStyle.ROWS_RED : style ;

        return style;
    }

    public void alertError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
