package com.o4codes.controllers;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.Alerts;
import com.o4codes.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppHomeController implements Initializable {
    @FXML
    private JFXButton dashboardBtn;

    @FXML
    private JFXButton projectsBtn;

    @FXML
    private JFXButton timelineBtn;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    private JFXButton signOutBtn;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label moduleTitleLbl;

    @FXML
    private Label deviceNameLbl;

    @FXML
    private Label profileNameLbl;

    @FXML
    private Label dateTimeLbl;

    @FXML
    private Rectangle profileImage;

    @FXML
    private JFXButton maximizeBtn;

    @FXML
    private JFXButton minimizeBtn;

    @FXML
    private AnchorPane root;

    @FXML
    private HBox titleBar;

    private double lastX = 0.0d, lastY = 0.0d, lastWidth = 0.0d, lastHeight = 0.0d;

    private double xOffset = 0, yOffset = 0;

    private Alerts alerts = new Alerts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set fonts for some Label
        moduleTitleLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 20 ));
        dateTimeLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 14 ));
        deviceNameLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Bold.ttf"), 16 ));
        profileNameLbl.setFont( Font.loadFont( MainApp.class.getResourceAsStream("/fonts/Lato/Lato-Regular.ttf"), 14 ));

        //animate the navigation buttons
        introAnimation();
        setLoggedInUser();

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

        titleBar.setOnMousePressed( e -> {
            xOffset = e.getSceneX();
            yOffset = e.getScreenY();
        } );

        titleBar.setOnMouseDragged( e -> {
            Stage stage = (Stage)minimizeBtn.getScene().getWindow();
            stage.setX( e.getScreenX() - xOffset );
            stage.setY( e.getScreenY() - yOffset );
        } );

        }

    @FXML
    private void showDashboard(ActionEvent event) {

    }

    @FXML
    private void showEditProfile(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur( 5, 5, 5 );
        borderPane.setEffect( blur );
        MainApp.showProfileUpdateView().showAndWait();
        borderPane.setEffect( null );
    }

    @FXML
    private void showProjects(ActionEvent event) throws IOException {
        createPage( "/fxml/projectDashboard.fxml" );
    }

    @FXML
    private void showSettings(ActionEvent event) throws IOException {
        createPage( "/fxml/appSettings.fxml" );
    }

    @FXML
    private void showTimeline(ActionEvent event) {

    }

    @FXML
    private void signOutEvent(ActionEvent event) {
        exit();
    }

    @FXML
    private void CloseAppEvent(ActionEvent event) throws IOException {
//        MainApp.showInfoAlert( "QUIT", "Information will be saved" );
        Alerts alerts = new Alerts();
        alerts.materialInfoAlert( stackPane, borderPane, "EXIT", "Information will be saved" );
        alerts.cancelBtn.setOnAction( event1 -> {
            System.exit( 0 );
        } );

    }

    public void exit() {
        alerts.materialConfirmAlert( stackPane, borderPane, "SIGN OUT", "Are you sre you want to sign out ?" );
        alerts.acceptBtn.setOnAction( e -> System.exit( 0 ) );
    }

    private void introAnimation() {
        Platform.runLater( () -> {
            Bounce bounce = new Bounce( dashboardBtn );
            Bounce bounce1 = new Bounce( projectsBtn );
            Bounce bounce2 = new Bounce( timelineBtn );
            Bounce bounce3 = new Bounce( settingsBtn );

            bounce.setDelay( Duration.seconds( 1 ) );
            bounce.play();
            bounce.playOnFinished( bounce1 );
            bounce1.playOnFinished( bounce2 );
            bounce2.playOnFinished( bounce3 );
        } );
    }

    private void setNode(Node node) {
        if (contentPane.getChildren().size() == 0){
            contentPane.getChildren().add( node );
            contentPane.setEffect( null );
            node.setLayoutX( 0 );
            node.setLayoutY( 0 );
            node.setOpacity( 0 );
            FadeIn fade = new FadeIn( node );
            fade.setDelay( Duration.seconds( 1 ) );
            fade.play();
        }
        else {
            FadeOut fadeOutLeft = new FadeOut( contentPane.getChildren().get( 0 ) );
            fadeOutLeft.play();
            fadeOutLeft.setSpeed( 2.0 );
            fadeOutLeft.getTimeline().setOnFinished( e -> {
                contentPane.getChildren().clear();
                contentPane.getChildren().add( node );
                contentPane.setEffect( null );
                node.setLayoutX( 0 );
                node.setLayoutY( 0 );
                node.setOpacity( 0 );
                FadeIn fade = new FadeIn( node );
                fade.setDelay( Duration.seconds( 1 ) );
                fade.play();
            } );
        }

    }

    private void createPage(String loc) throws IOException {
        AnchorPane home = FXMLLoader.load( MainApp.class.getResource( loc ) );
        setNode( home );
        AnchorPane.setTopAnchor( home, 0.0 );
        AnchorPane.setBottomAnchor( home, 0.0 );
        AnchorPane.setLeftAnchor( home, 0.0 );
        AnchorPane.setRightAnchor( home, 0.0 );
    }

    private void setLoggedInUser(){
        Platform.runLater( () -> {
            try {
                WelcomeController.user = UserSession.getMainUser();
                deviceNameLbl.setText( WelcomeController.user.getDeviceName() );
                profileNameLbl.setText( WelcomeController.user.getName() );
                setUserImage();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } );
    }

    private void setUserImage(){
        try {
            if (UserSession.isTableNotEmpty()) {
                WelcomeController.user = UserSession.getMainUser();
                UserSession.readDevicePicture( WelcomeController.user );

                if (WelcomeController.user.getUserImage() == null) {
                    Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
                    profileImage.setFill( new ImagePattern( image ) );
                } else {
                    profileImage.setFill( new ImagePattern( WelcomeController.user.getUserImage() ) );
                }
            } else {
                Image image = new Image( MainApp.class.getResource( "/images/robot.jpg" ).toString() );
                profileImage.setFill( new ImagePattern( image ) );
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
