package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.ProjectSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.helpers.Validators;
import com.o4codes.models.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProjectConfigController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label paneTitleLbl;

    @FXML
    private JFXButton closePaneBtn;

    @FXML
    private JFXColorPicker colorThemeField;

    @FXML
    private TextField projectTitleField;

    @FXML
    private TextArea projectDescriptionField;

    @FXML
    private JFXDatePicker dueDateField;

    @FXML
    private JFXButton createProjectBtn;

    @FXML
    private VBox vBox;

    private Alerts alerts;

    private Validators validate;

    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //event to close pane when the close button is pressed
        closePaneBtn.setOnAction( e -> ((JFXButton) e.getSource()).getScene().getWindow().hide() );

        //initialize helper classes
        alerts = new Alerts();
        validate = new Validators();

        //set Fonts
        paneTitleLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 15 )  );

    }

    @FXML
    private void CreateProjectEvent(ActionEvent event) throws IOException, SQLException {
        String colorTheme = String.valueOf( colorThemeField.getValue() );
        String projectTitle = projectTitleField.getText();
        String projectDescription = projectDescriptionField.getText();
        LocalDate dueDate = dueDateField.getValue();

        if (projectTitle.isEmpty() || projectDescription.isEmpty() || dueDate == null) {
            alerts.Notification( "EMPTY FIELD(S)", "Ensure all fields are filled up" );
        } else {
            int MAX_DESCRIPTION_WORDS = 20;
            if (validate.wordCount( projectDescription ) > MAX_DESCRIPTION_WORDS) {
                alerts.Notification( "WORD_LIMIT EXCEED", "Project description has exceeded word limit" );
            } else {
                if (createProjectBtn.getText().equals( "Update Project" )){
                    this.project.setColorTheme( colorTheme );
                    this.project.setDueDate( dueDate );
                    this.project.setDescription( projectDescription );
                    this.project.setTitle( projectTitle );

                    ProjectSession.updateProject( project );
                    alerts.materialInfoAlert( stackPane,vBox,"Project Updated","'"+this.project.getTitle()+"Project Updated successfully" );
                }
                else {
                    Project project = new Project( colorTheme, projectTitle, projectDescription, LocalDate.now(), dueDate );
                    if (ProjectSession.insertProject(project).getId().equals(project.getId())) {
                        alerts.materialInfoAlert( stackPane,vBox,"Project Created","New Project is successfully created" );
                    }
                    else {
                        alerts.materialInfoAlert( stackPane,vBox,"Project Creation Failed","Check Project details, project creation failed" );
                    }
                }

            }
        }
    }

    public void setProject(Project project){
        this.project = project;
    };

    public void setFieldDetails(){
        //set field details on update state
        if (this.project != null){
            projectTitleField.setText( project.getTitle() );
            projectDescriptionField.setText( project.getDescription() );
            colorThemeField.setValue( Color.valueOf(project.getColorTheme()) );
            dueDateField.setValue( project.getDueDate() );
            createProjectBtn.setText( "Update Project" );
        }

    }

}
