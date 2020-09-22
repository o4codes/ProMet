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

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set fonts down
        cardProjectTitleLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 14));
        cardTaskTitleLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 12));
        cardTaskDescriptionLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 10));
        cardDueDateLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 12));
        cardDueDateLblValue.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 12));
        cardProjectDescriptionLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 12));
    }

    void setTask(Task task) throws IOException, SQLException {
        this.task = task;
        fillCardDetailsTask();
    }

    private void fillCardDetailsTask () throws IOException, SQLException {
        cardTaskTitleLbl.setText(this.task.getTitle());
        cardTaskDescriptionLbl.setText(this.task.getDescription());
        cardDueDateLblValue.setText(this.task.getDeadlineDate().toString());
        cardProjectTitleLbl.setText(ProjectSession.getProjectById(this.task.getProjectId()).getTitle());
        cardProjectDescriptionLbl.setText(ProjectSession.getProjectById(this.task.getProjectId()).getDescription());
        cardProjectProgressIndicator.setProgress(getTaskProgress());
    }

    private double getTaskProgress() throws IOException, SQLException {
        float seconds_to_be_used = this.task.getDuration(); // duration of tasks is registered in seconds.
        float seconds_used = 0;
        for (TaskTimeline timeline : TaskTimelineSession.getTaskTimeLineByTaskId(this.task.getTaskId())) {
            seconds_used += timeline.getTaskTimeSpent();
        }

        return seconds_used / seconds_to_be_used;
    }
}
