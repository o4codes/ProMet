package com.o4codes.controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.database.dbTransactions.TaskTimelineSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.models.Project;
import com.o4codes.models.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TaskDashboardController implements Initializable {
    @FXML
    private Label projectDescriptionLbl;

    @FXML
    private Label projectCreationDateLbl;

    @FXML
    private Label dueDateTitleLbl;

    @FXML
    private Label projectDueDateLbl;

    @FXML
    private JFXSpinner projectSpinnerLbl;

    @FXML
    private Label taskFractionProgressLbl;

    @FXML
    private Label timeFractionProgressLbl;

    @FXML
    private JFXButton editProjectDetailsBtn;

    @FXML
    private ComboBox<String> tasksSearchCombo;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton addNewTaskBtn;

    @FXML
    private VBox tasksContainerPane;

    @FXML
    private Label projectTitleLbl;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    private Project project;

    private Alerts alerts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set external fonts to labels
        projectTitleLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 16));
        projectDescriptionLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 13));
        projectCreationDateLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Light.ttf"), 13));
        projectDueDateLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Light.ttf"), 13));
        dueDateTitleLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 13));
        taskFractionProgressLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 13));
        timeFractionProgressLbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 13));

        //initialize alert classes
        alerts = new Alerts();

        //initialize labels with value from db
        try {
            projectTitleLbl.setText(project.getTitle());
            projectDescriptionLbl.setText(project.getDescription());
            projectCreationDateLbl.setText("Created on " + project.getBeginDate().toString());
            projectDueDateLbl.setText(project.getDueDate().toString());
            taskFractionProgressLbl.setText(TaskSession.finishedTasksCountInProject(project.getId()) + "/" + TaskSession.tasksCountInProject(project.getId()) + " tasks");
            timeFractionProgressLbl.setText(totalTimeUsed() + "/" + totaltimeToBeUsed() + " seconds");
            projectSpinnerLbl.setProgress((double) TaskSession.finishedTasksCountInProject(project.getId()) / (double) TaskSession.tasksCountInProject(project.getId()));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        //initialize tasks available and set to list
        setTasksListInProject();
    }

    @FXML
    private void AddNewTaskEvent(ActionEvent event) throws IOException {
        BoxBlur boxBlur = new BoxBlur(5, 5, 5);
        stackPane.setEffect(boxBlur);
        MainApp.showTaskConfigView(project, null).showAndWait();
        stackPane.setEffect(null);
        setTasksListInProject();
    }

    @FXML
    private void EditProjectDetailsEvent(ActionEvent event) {

    }

    @FXML
    private void taskSearchEvent(ActionEvent event) {

    }

    public void setProject(Project project) {
        this.project = project;
    }

    private void setTasksListInProject() {
        Platform.runLater(() -> {
            try {
                //get all tasks with the project
                for (Task task : TaskSession.getAllTasks(project.getId())) {
                    String fxmlFile = "/fxml/taskListCard.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource(fxmlFile));
                    HBox card = loader.load();

                    // inject task details into view
                    TaskListCardController taskListController = loader.getController();
                    taskListController.setTask(task);
                    tasksContainerPane.getChildren().add(card);

                    //add events to card buttons

                    //edit task event
                    taskListController.editTaskBtn.setOnAction(e -> {
                        try {
                            BoxBlur blur = new BoxBlur(5, 5, 5);
                            stackPane.setEffect(blur);
                            MainApp.showTaskConfigView(null, task).showAndWait();
                            stackPane.setEffect(null);
                            setTasksListInProject();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });

                    // mark task as done event
                    taskListController.markAsDoneBtn.setOnAction(e -> {
                        alerts.materialConfirmAlert(stackPane, vBox, "Confirm Task Completion", "Proceed to mark task as complete");
                        alerts.acceptBtn.setOnAction(ev -> {
                            try {
                                taskListController.markAsDone();
                                alerts.materialInfoAlert(stackPane, vBox, "Task Complete", "'" + task.getTitle() + "' has been marked as a complete task ");
                                taskListController.finishedTaskIconPane.toFront();
                                setTasksListInProject();
                            } catch (IOException | SQLException ex) {
                                ex.printStackTrace();
                            }

                        });
                    });

                    //delete task Event
                    taskListController.deleteTaskBtn.setOnAction(e -> {
                        alerts.materialConfirmAlert(stackPane, vBox, "Confirm Task Delete", "Proceed to delete this task");
                        alerts.acceptBtn.setOnAction(ev -> {
                            SlideOutLeft slide = new SlideOutLeft(card);
                            slide.play();
                            slide.getTimeline().setOnFinished(eve -> {
                                try {
                                    taskListController.deleteTask();
                                    setTasksListInProject();
                                } catch (IOException | SQLException ex) {
                                    ex.printStackTrace();
                                }
                            });
                        });
                    });

                    //Resume task
                    taskListController.resumeTaskBtn.setOnAction(e -> {
                        alerts.Notification("UNDEVELOPED", "Feature soon coming");
                    });

                    //animate the card entry
                    SlideInLeft slideIn = new SlideInLeft(card);
                    slideIn.play();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private int totaltimeToBeUsed() throws IOException, SQLException {
        int totalTimeInSeconds = 0;
        for (Task task : TaskSession.getAllTasks(project.getId())) {
            int timeInSeconds = task.getDuration() * 60;
            totalTimeInSeconds += timeInSeconds;
        }
        return totalTimeInSeconds;
    }

    private int totalTimeUsed() throws IOException, SQLException {
        int timeInSeconds = 0;
        for (Task task : TaskSession.getAllTasks(project.getId())) {
            timeInSeconds += TaskTimelineSession.getTaskTotalTimeConsumed(task);
        }
        return timeInSeconds;
    }

}
