package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasscodeConfigController implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private PasswordField presentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private JFXButton updatePasswordBtn;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    private Alerts alerts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //close button event
        closeBtn.setOnAction( e -> {
            ((JFXButton)e.getSource()).getScene().getWindow().hide();
        } );

        alerts = new Alerts();

        //set Font to title Label
        titleLabel.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 18 ));
    }

    @FXML
    private void UpdatePasswordEvent(ActionEvent event) throws IOException, SQLException {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();
        String oldPassword = presentPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty() || oldPassword.isEmpty()) {
            alerts.Notification( "Empty Field(s)", "Ensure all fields are filled up" );
        } else {

                if (UserSession.comparePassword( oldPassword ) ) {
                    if (newPassword.equals( confirmPassword )) {
                        WelcomeController.user.setDevicePassword( newPassword );
                        UserSession.updateUserDetails( WelcomeController.user, WelcomeController.user.getName() );
                        alerts.materialInfoAlert( stackPane, vBox, "Password Update Successful", "Password has been successfully Updated." );
                    } else {
                        alerts.Notification( "Mismatching Password", "Passwords entered do not match, \n Check Passwords " );
                    }
                } else {
                    alerts.Notification( "Wrong Present Password", "Present password input is wrong" );
                }

        }
    }


}
