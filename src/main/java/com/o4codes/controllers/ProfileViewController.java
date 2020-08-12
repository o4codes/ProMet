package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.helpers.Validators;
import com.o4codes.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable {
    @FXML
    private Label appTitleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private Circle circluarImageView;

    @FXML
    private JFXButton uploadDeviceImageBtn;

    @FXML
    private JFXButton defaultImageBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField mobileNumberField;

    @FXML
    private TextField deviceNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private JFXButton updateProfileBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane stackPane;

    private Alerts alerts = new Alerts();

    Validators validators = new Validators();

    private void initializeFields(User user) {
        Platform.runLater( () -> {
            nameField.setText( user.getName() );
            deviceNameField.setText( user.getDeviceName() );
            mobileNumberField.setText( user.getMobileNumber() );
            passwordField.setText( user.getDevicePassword() );
            try {
                UserSession.readDevicePicture( user );
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            if (user.getUserImage() == null) {
                Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
                circluarImageView.setFill( new ImagePattern( image ) );
            } else {
                circluarImageView.setFill( new ImagePattern( user.getUserImage() ) );
            }
        } );
    }

    private void numberTypedConsumers(KeyEvent e) {
        String ch = e.getCharacter();
        char CH = ch.charAt( 0 );
        if (!(Character.isDigit( CH ))) {
            e.consume();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // fill up fields with details from db
        initializeFields( WelcomeController.user );

        //set mobile field to accept only numbers
        mobileNumberField.setOnKeyTyped( this::numberTypedConsumers );
    }

    @FXML
    private void CloseSceneEvent(ActionEvent event) {
        updateProfileBtn.getScene().getWindow().hide();
    }

    @FXML
    private void HandleDefaultImage(ActionEvent event) {
        alerts.materialConfirmAlert( stackPane, borderPane, "Default Device Image", "Are you sure you want to set default device Image ?" );
        alerts.acceptBtn.setOnAction( e -> {
            Platform.runLater( () -> {

                try {
                    UserSession.updateDevicePicture( null );
                    WelcomeController.user = UserSession.getMainUser();
                    UserSession.readDevicePicture( WelcomeController.user );
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
                if (WelcomeController.user.getUserImage() == null) {
                    Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
                    circluarImageView.setFill( new ImagePattern( image ) );
                } else {
                    circluarImageView.setFill( new ImagePattern( WelcomeController.user.getUserImage() ) );
                }

            } );
        } );
    }

    @FXML
    private void HandleUpdateProfile(ActionEvent event) throws IOException, SQLException {
        String name = nameField.getText();
        String mobileNumber = mobileNumberField.getText();
        String deviceName = deviceNameField.getText();
        String password = passwordField.getText().trim();
        String oldName = WelcomeController.user.getName();
        if (name.isEmpty() || mobileNumber.isEmpty() || deviceName.isEmpty() || password.isEmpty()) {
            alerts.Notification( "Empty Field(s)", "Ensure all fields are filled up" );
        } else {
            if (validators.validateMobileNumber( mobileNumber )) {
                WelcomeController.user.setMobileNumber( mobileNumber );
                WelcomeController.user.setDevicePassword( password );
                WelcomeController.user.setDeviceName( deviceName );
                WelcomeController.user.setName( name );

                alerts.materialConfirmAlert( stackPane,borderPane,"Profile Update","Your profile will be updated if you continue" );
                alerts.acceptBtn.setOnAction( e -> {
                    try {
                        UserSession.updateUserDetails( WelcomeController.user,oldName );
                        alerts.materialInfoAlert( stackPane,borderPane,"Update Success","Profile is updated successfully" );
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                } );
            } else {
                alerts.Notification( "Invalid Mobile Number", "Ensure your mobile number is entered correctly" );
            }
        }
    }

    @FXML
    private void HandleUploadDeviceImage(ActionEvent event) throws IOException, SQLException {
        FileChooser fileChoice = new FileChooser();
        fileChoice.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter( "Image Files", "*.png", "*.jpg", "*.gif" )
        );
        File file = fileChoice.showOpenDialog( MainApp.showProfileUpdateView() );
        if (file != null) {
            UserSession.updateDevicePicture( file.getPath() );
            Image image = new Image( file.toURI().toString() );
            circluarImageView.setFill( new ImagePattern( image ) );
            alerts.materialInfoAlert( stackPane, borderPane, "Device Image Uploaded", "Device Image is uploaded successfully" );
        } else {
            alerts.materialInfoAlert( stackPane, borderPane, "Invalid Image", "The image selected is not valid" );
        }
    }

}
