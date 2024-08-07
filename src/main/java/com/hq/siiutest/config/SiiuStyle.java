package com.hq.siiutest.config;

public enum SiiuStyle {
    ROWS_RED    ("02 05 31 00 02 00 00 01 37 03"),
    COLUMNS_RED ("02 05 31 01 02 00 00 01 36 03"),
    COLUMNS_BLACK ("02 05 31 01 01 00 00 01 35 03"),
    ROWS_BLACK("02 05 31 00 01 00 00 01 34 03");

    private final String command;

    SiiuStyle(String command){
        this.command = command;
    }

    public String getCommand(){
        return command;
    }
}
