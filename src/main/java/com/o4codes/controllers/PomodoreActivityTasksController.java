package com.o4codes.controllers;

import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskTimelineSession;
import com.o4codes.helpers.Utils;
import com.o4codes.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PomodoreActivityTasksController implements Initializable {
    @FXML
    private Label taskTitleLabel;

    @FXML
    private Label taskDurationLbl;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set Font to label
        taskTitleLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 12 ) );
        taskDurationLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 11 ) );
    }

    public void setTask(Task task) throws IOException, SQLException {
        this.task = task;
        taskTitleLabel.setText( task.getTitle() );

        //convert duration which is minutes to seconds
        int duration_to_be_used = task.getDuration() *60; // converted to seconds
        int duration_used = TaskTimelineSession.getTaskTotalTimeConsumed( task );
        int time_left = duration_to_be_used - duration_used;
        taskDurationLbl.setText( Utils.getTimeLeftInMinutes( time_left ) );
    }



}
