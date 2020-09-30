package com.o4codes.controllers;

import com.o4codes.MainApp;
import com.o4codes.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.Duration;
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

    public void setTask(Task task) {
        this.task = task;
        taskTitleLabel.setText( task.getTitle() );

        //convert duration which is minutes to hours
        taskDurationLbl.setText( getDurationInHourMinutes( task.getDuration() ) );
    }

    private String getDurationInHourMinutes(int duration) {
        String durationHourMinutes = "";
        //duration is in minutes
        int hour = (int) Duration.ofMinutes( duration ).toHours();
        //check if there are remaining minutes
        if (Duration.ofHours( hour ).toMinutes() == duration) {
            durationHourMinutes = hour + " hr";
        } else {
            int remaining_minutes = (int) (duration - Duration.ofHours( hour ).toMinutes());
            durationHourMinutes = hour + " hr " + remaining_minutes + " mins";
        }
        return durationHourMinutes;
    }

}
