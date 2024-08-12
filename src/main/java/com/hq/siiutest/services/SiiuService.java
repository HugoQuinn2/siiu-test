package com.hq.siiutest.services;

import com.fazecast.jSerialComm.SerialPort;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SiiuService {
    private static final Logger logger = LoggerFactory.getLogger(SiiuService.class);

    private final String com;
    private final int baud;
    private final int dataSize;
    private SerialPort port;

    private final int timeout;

    public SiiuService(String com, int baud, int dataSize){
        this.com = com;
        this.baud = baud;
        this.dataSize = dataSize;
        this.timeout = 1000;
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

                Thread.sleep(350);
                return readBuffer(port);
            } catch (Exception e) {
                logger.error("Error en comando: " + Arrays.toString(command), e);
                return null;
            } finally {
                port.closePort();
            }
        } else {
            alertError(
                    "Error de Comunicación " + com,
                    "No se pudo establecer conexion al puerto."
            );
        }
        return null;
    }

    private String readBuffer(SerialPort port) throws IOException{
        byte[] readBuffer = new byte[5];
        int numRead = 0;

        if (port.getInputStream().available() > 0) {
            numRead = port.readBytes(readBuffer, readBuffer.length);
            if (numRead > 0) {
                logger.info("Respuesta de comando: " + Arrays.toString(readBuffer));
                return Arrays.toString(readBuffer);
            }
        }

        if (numRead == 0) {
            logger.info("No se obtuvo respuesta");
            alertError(
                    "Error de Comunicación " + com,
                    "No se obtuvo respuesta del equipo."
            );
        }

        return null;
    }

    private void alertError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
