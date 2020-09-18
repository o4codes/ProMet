package com.o4codes.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskConfigController implements Initializable {
    @FXML
    private Label formTitleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private TextField taskTitleField;

    @FXML
    private JFXTextArea taskDescriptionField;

    @FXML
    private Spinner<?> durationHourField;

    @FXML
    private Spinner<?> durationMinField;

    @FXML
    private JFXDatePicker deadlineDateField;

    @FXML
    private JFXTimePicker deadlineTimeField;

    @FXML
    private JFXCheckBox mileStoneTaskToggle;

    @FXML
    private JFXButton createEditTaskBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void CreateEditTaskEvent(ActionEvent event) {

    }


}
