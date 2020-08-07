package com.o4codes.helpers.alerts;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChoiceAlertController implements Initializable {
    @FXML
    private Label headingLabel;

    @FXML
    private Label messageLbl;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private JFXButton declineBtn;

    private boolean result;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void confirmEvent(ActionEvent event) {
        confirmBtn.getScene().getWindow().hide();
        result = true;
    }

    @FXML
    private void declineEvent(ActionEvent event) {
        declineBtn.getScene().getWindow().hide();
        result = false;
    }

    public void setComponents(String heading, String message){
        headingLabel.setText( heading );
        messageLbl.setText( message );
    }

    public boolean result(){
        return result;
    }


}
