package com.o4codes.helpers.alerts;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoAlertController implements Initializable {
    @FXML
    private Label headingLabel;

    @FXML
    private Label messageLbl;

    @FXML
    private JFXButton dismissBtn;

    @FXML
    private AnchorPane anchorPane;


    private String heading, message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeIn fadeIn = new FadeIn( anchorPane );
        fadeIn.play();
    }

    @FXML
    private void dismissAlert(ActionEvent event) {
        dismissBtn.getScene().getWindow().hide();
    }

    public void setComponents(String heading, String message) {
        headingLabel.setText( heading );
        messageLbl.setText( message );
    }

}
