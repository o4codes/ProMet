package com.o4codes.controllers;

import com.jfoenix.controls.JFXSpinner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DeadlineCardController implements Initializable {
    @FXML
    private VBox cardPane;

    @FXML
    private Label cardProjectTitleLbl;

    @FXML
    private JFXSpinner cardProjectProgressIndicator;

    @FXML
    private Label cardTaskTitleLbl;

    @FXML
    private Label cardTaskDescriptionLbl;

    @FXML
    private Label cardDueDateLbl;

    @FXML
    private Label cardProjectDescriptionLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
