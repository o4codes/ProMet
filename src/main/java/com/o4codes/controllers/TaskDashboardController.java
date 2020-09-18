package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskDashboardController implements Initializable {
    @FXML
    private Label projectDescriptionLbl;

    @FXML
    private Label projectCreationDateLbl;

    @FXML
    private Label dueDateTitleLbl;

    @FXML
    private Label projectDueDateLbl;

    @FXML
    private JFXSpinner projectSpinnerLbl;

    @FXML
    private Label taskFractionProgressLbl;

    @FXML
    private Label timeFractionProgressLbl;

    @FXML
    private JFXButton editProjectDetailsBtn;

    @FXML
    private ComboBox<?> tasksSearchCombo;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton addNewTaskBtn;

    @FXML
    private VBox tasksContainerPane;

    @FXML
    private Label projectTitleLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void AddNewTaskEvent(ActionEvent event) {

    }

    @FXML
    void EditProjectDetailsEvent(ActionEvent event) {

    }

    @FXML
    void taskSearchEvent(ActionEvent event) {

    }

}
