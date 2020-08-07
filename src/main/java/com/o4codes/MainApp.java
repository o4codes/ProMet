package com.o4codes;

import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.alerts.InfoAlertController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger( MainApp.class );
    public static Stage stage;

    public static void main(String[] args) throws Exception {
        launch( args );

    }

    public void start(Stage stage) throws Exception {

        //boot up necessary resources
        preLoad();
        MainApp.stage = stage;
        showWelcomeView();
    }

    private void preLoad() {
        // create Db and Tables
        Platform.runLater( () -> {
            try {
                System.out.println( "User Table will be created" );
                UserSession.createUserTable();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } );
    }


    public void showWelcomeView() throws IOException {
        log.info( "Starting Hello JavaFX and Maven demonstration application" );

        String fxmlFile = "/fxml/welcome.fxml";
        log.debug( "Loading FXML for main view from: {}", fxmlFile );
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        Parent rootNode = loader.load();

        log.debug( "Showing Welcome scene" );
        Scene scene = new Scene( rootNode );

        stage.initStyle( StageStyle.UNDECORATED );
        stage.setScene( scene );
        stage.show();
        stage.setOnCloseRequest( e -> {
            System.exit( 0 );
        } );
    }

    public static void showInfoAlert(String heading, String message) throws IOException {
        log.info( "showing information alert" );
        String fxmlFile = "/fxml/alerts/infoAlertView.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        AnchorPane rootNode = loader.load();
        InfoAlertController infoAlert = loader.getController();
        infoAlert.setComponents( heading, message );
        Scene scene = new Scene( rootNode );
        Stage stage = new Stage();
        stage.initStyle( StageStyle.UNDECORATED );
        stage.setScene( scene );
        rootNode.setOpacity( 0 );
        stage.showAndWait();
        stage.setOnCloseRequest( e -> {
            stage.hide();
        } );

    }

}
