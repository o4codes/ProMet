package com.o4codes.controllers;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.ProjectSession;
import com.o4codes.models.Project;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectDashboardController implements Initializable {
    @FXML
    private VBox unemptyProjectPane;

    @FXML
    private HBox deadlineProjectList;

    @FXML
    private JFXComboBox<?> statusesComboBox;

    @FXML
    private JFXComboBox<?> allTimeComboBox;

    @FXML
    private JFXButton createNewProjectBtn;

    @FXML
    private ScrollPane unemptyListProjectPane;

    @FXML
    private VBox projectListContainer;

    @FXML
    private VBox emptyListProjectPane;

    @FXML
    private Label no_project_lbl;

    @FXML
    private VBox emptyProjectPane;

    @FXML
    private AnchorPane root;

    @FXML
    private StackPane rootStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (ProjectSession.projectCount() > 0) {
                unemptyProjectPane.toFront();
                unemptyListProjectPane.toFront();
                showProjectList();
            }
            else {
                emptyProjectPane.toFront();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CreateNewProjectEvent(ActionEvent event) throws IOException, SQLException {
        BoxBlur blur = new BoxBlur( 5, 5, 5 );
        rootStackPane.setEffect( blur );
        MainApp.showProjectConfigView().showAndWait();
        if (ProjectSession.projectCount() > 0) {
            unemptyProjectPane.toFront();
            unemptyListProjectPane.toFront();
            showProjectList();
        }
        else {
            emptyProjectPane.toFront();
        }
        rootStackPane.setEffect( null );
    }

    private void showProjectList() throws IOException, SQLException {

        Platform.runLater( () -> {
            try {
                projectListContainer.getChildren().clear();
                for (Project project : ProjectSession.getProjects()) {
                    //get list tile from FXML File
                    String fxmlFile = "/fxml/projectListCard.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation( MainApp.class.getResource( fxmlFile ) );
                    HBox listTile = loader.load();

                    //populate the list tile with details from DB
                    ProjectListCardController projectListCard = loader.getController();
                    projectListCard.fillCardDetailsUp( project );
                    projectListContainer.getChildren().add( listTile );

                    //animate the list tile entry
                    SlideInLeft slideIn = new SlideInLeft( listTile );
                    slideIn.play();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } );
    }


}
