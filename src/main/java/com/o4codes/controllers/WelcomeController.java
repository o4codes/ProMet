package com.o4codes.controllers;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOutLeft;
import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.helpers.Validators;
import com.o4codes.models.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger( WelcomeController.class );
    @FXML
    private Label appTitleLbl;

    @FXML
    private JFXButton appCloseBtn;

    @FXML
    private VBox imgPane;

    @FXML
    private Label appDecriptionLbl;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox registerPane;

    @FXML
    private TextField signUpNameField;

    @FXML
    private TextField signUpMobileNumberField;

    @FXML
    private TextField signUpDeviceNameField;

    @FXML
    private PasswordField signUpDevicePasswordField;

    @FXML
    private JFXButton registerAccountBtn;

    @FXML
    private VBox signInPane;

    @FXML
    private Circle circleImage;

    @FXML
    private Label deviceNameLbl;

    @FXML
    private PasswordField signInPasswordField;

    @FXML
    private JFXButton one_btn;

    @FXML
    private JFXButton two_btn;

    @FXML
    private JFXButton three_btn;

    @FXML
    private JFXButton four_btn;

    @FXML
    private JFXButton five_btn;

    @FXML
    private JFXButton six_btn;

    @FXML
    private JFXButton seven_btn;

    @FXML
    private JFXButton eight_btn;

    @FXML
    private JFXButton nine_btn;

    @FXML
    private JFXButton zero_btn;

    @FXML
    private JFXButton backspace_btn;

    @FXML
    private JFXButton signInAccountBtn;

    @FXML
    private Label forgotPasswordLbl;

    @FXML
    private VBox fpPane;

    @FXML
    private TextField fpMobileNumber;

    @FXML
    private PasswordField fpPasswordField;

    @FXML
    private PasswordField fpConfirmPasswordField;

    @FXML
    private JFXButton resetPassword;

    @FXML
    private Label fpSignInLabel;

    @FXML
    private StackPane stackPaneContainer;

    @FXML
    private BorderPane borderPane;

    private String passwordDisplayed;

    private StringProperty passwordShown = new SimpleStringProperty();

    Alerts alerts = new Alerts();

    Validators validators = new Validators();

    public static User user;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            startUpMode();
            setSystemImage();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        imgPane.setTranslateX( 250 );
        stackPane.setTranslateX( 600 );
        animateIntroduction();
        signInPasswordField.textProperty().bind( passwordShown );
        passwordShown.setValue( "" );

        //ensure only numbers are typed in this fields
        fpPasswordField.setOnKeyTyped( this::numberTypedConsumers );
        fpConfirmPasswordField.setOnKeyTyped( this::numberTypedConsumers );
        signInPasswordField.setOnKeyTyped( this::numberTypedConsumers );
        signUpDevicePasswordField.setOnKeyTyped( this::numberTypedConsumers );
        signUpMobileNumberField.setOnKeyTyped( this::numberTypedConsumers );
        fpMobileNumber.setOnKeyTyped( this::numberTypedConsumers );
    }

    //_____________user defined methods

    private void startUpMode() throws IOException, SQLException {
        if (UserSession.isTableNotEmpty()) {
            signInPane.setOpacity( 1 );
            signInPane.toFront();
            registerPane.setOpacity( 0 );
            fpPane.setOpacity( 0 );
            deviceNameLbl.setText( UserSession.getMainUser().getDeviceName() );
        } else {
            registerPane.toFront();
            signInPane.setOpacity( 0 );
            fpPane.setOpacity( 0 );
        }
    }

    private void animateIntroduction() {
        Platform.runLater( () -> {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode( imgPane );
            translateTransition.setDelay( Duration.seconds( 6 ) );
            translateTransition.setDuration( Duration.seconds( 3 ) );
            translateTransition.setFromX( 250 );
            translateTransition.setToX( 0 );

            TranslateTransition translateTransition1 = new TranslateTransition();
            translateTransition1.setNode( stackPane );
            translateTransition1.setDelay( Duration.seconds( 6 ) );
            translateTransition1.setDuration( Duration.seconds( 3 ) );
            translateTransition1.setFromX( 600 );
            translateTransition1.setToX( 0 );
            translateTransition.play();
            translateTransition1.play();
        } );

    }

    private void transitInOut(Node initNode, Node newNode) {
        FadeOutLeft fadeOutLeft = new FadeOutLeft( initNode );
        fadeOutLeft.play();
        fadeOutLeft.getTimeline().setOnFinished( e -> {
            newNode.toFront();
            FadeInLeft fadeInLeft = new FadeInLeft( newNode );
            fadeInLeft.play();
        } );
    }

    private void setSystemImage() {
        Platform.runLater( () -> {
            //this is this way until there is a database to persist the image
            // here is the default image
            try {
                if (UserSession.isTableNotEmpty()) {
                    user = UserSession.getMainUser();
                    UserSession.readDevicePicture( user );

                    if (user.getUserImage() == null) {
                        Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
                        circleImage.setFill( new ImagePattern( image ) );
                    } else {
                        circleImage.setFill( new ImagePattern( user.getUserImage() ) );
                    }
                } else {
                    Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
                    circleImage.setFill( new ImagePattern( image ) );
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
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

    //____________________controller defined methods
    @FXML
    private void BackSpaceEvent(ActionEvent event) {
        if (passwordShown.getValue().length() >= 1) {
            int lastCharIndex = passwordShown.getValue().length() - 1;
            passwordShown.setValue( passwordShown.getValue().substring( 0, lastCharIndex ) );
        }
    }

    @FXML
    private void CloseAppEvent(ActionEvent event) throws IOException {
//        MainApp.showInfoAlert( "QUIT", "Information will be saved" );
        Alerts alerts = new Alerts();
        alerts.materialInfoAlert( stackPaneContainer, borderPane, "EXIT", "Information will be saved" );
        alerts.cancelBtn.setOnAction( event1 -> {
            System.exit( 0 );
        } );

    }

    @FXML
    private void DigitEightEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue() + "8" );
    }

    @FXML
    private void DigitFiveEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "5" ) );
    }

    @FXML
    private void DigitFourEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "4" ) );
    }

    @FXML
    private void DigitNineEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "9" ) );
    }

    @FXML
    private void DigitOneEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "1" ) );
    }

    @FXML
    private void DigitSevenEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "7" ) );
    }

    @FXML
    private void DigitSixEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "6" ) );
    }

    @FXML
    private void DigitThreeEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "3" ) );
    }

    @FXML
    private void DigitTwoEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "2" ) );
    }

    @FXML
    private void DigitZeroEvent(ActionEvent event) {
        passwordShown.set( passwordShown.getValue().concat( "0" ) );
    }

    @FXML
    private void ForgotPasswordEvent(MouseEvent event) {
        transitInOut( signInPane, fpPane );
    }

    @FXML
    private void RegisterAccountEvent(ActionEvent event) throws IOException, SQLException {
        String name = signUpNameField.getText();
        String deviceName = signUpDeviceNameField.getText();
        String mobileNo = signUpMobileNumberField.getText().trim();
        String password = signUpDevicePasswordField.getText();

        if (name.isEmpty() || deviceName.isEmpty() || mobileNo.isEmpty() || password.isEmpty()) {
            alerts.Notification( "Empty Field(s)", "Ensure all fields are filled up" );
        } else {
            if (validators.validateMobileNumber( mobileNo )) {
                user = new User( name, mobileNo, deviceName, password, null );

                if (UserSession.insertBasicUserDetails( user )) {
                    alerts.materialInfoAlert( stackPaneContainer, borderPane, "User Sign Up Successful", "User " + name + " has been successfully saved" );
                    alerts.cancelBtn.setOnAction( e -> {
                        transitInOut( registerPane, signInPane );
                        deviceNameLbl.setText( user.getDeviceName() );
                    } );
                } else {
                    alerts.materialInfoAlert( stackPaneContainer, borderPane, "User Saving Failed", "User " + name + " could not be saved" );
                }
            } else {
                alerts.Notification( "Invalid Mobile Number", "Ensure your mobile number is entered correctly" );
            }
        }

    }

    @FXML
    private void SignInEvent(ActionEvent event) throws IOException, SQLException {
        String password = signInPasswordField.getText();
        if (password.isEmpty()) {
            alerts.Notification( "Empty Field(s)", "Ensure the password field is not empty" );
        } else {
            if (UserSession.comparePassword( password )) {
                alerts.materialInfoAlert( stackPaneContainer, borderPane, "Sign In Successful", "You will be directed to your dashboard" );
                MainApp.showMainAppView().show();
                backspace_btn.getScene().getWindow().hide();
            } else {
                alerts.materialInfoAlert( stackPaneContainer, borderPane, "Sign In Failed", "Enter the correct password" );
            }
        }
    }

    @FXML
    private void HandleResetPassword(ActionEvent event) throws IOException, SQLException {
        String newPassword = fpPasswordField.getText();
        String confirmPassword = fpConfirmPasswordField.getText();
        String mobileNumber = fpMobileNumber.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty() || mobileNumber.isEmpty()) {
            alerts.Notification( "Empty Field(s)", "Ensure all fields are filled up" );
        } else {
            if (validators.validateMobileNumber( mobileNumber )) {
                if (UserSession.getMainUser().getMobileNumber().equals( mobileNumber )) {
                    if (newPassword.equals( confirmPassword )) {
                        User user = UserSession.getMainUser();
                        user.setDevicePassword( newPassword );
                        UserSession.updateUserDetails( user, user.getName() );
                        alerts.materialInfoAlert( stackPaneContainer, borderPane, "Password Reset Successful", "Password has been successfully reset. Sign In with new password" );
                    } else {
                        alerts.Notification( "Mismatching Password", "Passwords entered do not match, \n Check Passwords " );
                    }
                } else {
                    alerts.Notification( "Wrong Mobile Number", "Mobile number entered doesn't match user mobile number" );
                }
            } else {
                alerts.Notification( "Invalid Mobile Number", "Ensure your mobile number is entered correctly" );
            }
        }
    }

    @FXML
    private void SignIntoAccountEvent(MouseEvent event) {
        transitInOut( fpPane, signInPane );
    }


}
