package com.o4codes.controllers;

import animatefx.animation.Bounce;
import animatefx.animation.FadeInLeft;
import com.jfoenix.controls.JFXButton;
import com.o4codes.MainApp;
import com.o4codes.helpers.Alerts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
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
    private MenuItem editProfileBtn;

    @FXML
    private MenuItem signOutBtn;

    @FXML
    private Circle profileCircleImage;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane contentPane;

    private Alerts alerts = new Alerts();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //animate the navigation buttons
        introAnimation();
    }

    @FXML
    void ShowDashboard(ActionEvent event) {

    }

    @FXML
    void ShowEditProfile(ActionEvent event) {

    }

    @FXML
    void ShowProjects(ActionEvent event) {

    }

    @FXML
    void ShowSettings(ActionEvent event) {

    }

    @FXML
    void ShowTimeline(ActionEvent event) {

    }

    @FXML
    private void SignOutEvent(ActionEvent event) {
       exit();
    }

    public void exit(){
        alerts.materialConfirmAlert( stackPane, borderPane, "SIGN OUT", "Are you sre you want to sign out ?" );
        alerts.acceptBtn.setOnAction( e -> System.exit( 0 ) );
    }

    private void introAnimation(){
        Platform.runLater(() -> {
            Bounce bounce = new Bounce( dashboardBtn );
            Bounce bounce1 = new Bounce( projectsBtn );
            Bounce bounce2 = new Bounce( timelineBtn );
            Bounce bounce3 = new Bounce( settingsBtn );

            bounce.setDelay( Duration.seconds( 1 ) );
            bounce.play();
            bounce.playOnFinished( bounce1 );
            bounce1.playOnFinished( bounce2 );
            bounce2.playOnFinished( bounce3 );
        });
    }

    private void setNode(Node node) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add( node );
        contentPane.setEffect( null );
        node.setLayoutX(0);
        node.setLayoutY(0);
        FadeInLeft fade = new FadeInLeft(node);
        fade.setDelay( Duration.seconds(1) );
        fade.play();
    }

    private void createPage(String loc) throws IOException {
        AnchorPane home = FXMLLoader.load( MainApp.class.getResource( loc ) );
        setNode( home );
        AnchorPane.setTopAnchor( home, 0.0);
        AnchorPane.setBottomAnchor( home, 0.0);
        AnchorPane.setLeftAnchor( home, 0.0);
        AnchorPane.setRightAnchor( home, 0.0);
    }

}
