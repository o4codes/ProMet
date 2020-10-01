package com.o4codes.controllers;

import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.AppConfigSession;
import com.o4codes.models.AppConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BreaksCardControler implements Initializable {

    @FXML
    private Label taskDurationLbl;

    @FXML
    private Label taskTitleLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskTitleLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Bold.ttf" ), 12 ) );
        taskDurationLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream( "/fonts/Lato/Lato-Regular.ttf" ), 11 ) );
    }

    public void setLabels(boolean isBreakShort) throws IOException, SQLException {
        AppConfiguration appConfiguration = AppConfigSession.getAppConfig();
        if (isBreakShort){
            taskDurationLbl.setText( appConfiguration.getShortBreakDuration()+" mins" );
            taskTitleLabel.setText( "Short Break" );
        }
        else{
            taskDurationLbl.setText( appConfiguration.getLongBreakDuration()+" mins" );
            taskTitleLabel.setText( "Long Break" );
        }
    }
}
