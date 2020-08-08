package com.o4codes.controllers;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOutLeft;
import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.Alerts;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    private TextField signUpDevicePasswordField;

    @FXML
    private JFXButton registerAccountBtn;

    @FXML
    private VBox signInPane;

    @FXML
    private Circle circleImage;

    @FXML
    private Label deviceNameLbl;

    @FXML
    private TextField signInPasswordField;

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
    }

    private void startUpMode() throws IOException, SQLException {
        if (UserSession.isTableNotEmpty()) {
            signInPane.setOpacity( 1 );
            signInPane.toFront();
            registerPane.setOpacity( 0 );
            fpPane.setOpacity( 0 );
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

    private void forgotPasswordTransit(){
        FadeOutLeft fadeOutLeft = new FadeOutLeft( signInPane);
        fadeOutLeft.play();
        fadeOutLeft.getTimeline().setOnFinished( e -> {
            fpPane.toFront();
            FadeInLeft fadeInLeft = new FadeInLeft( fpPane );
            fadeInLeft.play();
        } );
    }

    private void transitInOut(Node initNode, Node newNode){
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
            Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
            circleImage.setFill( new ImagePattern( image ) );
        } );

    }


    @FXML
    void BackSpaceEvent(ActionEvent event) {

    }

    @FXML
    private void CloseAppEvent(ActionEvent event) throws IOException {
//        MainApp.showInfoAlert( "QUIT", "Information will be saved" );
        Alerts alerts = new Alerts();
        alerts.materialInfoAlert( stackPaneContainer,borderPane,"EXIT","Information will be saved" );
        alerts.cancelBtn.setOnAction( event1 -> {
            System.exit( 0 );
        } );

    }

    @FXML
    void DigitEightEvent(ActionEvent event) {

    }

    @FXML
    void DigitFiveEvent(ActionEvent event) {

    }

    @FXML
    void DigitFourEvent(ActionEvent event) {

    }

    @FXML
    void DigitNineEvent(ActionEvent event) {

    }

    @FXML
    private void DigitOneEvent(ActionEvent event) {

    }

    @FXML
    void DigitSevenEvent(ActionEvent event) {

    }

    @FXML
    void DigitSixEvent(ActionEvent event) {

    }

    @FXML
    void DigitThreeEvent(ActionEvent event) {

    }

    @FXML
    void DigitTwoEvent(ActionEvent event) {

    }

    @FXML
    void DigitZeroEvent(ActionEvent event) {

    }

    @FXML
    void ForgotPasswordEvent(MouseEvent event) {
        transitInOut( signInPane,fpPane );
    }

    @FXML
    private void RegisterAccountEvent(ActionEvent event) {
        transitInOut( registerPane,signInPane );
    }

    @FXML
    void SignInEvent(ActionEvent event) {

    }

    @FXML
    private void HandleResetPassword(ActionEvent event) {

    }

    @FXML
    private void SignIntoAccountEvent(MouseEvent event) {
        transitInOut( fpPane,signInPane );
    }


}
