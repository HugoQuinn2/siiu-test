package com.hq.siiutest.controller;

import com.hq.siiutest.config.SiiuStyle;
import com.hq.siiutest.models.Row;
import com.hq.siiutest.models.SiiuTest;
import com.hq.siiutest.services.SiiuService;
import com.hq.siiutest.tools.SiiuParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class SiiuController {
    private static final Logger logger = LoggerFactory.getLogger(SiiuController.class);

    private final SiiuService siiuService;
    private final SiiuParse siiuParse;

    public SiiuController(String com){
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

        int minRows = 3;
        if (size < minRows) {
            for (int i = 0; i < (minRows - size) ; i++){
                rows.add(new Row("&", "0"));
            }
        }

        String command = siiuParse.rows2Hex(rows);
        logger.info("Ejecutando comando: " + command);
        byte[] bytesCommand = siiuParse.hex2ByteArray(command);

        return siiuService.command(bytesCommand);
    }

    public void showTestRows(List<SiiuTest> siiuTestList) {
        for (SiiuTest siiuTest : siiuTestList) {
            try {
                if (!showTestRows(siiuTest)){
                    break;
                }
                Thread.sleep( siiuTest.getCooldTime() * 1000L);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    public boolean showTestRows(SiiuTest siiuTest){
        try {
            SiiuStyle style = getSiiuStyle(siiuTest.getStyle());
            if ( style(style) != null ) {
                Thread.sleep(100);
                return showRows(siiuTest.getRows()) != null;
            }
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public String style(SiiuStyle style) {
        logger.info("Ejecutando estilo: " + style);
        byte[] bytesCommand = siiuParse.hex2ByteArray(style.getCommand());
        logger.info("Ejecutando comando: " + style.getCommand());
        return siiuService.command(bytesCommand);
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
}
