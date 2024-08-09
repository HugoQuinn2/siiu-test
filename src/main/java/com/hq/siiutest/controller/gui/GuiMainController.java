package com.hq.siiutest.controller.gui;

import com.fazecast.jSerialComm.SerialPort;
import com.hq.siiutest.config.SiiuStyle;
import com.hq.siiutest.controller.SiiuController;
import com.hq.siiutest.controller.TestController;
import com.hq.siiutest.models.SiiuTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class GuiMainController {
    @FXML
    private ComboBox cbxComs;
    @FXML
    private ListView<String> lstTestsView;
    @FXML
    private Button btnTest;

    private SiiuController siiuController;
    private TestController testController;
    private List<SiiuTest> siiuTests;
    private SerialPort[] serialPorts;

    private static final Logger logger = LoggerFactory.getLogger(GuiMainController.class);

    @FXML
    public void initialize() {
        testController = new TestController();
        siiuTests = testController.getSiiuTests();

        serialPorts = SerialPort.getCommPorts();
        String[] portsName = new String[serialPorts.length];
        for (int i = 0 ; i < serialPorts.length ; i++) {
            portsName[i] = serialPorts[i].getSystemPortName();
        }

        cbxComs.getItems().addAll(portsName);
        cbxComs.setValue(portsName[0]);

        makeTestsView();
    }

    private void makeTestsView(){
        if (siiuTests != null) {
            ObservableList<String> txtTest = FXCollections.observableArrayList();
            for (SiiuTest test : siiuTests){
                txtTest.add(test.getName());
            }
            lstTestsView.setItems(txtTest);
        }

    }

    public void onBtnTests() {
        siiuController = new SiiuController(cbxComs.getValue().toString());
        siiuController.shotTestRows(siiuTests);
    }

    public void onBtnTest() {
        siiuController = new SiiuController(cbxComs.getValue().toString());
        String test = lstTestsView.getSelectionModel().getSelectedItem();
        if (test != null) {
            SiiuTest siiuTest = getSiiuTestByName(test);
            if (siiuTest != null) {
                logger.info("Ejecutando prueba: " + siiuTest.getName());
                SiiuStyle style = siiuController.getSiiuStyle(siiuTest.getStyle());
                siiuController.style(style);
                siiuController.showRows(siiuTest.getRows());
            }
        }
    }

    private SiiuTest getSiiuTestByName(String name) {
        for (SiiuTest test : siiuTests){
            if (test.getName().contains(name)) {
                return test;
            }
        }
        return null;
    }
}
