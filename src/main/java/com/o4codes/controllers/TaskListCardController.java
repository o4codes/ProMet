package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskListCardController implements Initializable {
    @FXML
    private Label taskTtitleLbl;

    @FXML
    private Label taskDescriptionLbl;

    @FXML
    private JFXProgressBar taskProgressBar;

    @FXML
    private Label timeUsedLbl;

    @FXML
    private Label timeLeftLbl;

    @FXML
    private MenuItem editTaskBtn;

    @FXML
    private MenuItem deleteTaskBtn;

    @FXML
    private MenuItem markAsDoneBtn;

    @FXML
    private HBox finishedTaskIconPane;

    @FXML
    private AnchorPane resumeBtnPane;

    @FXML
    private JFXButton resumeTaskBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void DeleteTaskEvent(ActionEvent event) {

    }

    @FXML
    void EditTaskEvent(ActionEvent event) {

    }

    @FXML
    void MarkAsDoneEvent(ActionEvent event) {

    }

    @FXML
    void ResumeTaskEvent(ActionEvent event) {

    }


}
