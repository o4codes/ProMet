package com.o4codes.controllers;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOutLeft;
import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.models.Project;
import com.o4codes.models.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane stackPane;

    private Project project;

    private double xOffset = 0, yOffset = 0;

    private ObservableList<Task> taskCollection = FXCollections.observableArrayList();

    private Alerts alerts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize alerts class
        alerts = new Alerts();
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

                    //add events to the card
                    // when the pane is double clicked it is added to the pomodore task cycle list
                    card.setOnMouseClicked( e -> {
                        try {
                            if (e.getClickCount() == 2) { // when pane is double clicked the event is triggered
                                if (taskCollection.size() < 4) {
                                    String fxmlFile2 = "/fxml/pomodoreActivityCycle.fxml";
                                    FXMLLoader loader2 = new FXMLLoader();
                                    loader2.setLocation( MainApp.class.getResource( fxmlFile2 ) );
                                    HBox card2 = loader2.load();

                                    // inject the newly added task into the view
                                    PomodoreActivityCycleController pomodoreTaskCycle = loader2.getController();
                                    pomodoreTaskCycle.setTask( task );
                                    pomodoreCycleList.getChildren().add( card2 );

                                    //animate the card entry
                                    FadeInLeft slide = new FadeInLeft( card2 );
                                    slide.play();

                                    taskCollection.add( task ); // add tasks to list of tasks to be worked on
                                    int taskCardIndex = pomodoreCycleList.getChildren().indexOf( card2 );
                                    // add breaks card
                                    if (taskCollection.size() <= 3) {
                                        pomodoreCycleList.getChildren().add( breakCards( true ) );
                                    } else {
                                        pomodoreCycleList.getChildren().add( breakCards( false ) );
                                    }

                                    // add events to the card2 to remove the task from the list when double clicked
                                    pomodoreTaskCycle.removeTaskButton.setOnAction( ev -> {

                                        FadeOutLeft fadeOutLeft = new FadeOutLeft( card2 );
                                        fadeOutLeft.play();
                                        fadeOutLeft.getTimeline().setOnFinished( eve -> {
                                            try {
                                                if (pomodoreCycleList.getChildren().size() == 8) {
                                                    if (taskCardIndex == 6) {
                                                        pomodoreCycleList.getChildren().remove( taskCardIndex + 1 );
                                                    } else {
                                                        int lastIndex = pomodoreCycleList.getChildren().size() - 1;
                                                        pomodoreCycleList.getChildren().remove( lastIndex );
                                                        pomodoreCycleList.getChildren().remove( taskCardIndex + 1 );
                                                        pomodoreCycleList.getChildren().add( breakCards( true ) );
                                                    }

                                                } else if (pomodoreCycleList.getChildren().size() == 1) {
                                                    pomodoreCycleList.getChildren().remove( taskCardIndex + 1 );
                                                } else {
                                                    int indexOfCard = pomodoreCycleList.getChildren().indexOf( card2 );
                                                    pomodoreCycleList.getChildren().remove( indexOfCard + 1 );
                                                }
                                                pomodoreCycleList.getChildren().remove( card2 );
                                                taskCollection.remove( task );
                                            } catch (IOException | SQLException ex) {
                                                ex.printStackTrace();
                                            }
                                        } );
                                    } );
                                } else {
                                    alerts.materialInfoAlert( stackPane, borderPane, "Tasks Limit", "Only a total of four(4) tasks is allowed to be worked on" );
                                }
                            }

                        } catch (IOException | SQLException ex) {
                            ex.printStackTrace();
                        }

                    } );
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

    public HBox breakCards(boolean isBreakShort) throws IOException, SQLException {
        String fxmlFile = "/fxml/breaksCard.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        HBox card = loader.load();

        BreaksCardControler breaksCard = loader.getController();
        breaksCard.setLabels( isBreakShort );

        //animate the card entry
        FadeInLeft slide = new FadeInLeft( card );
        slide.play();

        return card;
    }

}
