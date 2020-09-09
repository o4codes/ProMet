package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.AppConfigSession;
import com.o4codes.models.AppConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppSettingsController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label deviceNameLabel;

    @FXML
    private JFXButton editProfileButton;

    @FXML
    private JFXButton changePasscodeEvent;

    @FXML
    private JFXButton editPomodoreSettings;

    @FXML
    private Label taskIntervalValueLbl;

    @FXML
    private Label taskIntervalLbl;

    @FXML
    private Label shortRestIntervalValueLbl;

    @FXML
    private Label shortRestIntervalLbl;

    @FXML
    private Label longRestIntervalValueLbl;

    @FXML
    private Label longRestIntervalLbl;

    @FXML
    private Label cocurentsProjectsValueLbl;

    @FXML
    private Label cocurentsProjectsLbl;

    @FXML
    private JFXButton preferencesBtn;

    @FXML
    private Rectangle circularImageView;

    private AppConfiguration appConfiguration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // change fonts of some Labels
        deviceNameLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 24 ));
        userNameLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 24 ));
        taskIntervalLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
        shortRestIntervalLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
        longRestIntervalLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));
        cocurentsProjectsLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 16 ));

        //set saved down values from database to fields
        try {
            getSavedPomodoreConfigurations();
            getSavedUserConfiguration();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void EditPomodoreEvent(ActionEvent event) throws IOException, SQLException {
        BoxBlur blur = new BoxBlur( 5, 5, 5 );
        stackPane.getParent().getParent().setEffect( blur );
        MainApp.showPomodoreConfigView().showAndWait();
        stackPane.getParent().getParent().setEffect( null );

        //reload UI with saved values from database
        getSavedPomodoreConfigurations();
    }

    @FXML
    private void EditProfileEvent(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur( 5, 5, 5 );
        stackPane.getParent().getParent().setEffect( blur );
        MainApp.showProfileUpdateView().showAndWait();
        stackPane.getParent().getParent().setEffect( null );

        //reload UI with saved values from database
        getSavedUserConfiguration();
    }

    @FXML
    private void PasscodeChangeEvent(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur( 5, 5, 5 );
        stackPane.getParent().getParent().setEffect( blur );
        MainApp.showPasscodeConfigView().showAndWait();
        stackPane.getParent().getParent().setEffect( null );
    }

    @FXML
    private void PreferencesEditEvent(ActionEvent event) {

    }

    //set saved down Pomodore values from database to UI
    private void getSavedPomodoreConfigurations() throws IOException, SQLException {
        appConfiguration = AppConfigSession.getAppConfig();
        taskIntervalValueLbl.setText(appConfiguration.getTaskDuration() +"min");
        shortRestIntervalValueLbl.setText(appConfiguration.getShortBreakDuration() +"min");
        longRestIntervalValueLbl.setText(appConfiguration.getLongBreakDuration() +"min");
        cocurentsProjectsValueLbl.setText(String.valueOf(appConfiguration.getMaxProjects()));
    }

    //set saved down values user profile values from database to UI
    private void getSavedUserConfiguration(){
        deviceNameLabel.setText(WelcomeController.user.getDeviceName());
        userNameLabel.setText(WelcomeController.user.getName());
        if (WelcomeController.user.getUserImage() == null) {
            Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
            circularImageView.setFill( new ImagePattern( image ) );
        } else {
            circularImageView.setFill( new ImagePattern( WelcomeController.user.getUserImage() ) );
        }
    }

}
