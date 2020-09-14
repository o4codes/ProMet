package com.o4codes.controllers;

import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.helpers.Utils;
import com.o4codes.models.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectListCardController implements Initializable {
    @FXML
    private Label projectListName;

    @FXML
    private Label projectListDescription;

    @FXML
    private Label projectListStartDate;

    @FXML
    private Label projectListTaskNumber;

    @FXML
    private Label projectListEndDate;

    @FXML
    private Label projectListStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectListName.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 12 ));
    }

    void fillCardDetailsUp(Project project) throws IOException, SQLException {
        projectListName.setText( project.getTitle() );
        projectListDescription.setText( project.getDescription() );
        projectListStartDate.setText( project.getBeginDate().toString() );
        projectListEndDate.setText( project.getDueDate().toString() );
        projectListStatus.setText( project.getCompletionDate() == null ? Utils.PROJECT_STATUS.get( 0 ) : Utils.PROJECT_STATUS.get( 1 ) );
        projectListTaskNumber.setText( getTasksRatio( project ) );
    }

    private String getTasksRatio(Project project) throws IOException, SQLException {
        return TaskSession.finishedTasksCountInProject(project.getId())+"/"+TaskSession.tasksCountInProject( project.getId()) +" Tasks";
    }
}
