package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PomodoreActivityCycleController implements Initializable {

    @FXML
    private Label taskDurationLbl;

    @FXML
    private Label taskTitleLabel;

    @FXML
    private JFXButton removeTaskButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
