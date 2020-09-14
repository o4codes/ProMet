package com.o4codes.controllers;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.ProjectSession;
import com.o4codes.database.dbTransactions.TaskSession;
import com.o4codes.models.Project;
import com.o4codes.models.Task;
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
import javafx.scene.text.Font;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // set views that will show up due to data retrieved
            if (ProjectSession.projectCount() > 0) {
                unemptyProjectPane.toFront();
                unemptyListProjectPane.toFront();
                showProjectList();
                showNextTasks();
            } else {
                emptyProjectPane.toFront();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        // set Fonts of some important Labels
        no_project_lbl.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 13));
        no_tasks_yet.setFont(Font.loadFont(MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 13));
    }

    @FXML
    private void CreateNewProjectEvent(ActionEvent event) throws IOException, SQLException {
        BoxBlur blur = new BoxBlur(5, 5, 5);
        rootStackPane.setEffect(blur);
        MainApp.showProjectConfigView().showAndWait();
        if (ProjectSession.projectCount() > 0) {
            unemptyProjectPane.toFront();
            unemptyListProjectPane.toFront();
            showProjectList();
        } else {
            emptyProjectPane.toFront();
        }
        rootStackPane.setEffect(null);
    }

    private void showProjectList() throws IOException, SQLException {

        Platform.runLater(() -> {
            try {
                projectListContainer.getChildren().clear();
                for (Project project : ProjectSession.getProjects()) {
                    //get list tile from FXML File
                    String fxmlFile = "/fxml/projectListCard.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource(fxmlFile));
                    HBox listTile = loader.load();

                    //populate the list tile with details from DB
                    ProjectListCardController projectListCard = loader.getController();
                    projectListCard.fillCardDetailsUp(project);
                    projectListContainer.getChildren().add(listTile);

                    //animate the list tile entry
                    SlideInLeft slideIn = new SlideInLeft(listTile);
                    slideIn.play();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void showNextTasks() {
        Platform.runLater(() -> {
            try {
                // get all projects
                for (Project project : ProjectSession.getProjects()) {

                    //get projects that are not complete
                    if (project.getCompletionDate() == null) {
                        unemptyTasksPane.toFront();

                        //sort the unfinished tasks according to deadline dates to get closest deadline at index 0
                        if (!TaskSession.getUnfinishedTasks(project.getId()).isEmpty()) {

                            Task task1 = TaskSession.getUnfinishedTasks(project.getId()).get(0);


                            // get the card from the view
                            String fxmlFile = "/fxml/deadlineCard.fxml";
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(MainApp.class.getResource(fxmlFile));
                            VBox card = loader.load();

                            // load the view with data
                            DeadlineCardController deadlineCardController = loader.getController();
                            deadlineCardController.fillCardDetails(task1);
                            deadlineProjectList.getChildren().add(card);

                            //animate the card entry
                            SlideInLeft slideIn = new SlideInLeft(card);
                            slideIn.play();

                        } else {
                            emptyTasksPane.toFront();
                        }
                    }
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }


}
