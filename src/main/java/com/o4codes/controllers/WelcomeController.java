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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private Rectangle circleImage;

    @FXML
    private Label deviceNameLbl;

    @FXML
    private Label signInNameLbl;

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
    private PasswordField pass1;

    @FXML
    private PasswordField pass2;

    @FXML
    private PasswordField pass3;

    @FXML
    private PasswordField pass4;

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

    @FXML
    private JFXButton maximizeBtn;

    @FXML
    private JFXButton minimizeBtn;

    @FXML
    private HBox titleBar;

    @FXML
    private AnchorPane root;

    private String passwordTyped;

    private Alerts alerts = new Alerts();

    private Validators validators = new Validators();

    public static User user;

    private double lastX = 0.0d, lastY = 0.0d, lastWidth = 0.0d, lastHeight = 0.0d;

    private double xOffset = 0, yOffset = 0;

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

        //ensure only numbers are typed in this fields
        fpPasswordField.setOnKeyTyped( this::numberTypedConsumers );
        fpConfirmPasswordField.setOnKeyTyped( this::numberTypedConsumers );
        signUpDevicePasswordField.setOnKeyTyped( this::numberTypedConsumers );
        signUpMobileNumberField.setOnKeyTyped( this::numberTypedConsumers );
        fpMobileNumber.setOnKeyTyped( this::numberTypedConsumers );
        passwordTyped = "";

        //add action events to the following buttons
        minimizeBtn.setOnAction( e -> {
        Stage stage = (Stage)minimizeBtn.getScene().getWindow();
        stage.setIconified( true );
        } );

        maximizeBtn.setOnAction( e -> {
            Node node = (Node)e.getSource();
            Window window = node.getScene().getWindow();
            double currentX = window.getX();
            double currentY = window.getY();
            double currentWidth = window.getWidth();
            double currentHeight = window.getHeight();

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            if (currentX != bounds.getMinX() && currentY != bounds.getMinY()
                    && currentWidth != bounds.getWidth() && currentHeight != bounds.getHeight()){
                window.setX( bounds.getMinX() );
                window.setY( bounds.getMinY() );
                window.setWidth( bounds.getWidth() );
                window.setHeight( bounds.getHeight() );

                //save old dimensions
                lastX = currentX;
                lastY = currentY;
                lastWidth = currentWidth;
                lastHeight = currentHeight;
            }
            else {
                //de-maximize the window
                window.setX( lastX );
                window.setY( lastY );
                window.setWidth( lastWidth );
                window.setHeight( lastHeight );
            }

            // ensure to not bubble up tot title bar
            e.consume();
        } );

        root.setOnMousePressed( e -> {
            xOffset = e.getSceneX();
            yOffset = e.getScreenY();
        } );

        root.setOnMouseDragged( e -> {
            Stage stage = (Stage)minimizeBtn.getScene().getWindow();
            stage.setX( e.getScreenX() - xOffset );
            stage.setY( e.getScreenY() - yOffset );
        } );
    }

    //_____________user defined methods

    private void startUpMode() throws IOException, SQLException {
        if (UserSession.isTableNotEmpty()) {
            signInPane.setOpacity( 1 );
            signInPane.toFront();
            registerPane.setOpacity( 0 );
            fpPane.setOpacity( 0 );
            deviceNameLbl.setText( UserSession.getMainUser().getDeviceName() );
            signInNameLbl.setText( UserSession.getMainUser().getName() );
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
        passwordTyped = "";
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

        Object target = e.getTarget();
        if (target instanceof PasswordField){
            PasswordField passwordField = (PasswordField) target;
            if (passwordField.getText().length() > 3){
                e.consume();
            }
        }
    }

    private void numbersTyped(String number) {
        if (pass1.getText().isEmpty()) {
            pass1.setText( number );
        } else {
            if (pass2.getText().isEmpty()) {
                pass2.setText( number );
            } else {
                if (pass3.getText().isEmpty()) {
                    pass3.setText( number );
                } else {
                    if (pass4.getText().isEmpty()) {
                        pass4.setText( number );
                    }
                }
            }
        }
    }

    private void backSpaceEvent() {
        if (!pass4.getText().isEmpty()) {
            pass4.clear();
        } else {
            if (!pass3.getText().isEmpty()) {
                pass3.clear();
            } else {
                if (!pass2.getText().isEmpty()) {
                    pass2.clear();
                } else {
                    if (!pass1.getText().isEmpty()) {
                        pass1.clear();
                    }
                }
            }
        }
    }

    //____________________controller defined methods
    @FXML
    private void BackSpaceEvent(ActionEvent event) { backSpaceEvent();  }

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
        numbersTyped( "8" );
    }

    @FXML
    private void DigitFiveEvent(ActionEvent event) {
        numbersTyped( "5" );
    }

    @FXML
    private void DigitFourEvent(ActionEvent event) {
        numbersTyped( "4" );
    }

    @FXML
    private void DigitNineEvent(ActionEvent event) {
        numbersTyped( "9" );
    }

    @FXML
    private void DigitOneEvent(ActionEvent event) {
        numbersTyped( "1" );
    }

    @FXML
    private void DigitSevenEvent(ActionEvent event) {
        numbersTyped( "7" );
    }

    @FXML
    private void DigitSixEvent(ActionEvent event) {
        numbersTyped( "6" );
    }

    @FXML
    private void DigitThreeEvent(ActionEvent event) {
        numbersTyped( "3" );
    }

    @FXML
    private void DigitTwoEvent(ActionEvent event) { numbersTyped( "2" ); }

    @FXML
    private void DigitZeroEvent(ActionEvent event) { numbersTyped( "0" );  }

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

        if (pass1.getText().isEmpty() || pass2.getText().isEmpty() || pass3.getText().isEmpty() || pass4.getText().isEmpty()) {
            alerts.Notification( "Empty Field(s)", "Ensure the password field is not empty" );
        } else {
            String password = pass1.getText() + pass2.getText() + pass3.getText() + pass4.getText();
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
