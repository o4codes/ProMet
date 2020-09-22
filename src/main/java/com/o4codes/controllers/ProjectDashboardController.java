package com.o4codes.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOut;
import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.ProjectSession;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.models.Project;
import com.o4codes.models.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
    private JFXComboBox<String> statusesComboBox;

    @FXML
    private JFXComboBox<String> allTimeComboBox;

    @FXML
    private JFXButton createNewProjectBtn;

    @FXML
    private ScrollPane unemptyListProjectPane;

    @FXML
    private ScrollPane unemptyTasksPane;

    @FXML
    private VBox projectListContainer;

    @FXML
    private VBox emptyListProjectPane;

    @FXML
    private Label no_project_lbl;

    @FXML
    private Label no_tasks_yet;

    @FXML
    private VBox emptyProjectPane;

    @FXML
    private VBox emptyTasksPane;

    @FXML
    private AnchorPane root;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane contentPane;

    private Alerts alerts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // set views that will show up due to data retrieved
            if (ProjectSession.projectCount() > 0) {
                unemptyProjectPane.toFront();
                unemptyListProjectPane.toFront();
                emptyProjectPane.setOpacity( 0 );
                showProjectList();
                showNextTasks();

            } else {
                emptyProjectPane.toFront();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        // set Fonts of some important Labels
        no_project_lbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 13 ) );
        no_tasks_yet.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 13 ) );

        //initialize alerts class
        alerts = new Alerts();


    }

    @FXML
    private void CreateNewProjectEvent(ActionEvent event) throws IOException, SQLException {
        BoxBlur blur = new BoxBlur( 5, 5, 5 );
        rootStackPane.setEffect( blur );
        MainApp.showProjectConfigView( null ).showAndWait();
        if (ProjectSession.projectCount() > 0) {
            unemptyProjectPane.toFront();
            unemptyListProjectPane.toFront();
            showProjectList();
        } else {
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

                    //set event when a list tile is being clicked
                    listTile.setOnMouseClicked( e -> {
                        Platform.runLater( () -> {
                            try {
                                if (e.getClickCount() == 2) {
                                    createPage( project );
                                    FadeOut fadeOut = new FadeOut( unemptyProjectPane );
                                    fadeOut.setSpeed( 1 );
                                    fadeOut.play();
                                    fadeOut.getTimeline().setOnFinished( ev -> {
                                        contentPane.toFront();
                                        FadeIn fadeIn = new FadeIn( contentPane );
                                        fadeIn.play();
                                    } );
                                }
                            } catch (IOException | SQLException ex) {
                                ex.printStackTrace();
                            }
                        } );
                    } );
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } );
    }

    private void showNextTasks() {
        Platform.runLater( () -> {
            try {
                // get all projects
                for (Project project : ProjectSession.getProjects()) {
                    //get projects that are not complete
                    if (project.getCompletionDate() == null) {
                        unemptyTasksPane.toFront();

                        //sort the unfinished tasks according to deadline dates to get closest deadline at index 0
                        if (!TaskSession.getUnfinishedTasks( project.getId() ).isEmpty()) {

                            Task task = TaskSession.getUnfinishedTasks( project.getId() ).get( 0 );
                            System.out.println( task.getTitle() );

                            // get the card from the view
                            String fxmlFile = "/fxml/deadlineCard.fxml";
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation( MainApp.class.getResource( fxmlFile ) );
                            VBox card = loader.load();

                            // load the view with data
                            DeadlineCardController deadlineCardController = loader.getController();
                            deadlineCardController.setTask( task );
                            deadlineProjectList.getChildren().add( card );

                            //animate the card entry
                            SlideInLeft slideIn = new SlideInLeft( card );
                            slideIn.play();

                        } else {
                            emptyTasksPane.toFront();
                        }
                    }
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } );
    }

    private void setNode(Node node) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add( node );
        contentPane.setEffect( null );
        node.setLayoutX( 0 );
        node.setLayoutY( 0 );
        node.setOpacity( 0 );
        FadeInLeft fade = new FadeInLeft( node );
        fade.setDelay( Duration.seconds( 1 ) );
        fade.play();
    }

    private void createPage(Project project) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( "/fxml/taskDashboard.fxml" ) );
        AnchorPane home = loader.load();
        TaskDashboardController taskDashboardController = loader.getController();
        taskDashboardController.setProject( project );
        taskDashboardController.setTasksListInProject();


        setNode( home );
        AnchorPane.setTopAnchor( home, 0.0 );
        AnchorPane.setBottomAnchor( home, 0.0 );
        AnchorPane.setLeftAnchor( home, 0.0 );
        AnchorPane.setRightAnchor( home, 0.0 );
    }


}
