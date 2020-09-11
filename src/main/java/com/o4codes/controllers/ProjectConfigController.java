package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
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

    private final int MAX_DESCRIPION_WORDS = 20;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //event to close pane when the close button is pressed
        closePaneBtn.setOnAction( e -> ((JFXButton) e.getSource()).getScene().getWindow().hide() );

        //initialize helper classes
        alerts = new Alerts();
        validate = new Validators();
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
            if (validate.wordCount( projectDescription ) > MAX_DESCRIPION_WORDS) {
                alerts.Notification( "WORD_LIMIT EXCEED", "Project description has exceeded word limit" );
            } else {
                Project project = new Project( colorTheme, projectTitle, projectDescription, LocalDate.now(), dueDate );
                if (ProjectSession.insertProject( project )) {
                   alerts.materialInfoAlert( stackPane,vBox,"Project Created","New Project is successfully created" );
                }
                else {
                    alerts.materialInfoAlert( stackPane,vBox,"Project Creation Failed","Check Project details, project creation failed" );
                }

            }
        }
    }


}
