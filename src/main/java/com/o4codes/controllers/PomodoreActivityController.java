package com.o4codes.controllers;

import animatefx.animation.FadeInLeft;
import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.models.Project;
import com.o4codes.models.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PomodoreActivityController implements Initializable {
    @FXML
    private HBox titleBar;

    @FXML
    private JFXButton minimizeBtn;

    @FXML
    private JFXButton appCloseBtn;

    @FXML
    private VBox projectTaskList;

    @FXML
    private Label taskTitleLbl;

    @FXML
    private Label timerLbl;

    @FXML
    private JFXButton tenMinsAdditionalBtn;

    @FXML
    private JFXButton nextTaskBtn;

    @FXML
    private JFXButton pauseTaskExecBtn;

    @FXML
    private VBox pomodoreCycleList;

    @FXML
    private Label taskDescriptionLbl;

    private Project project;

    private double xOffset = 0, yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //handle window title bar events
        //close window events
        appCloseBtn.setOnAction( e -> ((JFXButton) e.getSource()).getScene().getWindow().hide() );

        //minimize window event
        minimizeBtn.setOnAction( e -> {
            Stage stage = (Stage) minimizeBtn.getScene().getWindow();
            stage.setIconified( true );
        } );

        //event to move window from one source to another
        titleBar.setOnMousePressed( e -> {
            xOffset = e.getSceneX();
            yOffset = e.getScreenY();
        } );

        titleBar.setOnMouseDragged( e -> {
            Stage stage = (Stage) minimizeBtn.getScene().getWindow();
            stage.setX( e.getScreenX() - xOffset );
            stage.setY( e.getScreenY() - yOffset );
        } );
    }

    @FXML
    void NextTaskEventBtn(ActionEvent event) {

    }

    @FXML
    void PauseTaskExecEvent(ActionEvent event) {

    }

    @FXML
    void addTenMinsToTaskExec(ActionEvent event) {

    }

    private void fillUpProjectTasks(Project project) {
        Platform.runLater( () -> {
            try {
                for (Task task : TaskSession.getUnfinishedTasks( project.getId() )) {
                    String fxmlFile = "/fxml/pomdoreActivityTasks.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation( MainApp.class.getResource( fxmlFile ) );
                    HBox card = loader.load();

                    // inject task details into view
                    PomodoreActivityTasksController pomodoreTasks = loader.getController();
                    pomodoreTasks.setTask( task );
                    projectTaskList.getChildren().add( card );

                    //animate the card entry
                    FadeInLeft slideIn = new FadeInLeft( card );
                    slideIn.play();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } );
    }

    public void setProject(Project project) {
        this.project = project;
        fillUpProjectTasks( project );
    }

}
