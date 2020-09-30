package com.o4codes.controllers;

import com.jfoenix.controls.*;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.helpers.Validators;
import com.o4codes.models.Project;
import com.o4codes.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class TaskConfigController implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label formTitleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private TextField taskTitleField;

    @FXML
    private JFXTextArea taskDescriptionField;

    @FXML
    private Spinner<Integer> durationHourField;

    @FXML
    private Spinner<Integer> durationMinField;

    @FXML
    private JFXDatePicker deadlineDateField;

    @FXML
    private JFXTimePicker deadlineTimeField;

    @FXML
    private JFXCheckBox mileStoneTaskToggle;

    @FXML
    private JFXButton createEditTaskBtn;

    private Project project;

    private Alerts alerts;

    private Validators validate;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validate = new Validators();
        alerts = new Alerts();

        // set fields to insert or update state if task is null;


        //set spinner min and max values
        durationHourField.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, 100 ) );
        durationMinField.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 1, 60 ) );

        //event to close the page
        closeBtn.setOnAction( e -> {
            ((JFXButton) e.getSource()).getScene().getWindow().hide();
        } );

        //set Font family and size to the Label
        formTitleLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 15 ) );
    }

    @FXML
    private void CreateEditTaskEvent(ActionEvent event) throws IOException, SQLException {
        // method to create task or edit task depending on the text on the button
        String taskTitle = taskTitleField.getText();
        String taskDescription = taskDescriptionField.getText();

        // check if required fields are empty
        if (taskTitle.isEmpty() || taskDescription.isEmpty()
                || deadlineDateField.getValue() == null || deadlineTimeField.getValue() == null) {
            alerts.Notification( "EMPTY FIELD(S)", "Ensure important fields are not empty" );
        } else {
            LocalDate deadlineDate = deadlineDateField.getValue();
            LocalTime deadlineTime = deadlineTimeField.getValue();
            boolean isMileStone = mileStoneTaskToggle.isSelected();
            int durationHour = durationHourField.getValue();
            int durationMinute = durationMinField.getValue();
//            int totalDurationInMinutes = (60 * durationHour) + durationMinute;
            int totalDurationInMinutes = (int) Duration.hours(durationHour).toMinutes() + durationMinute;
            LocalDateTime maxTaskDurationDateTime = LocalDateTime.now().plusHours( durationHour ).plusMinutes( durationMinute );
            LocalDateTime taskDeadlineDateTime = LocalDateTime.of( deadlineDate, deadlineTime );

            // check if task deadline date set will occur after project deadline date
            if (deadlineDate.isAfter( project.getDueDate() )) {
                alerts.Notification( "Project Deadline Date Cannot Be Exceeded", "Kindly adjust your task deadline date or extend the project deadline date" );
            }
            // check if task deadline date set is before the present date
            else if (taskDeadlineDateTime.isBefore( LocalDateTime.now() )) {
                alerts.Notification( "INVALID DATE", "Ensure a date is set between now and project due date" );
            } else {
                // check if the task duration set will surpass the task deadline date
                if (maxTaskDurationDateTime.isAfter( taskDeadlineDateTime )) {
                    alerts.Notification( "Task Due Date Cannot Be Exceeded", "Ensure the duration of task set does not exceed task deadline date and time " );
                } else {
                    // task description must not exceed 20 words
                    if (validate.wordCount( taskDescription ) > 20) {
                        alerts.Notification( "Word Limit Exceeded", "Task Description should have a maximum of 20 words" );
                    } else {
                        // insert task
                        if (createEditTaskBtn.getText().equals( "Create Task" )) {
                            Task task = new Task( project.getId(), taskTitle, taskDescription, totalDurationInMinutes, LocalDate.now(), LocalTime.now(), deadlineDate, deadlineTime, isMileStone );
                            if (TaskSession.insertTask( task )) {
                                alerts.materialInfoAlert( stackPane, root, "Task Creation Successful", "'" + task.getTitle() + "' has been added to the list of tasks" );
                            } else {
                                alerts.materialInfoAlert( stackPane, root, "Task Creation Failed", "Task not added to list of tasks for project" );
                            }
                        } else {
                            // update task
                            this.task.setTitle( taskTitle );
                            this.task.setDescription( taskDescription );
                            this.task.setMileStone( isMileStone );
                            this.task.setDuration( totalDurationInMinutes );
                            this.task.setDeadlineDate( deadlineDate );
                            this.task.setDeadlineTime( deadlineTime );
                            TaskSession.updateTask( this.task );
                            alerts.materialInfoAlert( stackPane, root, "Task Updated Successful", "'" + task.getTitle() + "' Task details has been updated successfully" );
                        }
                    }
                }
            }

        }

    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTask(Task task) {
        this.task = task;
        fillUpEditDetails();
    }

    private void fillUpEditDetails() {
        if (task != null) {
            createEditTaskBtn.setText( "Update Task" );
            taskTitleField.setText( task.getTitle() );
            taskDescriptionField.setText( task.getDescription() );
            mileStoneTaskToggle.setSelected( task.isMileStone() );
            deadlineDateField.setValue( task.getDeadlineDate() );
            deadlineTimeField.setValue( task.getDeadlineTime() );
            durationHourField.getValueFactory().setValue( task.getDuration() / 60 );
            durationMinField.getValueFactory().setValue( task.getDuration() % 60 );
        }
    }


}
