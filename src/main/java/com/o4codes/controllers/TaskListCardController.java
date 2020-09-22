package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.database.dbTransactions.TaskTimelineSession;
import com.o4codes.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class TaskListCardController implements Initializable {
    @FXML
    private Label taskTitleLbl;

    @FXML
    private Label taskDescriptionLbl;

    @FXML
    private JFXProgressBar taskProgressBar;

    @FXML
    private Label timeUsedLbl;

    @FXML
    Label timeLeftLbl;

    @FXML
    MenuItem editTaskBtn;

    @FXML
    MenuItem deleteTaskBtn;

    @FXML
    MenuItem markAsDoneBtn;

    @FXML
    HBox finishedTaskIconPane;

    @FXML
    AnchorPane resumeBtnPane;

    @FXML
    JFXButton resumeTaskBtn;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set fonts
        taskTitleLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 13 ) );
        taskDescriptionLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 11 ) );
        timeLeftLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 12 ) );
        timeUsedLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 12 ) );


    }

    void deleteTask() throws IOException, SQLException {
        TaskSession.deleteTask( Integer.parseInt( this.task.getTaskId() ) );
    }

    void markAsDone() throws IOException, SQLException {
        this.task.setCompletionTime( LocalTime.now() );
        this.task.setCompletionDate( LocalDate.now() );

        TaskSession.updateTask( this.task );
    }

    public void setTask(Task task) {
        this.task = task;
        fillDetails();
    }

    private void fillDetails() {
        taskTitleLbl.setText( task.getTitle() );
        taskDescriptionLbl.setText( task.getDescription() );
        double durationInSeconds = task.getDuration() * 60;

        double timeUsed = 0;
        try {
            timeUsed = TaskTimelineSession.getTaskTotalTimeConsumed( task ); // this is in seconds
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        double timeLeft = durationInSeconds - timeUsed;
        taskProgressBar.setProgress( timeUsed / durationInSeconds );
        timeLeftLbl.setText( (int) timeLeft + " seconds" );
        timeUsedLbl.setText( (int) timeUsed + " seconds" );

        if (this.task.getCompletionDate() == null) {
            resumeBtnPane.toFront();
        } else {
            finishedTaskIconPane.toFront();
        }
    }

}
