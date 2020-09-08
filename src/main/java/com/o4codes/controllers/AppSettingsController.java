package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class AppSettingsController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label deviceNameLabel;

    @FXML
    private JFXButton editProfileButton;

    @FXML
    private JFXButton changePasscodeEvent;

    @FXML
    private JFXButton editPomodoreSettings;

    @FXML
    private Label taskIntervalValueLbl;

    @FXML
    private Label taskIntervalLbl;

    @FXML
    private Label shortRestIntervalValueLbl;

    @FXML
    private Label shortRestIntervalLbl;

    @FXML
    private Label longRestIntervalValueLbl;

    @FXML
    private Label longRestIntervalLbl;

    @FXML
    private Label cocurentsProjectsValueLbl;

    @FXML
    private Label cocurentsProjectsLbl;

    @FXML
    private JFXButton preferencesBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // change fonts of some Labels
        deviceNameLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 24 ));
        userNameLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 24 ));
        taskIntervalLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
        shortRestIntervalLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
        longRestIntervalLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
        cocurentsProjectsLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
    }

    @FXML
    void EditPomodoreEvent(ActionEvent event) {

    }

    @FXML
    void EditProfileEvent(ActionEvent event) {

    }

    @FXML
    void PasscodeChangeEvent(ActionEvent event) {

    }

    @FXML
    void PreferencesEditEvent(ActionEvent event) {

    }

}
