package com.hq.siiutest.controller.gui;

import com.fazecast.jSerialComm.SerialPort;
import com.hq.siiutest.controller.SiiuController;
import com.hq.siiutest.controller.TestController;
import com.hq.siiutest.models.SiiuTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.List;


public class GuiMainController {
    @FXML
    private ComboBox<String> cbxComs;
    @FXML
    private ListView<String> lstTestsView;

    private SiiuController siiuController;
    private List<SiiuTest> siiuTests;

//    private static final Logger logger = LoggerFactory.getLogger(GuiMainController.class);

    @FXML
    public void initialize() {
        TestController testController = new TestController();
        siiuTests = testController.getSiiuTests();

        SerialPort[] serialPorts = SerialPort.getCommPorts();
        String[] portsName = new String[serialPorts.length];

        for (int i = 0; i < serialPorts.length ; i++) {
            portsName[i] = serialPorts[i].getSystemPortName();
        }

        if (serialPorts.length != 0){
            cbxComs.getItems().addAll(portsName);
            cbxComs.setValue(portsName[0]);
        } else {
            cbxComs.getItems().add("None");
            cbxComs.setValue("None");
        }

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
        siiuController = new SiiuController(cbxComs.getValue());
        siiuController.showTestRows(siiuTests);
    }

    public void onBtnTest() {
        siiuController = new SiiuController(cbxComs.getValue());
        String test = lstTestsView.getSelectionModel().getSelectedItem();
        if (test != null) {
            SiiuTest siiuTest = getSiiuTestByName(test);
            if (siiuTest != null) {
                siiuController.showTestRows(siiuTest);
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
