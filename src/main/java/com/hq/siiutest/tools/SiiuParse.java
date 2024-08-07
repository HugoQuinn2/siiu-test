package com.hq.siiutest.tools;

import com.hq.siiutest.models.Row;

import java.util.ArrayList;
import java.util.List;

public class SiiuParse {
    public byte[] hex2ByteArray(String hex){
        String parseHex = hex.replaceAll(" ", "");
        int len = parseHex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(parseHex.charAt(i), 16) << 4)
                    + Character.digit(parseHex.charAt(i+1), 16));
        }
        return data;
    }

    public String rows2Hex(List<Row> rows) {
        List<String> output = new ArrayList<>();

        List<String> view = getView(rows);
        String size = Integer.toHexString(view.size() + 1).toUpperCase();

        char rowsSize = String.valueOf(rows.size()).toCharArray()[0];

        view.addFirst( toAsciiHex(rowsSize));
        view.addFirst("30");
        view.addFirst(size);
        view.addLast(getCheckSum(view));

        output.addAll(view);
        output.addFirst("02");
        output.addLast("03");

        return String.join(" ", output);
    }

    private List<String> getView(List<Row> rows) {
        List<String> view = new ArrayList<>();

        for (Row row : rows) {
            view.add("2C");
            view.addAll( text2Hex(row.getName()) );
            view.add("2C");
            view.addAll( text2Hex(row.getTime()) );
        }
        return view;
    }

    private String getCheckSum(List<String> plot) {
        int result = 0;

        for (String hex : plot) {
            int num = Integer.parseInt(hex, 16);
            result ^= num;
        }
        String checkSum = Integer.toHexString(result).toUpperCase();
        return String.format("%02d", Integer.parseInt(checkSum));
    }

    private String toAsciiHex(char input) {
        return Integer.toHexString((int) input);
    }

    private List<String> text2Hex(String input) {
        char[] letters = input.toCharArray();
        List<String> output = new ArrayList<>();

        for (char letter : letters) {
            output.add( toAsciiHex(letter) );
        }

        return output;
    }
}
