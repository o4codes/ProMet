package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.o4codes.database.dbTransactions.AppConfigSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.helpers.Utils;
import com.o4codes.models.AppConfiguration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppConfigController implements Initializable {
    @FXML
    private JFXButton maxProjects1;

    @FXML
    private JFXButton maxProjects2;

    @FXML
    private JFXButton maxProjects3;

    @FXML
    private JFXButton maxProjects4;

    @FXML
    private JFXButton maxProjects5;

    @FXML
    private JFXButton pomodoreTime20;

    @FXML
    private JFXButton pomodoreTime25;

    @FXML
    private JFXButton pomodoreTime30;

    @FXML
    private JFXButton pomodoreTime35;

    @FXML
    private JFXButton pomodoreTime40;

    @FXML
    private JFXButton pomShortBreak3;

    @FXML
    private JFXButton pomShortBreak5;

    @FXML
    private JFXButton pomShortBreak10;

    @FXML
    private JFXButton pomLongBreak10;

    @FXML
    private JFXButton pomLongBreak15;

    @FXML
    private JFXButton pomLongBreak20;

    @FXML
    private JFXButton updateSettingsBtn;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    private int maxProjects, pomodoreDuration, shortBreak, longDuration;

    private Alerts alerts = new Alerts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set events for maximum projects button selection
        maxProjects1.setOnAction( e -> setMaxProjectValue( Integer.parseInt( maxProjects1.getText() ) ) );
        maxProjects2.setOnAction( e -> setMaxProjectValue( Integer.parseInt( maxProjects2.getText() ) ) );
        maxProjects3.setOnAction( e -> setMaxProjectValue( Integer.parseInt( maxProjects3.getText() ) ) );
        maxProjects4.setOnAction( e -> setMaxProjectValue( Integer.parseInt( maxProjects4.getText() ) ) );
        maxProjects5.setOnAction( e -> setMaxProjectValue( Integer.parseInt( maxProjects5.getText() ) ) );

        //set events for pomodore tasks duration
        pomodoreTime20.setOnAction( e -> setTaskDurationValue( Integer.parseInt( pomodoreTime20.getText() ) ) );
        pomodoreTime25.setOnAction( e -> setTaskDurationValue( Integer.parseInt( pomodoreTime25.getText() ) ) );
        pomodoreTime30.setOnAction( e -> setTaskDurationValue( Integer.parseInt( pomodoreTime30.getText() ) ) );
        pomodoreTime35.setOnAction( e -> setTaskDurationValue( Integer.parseInt( pomodoreTime35.getText() ) ) );
        pomodoreTime40.setOnAction( e -> setTaskDurationValue( Integer.parseInt( pomodoreTime40.getText() ) )  );

        //set events for pomodore short break duration
        pomShortBreak3.setOnAction( e -> setPomodoreShortBreakValue( Integer.parseInt( pomShortBreak3.getText() ) ) );
        pomShortBreak5.setOnAction( e -> setPomodoreShortBreakValue( Integer.parseInt( pomShortBreak5.getText() ) ));
        pomShortBreak10.setOnAction( e -> setPomodoreShortBreakValue( Integer.parseInt( pomShortBreak10.getText() ) ) );

        //set events  for pomodore long break duration
        pomLongBreak10.setOnAction( e -> setPomodoreLongBreakValue( Integer.parseInt( pomLongBreak10.getText() )) );
        pomLongBreak15.setOnAction( e -> setPomodoreLongBreakValue( Integer.parseInt( pomLongBreak15.getText() )) );
        pomLongBreak20.setOnAction( e -> setPomodoreLongBreakValue( Integer.parseInt( pomLongBreak20.getText() )) );

        initDefaultSettings();
    }

    //initialize the default settings
    public void initDefaultSettings(){
        Platform.runLater( () -> {
            try {
                AppConfiguration appConfiguration = AppConfigSession.getAppConfig();
                maxProjects = appConfiguration.getMaxProjects();
                pomodoreDuration = appConfiguration.getTaskDuration();
                shortBreak = appConfiguration.getShortBreakDuration();
                longDuration = appConfiguration.getLongBreakDuration();

                // set UI to display default settings
                setMaxProjectValue( maxProjects );
                setTaskDurationValue( pomodoreDuration );
                setPomodoreShortBreakValue( shortBreak );
                setPomodoreLongBreakValue( longDuration );
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    private void HandleUpdateSettings(ActionEvent event) {
        AppConfiguration appConfiguration = new AppConfiguration( maxProjects,pomodoreDuration,shortBreak,longDuration, Utils.appTheme.get( 0 ));
        alerts.materialConfirmAlert( stackPane, vBox, "Settings Update", "By proceeding the app settings will be updated" );
        alerts.acceptBtn.setOnAction( e -> {
            try {
                AppConfigSession.updateAppConfigurations( appConfiguration );
                alerts.materialInfoAlert( stackPane,vBox, "Settings Updated","App Settings is successfully updated" );
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        } );
    }

    private void setMaxProjectValue(int maxProjectsValue) {
        maxProjects = maxProjectsValue;
        switch (maxProjectsValue) {
            case 1:

                setSelectedButtonColor( maxProjects1 );
                setUnSelectedButtonColor( maxProjects2 );
                setUnSelectedButtonColor( maxProjects3 );
                setUnSelectedButtonColor( maxProjects4 );
                setUnSelectedButtonColor( maxProjects5 );
                break;
            case 2:
                setSelectedButtonColor( maxProjects2 );
                setUnSelectedButtonColor( maxProjects1 );
                setUnSelectedButtonColor( maxProjects3 );
                setUnSelectedButtonColor( maxProjects4 );
                setUnSelectedButtonColor( maxProjects5 );
                break;
            case 3:
                setSelectedButtonColor( maxProjects3 );
                setUnSelectedButtonColor( maxProjects1 );
                setUnSelectedButtonColor( maxProjects2 );
                setUnSelectedButtonColor( maxProjects4 );
                setUnSelectedButtonColor( maxProjects5 );
                break;
            case 4:
                setSelectedButtonColor( maxProjects4 );
                setUnSelectedButtonColor( maxProjects1 );
                setUnSelectedButtonColor( maxProjects3 );
                setUnSelectedButtonColor( maxProjects2 );
                setUnSelectedButtonColor( maxProjects5 );
                break;
            case 5:
                setSelectedButtonColor( maxProjects5 );
                setUnSelectedButtonColor( maxProjects1 );
                setUnSelectedButtonColor( maxProjects3 );
                setUnSelectedButtonColor( maxProjects4 );
                setUnSelectedButtonColor( maxProjects2 );
                break;
        }
    }

    private void setTaskDurationValue(int taskDuration) {
        pomodoreDuration = taskDuration;
        switch (taskDuration) {
            case 20:
                setSelectedButtonColor( pomodoreTime20 );
                setUnSelectedButtonColor( pomodoreTime25 );
                setUnSelectedButtonColor( pomodoreTime30);
                setUnSelectedButtonColor( pomodoreTime35 );
                setUnSelectedButtonColor( pomodoreTime40 );
                break;
            case 25:
                setSelectedButtonColor( pomodoreTime25 );
                setUnSelectedButtonColor( pomodoreTime20 );
                setUnSelectedButtonColor( pomodoreTime30);
                setUnSelectedButtonColor( pomodoreTime35 );
                setUnSelectedButtonColor( pomodoreTime40 );
                break;
            case 30:
                setSelectedButtonColor( pomodoreTime30 );
                setUnSelectedButtonColor( pomodoreTime20 );
                setUnSelectedButtonColor( pomodoreTime25);
                setUnSelectedButtonColor( pomodoreTime35 );
                setUnSelectedButtonColor( pomodoreTime40 );
                break;
            case 35:
                setSelectedButtonColor( pomodoreTime35 );
                setUnSelectedButtonColor( pomodoreTime20 );
                setUnSelectedButtonColor( pomodoreTime30);
                setUnSelectedButtonColor( pomodoreTime25 );
                setUnSelectedButtonColor( pomodoreTime40 );
                break;
            case 40:
                setSelectedButtonColor( pomodoreTime40 );
                setUnSelectedButtonColor( pomodoreTime25 );
                setUnSelectedButtonColor( pomodoreTime30);
                setUnSelectedButtonColor( pomodoreTime35 );
                setUnSelectedButtonColor( pomodoreTime20 );
                break;
        }
    }

    private void setPomodoreShortBreakValue (int shortBreakValue){
        shortBreak = shortBreakValue;
        switch (shortBreakValue) {
            case 3:
                setSelectedButtonColor( pomShortBreak3 );
                setUnSelectedButtonColor( pomShortBreak5 );
                setUnSelectedButtonColor( pomShortBreak10 );
                break;
            case 5:
                setSelectedButtonColor( pomShortBreak5 );
                setUnSelectedButtonColor( pomShortBreak3 );
                setUnSelectedButtonColor( pomShortBreak10 );
                break;
            case 10:
                setSelectedButtonColor( pomShortBreak10 );
                setUnSelectedButtonColor( pomShortBreak5 );
                setUnSelectedButtonColor( pomShortBreak3 );
                break;

        }
    }

    private void setPomodoreLongBreakValue (int longBreakValue){
        longDuration = longBreakValue;
        switch (longBreakValue) {
            case 10:
                setSelectedButtonColor( pomLongBreak10 );
                setUnSelectedButtonColor( pomLongBreak15 );
                setUnSelectedButtonColor( pomLongBreak20 );
                break;
            case 15:
                setSelectedButtonColor( pomLongBreak15 );
                setUnSelectedButtonColor( pomLongBreak10 );
                setUnSelectedButtonColor( pomLongBreak20 );
                break;
            case 20:
                setSelectedButtonColor( pomLongBreak20 );
                setUnSelectedButtonColor( pomLongBreak15 );
                setUnSelectedButtonColor( pomLongBreak10 );
                break;
        }
    }

    private void setSelectedButtonColor(JFXButton button) {
        button.setStyle( "-fx-background-color: red; -fx-text-fill: white;" );
    }

    private void setUnSelectedButtonColor(JFXButton button) {
        button.setStyle( "-fx-background-color: white;" );
    }

}
