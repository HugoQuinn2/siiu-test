package com.hq.siiutest.services;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public class SiiuService {
    private String com;
    private int baud;
    private int dataSize;
    private SerialPort port;

    private int timeout;

    public SiiuService(String com, int baud, int dataSize){
        this.com = com;
        this.baud = baud;
        this.dataSize = dataSize;
        this.timeout = 100;
    }

    private boolean connect(){
        port = SerialPort.getCommPort(com);
        port.setBaudRate(baud);
        port.setNumDataBits(dataSize);
        port.setParity(SerialPort.NO_PARITY);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, timeout, 0);

        return port.openPort();
    }

    public String command(byte[] command) {
        if (connect()) {
            try {

                port.getOutputStream().write(command);
                port.getOutputStream().flush();

                return readBuffer(port);

            } catch (Exception e) {
                System.err.println("Error Command: " + e.getMessage());
                return null;
            } finally {
                port.closePort();
            }
        }
        return null;
    }

    private String readBuffer(SerialPort port) throws IOException {
        byte[] readBuffer = new byte[1024];
        int numRead = 0;

        while ((System.currentTimeMillis() - System.currentTimeMillis()) < timeout) {
            if (port.getInputStream().available() > 0) {
                numRead = port.getInputStream().read(readBuffer);
                if (numRead > 0) {
                    return new String(readBuffer, 0, numRead);
                }
            }
        }

        if (numRead == 0) {
            System.out.println("No se recibieron datos dentro del tiempo de espera.");
        }

        return null;
    }
}
