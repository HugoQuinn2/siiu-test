package com.hq.siiutest.models;

import com.hq.siiutest.config.SiiuStyle;

import java.util.List;

public class SiiuTest {
    private String name;
    private String style;
    private List<Row> rows;
    private int cooldTime;

    public SiiuTest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public int getCooldTime() {
        return cooldTime;
    }

    public void setCooldTime(int cooldTime) {
        this.cooldTime = cooldTime;
    }

    @Override
    public String toString() {
        return "SiiuTest{" +
                "name='" + name + '\'' +
                ", style='" + style + '\'' +
                ", rows=" + rows +
                ", cooldTime=" + cooldTime +
                '}';
    }
}
