package com.o4codes.controllers;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectTitleLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 16)  );
        projectDescriptionLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 13 ) );
        projectCreationDateLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Light.ttf" ), 13)  );
        projectDueDateLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Light.ttf" ), 13)  );
        dueDateTitleLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 13)  );
        taskFractionProgressLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 13 ) );
        timeFractionProgressLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 13)  );
    }

    @FXML
    private void AddNewTaskEvent(ActionEvent event) throws IOException {
        BoxBlur boxBlur = new BoxBlur( 5, 5, 5 );
        stackPane.setEffect( boxBlur );
        MainApp.showTaskConfigView( project, null ).showAndWait();
        stackPane.setEffect( null );
    }

    @FXML
    private void EditProjectDetailsEvent(ActionEvent event) {

    }

    @FXML
    private void taskSearchEvent(ActionEvent event) {

    }

    public void setProject(Project project){
        this.project = project;
    }

    private void setTasksListInProject(){
        Platform.runLater( () -> {
            try {
                //get all tasks with the project
                for (Task task : TaskSession.getAllTasks(project.getId())){
                    String fxmlFile = "/fxml/taskListCard.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource(fxmlFile));
                    HBox card = loader.load();

                    // inject task details into view
                    TaskListCardController taskListController = loader.getController();

                    tasksContainerPane.getChildren().add( card );

                    //animate the card entry
                    SlideInLeft slideIn = new SlideInLeft(card);
                    slideIn.play();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } );
    }

}
