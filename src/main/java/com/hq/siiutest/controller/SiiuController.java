package com.hq.siiutest.controller;

import com.hq.siiutest.config.SiiuStyle;
import com.hq.siiutest.models.Row;
import com.hq.siiutest.services.SiiuService;
import com.hq.siiutest.tools.SiiuParse;

import java.util.List;

public class SiiuController {
    private SiiuService siiuService;
    private SiiuParse siiuParse;
    private int minRows = 3;

    public SiiuController(String com){

        this.siiuService = new SiiuService(
                com,
                115200,
                8
        );

        this.siiuParse = new SiiuParse();
    }

    public void showRows(List<Row> rows) {
        int size = rows.size();

        if (size < minRows) {
            for (int i = 0; i < (minRows - size) ; i++){
                rows.add(new Row("&", "0"));
            }
        }

        String command = siiuParse.rows2Hex(rows);
        byte[] bytesCommand = siiuParse.hex2ByteArray(command);

        siiuService.command(bytesCommand);
    }

    public void style(SiiuStyle style) {

        byte[] bytesCommand = siiuParse.hex2ByteArray(style.getCommand());
        siiuService.command(bytesCommand);

    }
}
