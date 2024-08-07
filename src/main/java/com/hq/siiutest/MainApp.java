package com.hq.siiutest;

import com.hq.siiutest.config.SiiuStyle;
import com.hq.siiutest.controller.SiiuController;
import com.hq.siiutest.models.Row;
import com.hq.siiutest.services.SiiuService;
import com.hq.siiutest.tools.SiiuParse;

import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        SiiuController siiuController = new SiiuController("COM5");
        List<Row> test = new ArrayList<>();
        test.add(new Row("V01", "1"));
        test.add(new Row("V02", "2"));
        test.add(new Row("V02", "2"));
        test.add(new Row("V02", "2"));
        test.add(new Row("V02", "2"));
        test.add(new Row("V02", "2"));

        siiuController.style(SiiuStyle.COLUMNS_BLACK);
        siiuController.showRows(test);

    }
}
