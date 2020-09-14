package com.o4codes.controllers;

import com.jfoenix.controls.JFXSpinner;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.ProjectSession;
import com.o4codes.database.dbTransactions.TaskTimelineSession;
import com.o4codes.models.Task;
import com.o4codes.models.TaskTimeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    @FXML
    private Label cardDueDateLblValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set fonts down
        cardProjectTitleLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 14));
        cardTaskTitleLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 12));
        cardTaskDescriptionLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 13));
        cardDueDateLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 12));
        cardDueDateLblValue.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 12));
        cardProjectDescriptionLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 12));
    }

    void fillCardDetails(Task task) throws IOException, SQLException {
        cardTaskTitleLbl.setText(task.getTitle());
        cardTaskDescriptionLbl.setText(task.getDescription());
        cardDueDateLblValue.setText(task.getDeadlineDate().toString());
        cardProjectTitleLbl.setText(ProjectSession.getProjectById(task.getProjectId()).getTitle());
        cardProjectDescriptionLbl.setText(ProjectSession.getProjectById(task.getProjectId()).getDescription());
        cardProjectProgressIndicator.setProgress(getTaskProgress(task));
    }

    private double getTaskProgress(Task task) throws IOException, SQLException {
        float seconds_to_be_used = task.getDuration(); // duration of tasks is registered in seconds.
        float seconds_used = 0;
        for (TaskTimeline timeline : TaskTimelineSession.getTaskTimeLineByTaskId(task.getTaskId())) {
            seconds_used += timeline.getTaskTimeSpent();
        }

        return seconds_used / seconds_to_be_used;
    }
}
