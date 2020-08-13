package com.o4codes;

import com.o4codes.controllers.AppHomeController;
import com.o4codes.database.dbTransactions.AppConfigSession;
import com.o4codes.database.dbTransactions.UserSession;
import com.o4codes.helpers.Alerts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {
    private static final Logger log = LoggerFactory.getLogger( MainApp.class );

    public static Stage stage;

    public void start(Stage stage) throws Exception {
        //boot up necessary resources
        preLoad();
        MainApp.stage = stage;
        Platform.runLater( () -> {
            try {
               showWelcomeView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );

    }

    private void preLoad() throws IOException, SQLException {
        // create Db and Tables
        UserSession.createUserTable();
        AppConfigSession.createAppConfigTable();
    }

    public static void showWelcomeView() throws IOException {
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

    public static Stage showMainAppView() throws IOException {
        log.info( "Starting Hello JavaFX and Maven demonstration application" );

        String fxmlFile = "/fxml/appHome.fxml";
        log.debug( "Loading FXML for main view from: {}", fxmlFile );
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        Parent rootNode = loader.load();
        AppHomeController controller = loader.getController();

        log.debug( "Showing Welcome scene" );
        Scene scene = new Scene( rootNode );

        Stage myStage = new Stage();
        myStage.initStyle( StageStyle.UNDECORATED );
        myStage.setMaximized( true );
        myStage.setScene( scene );
        myStage.show();
        myStage.setOnCloseRequest( e -> {
            controller.exit();
        } );
        return myStage;
    }

    public static Stage showProfileUpdateView() throws IOException {
        String fxmlFile = "/fxml/profileView.fxml";
        log.debug( "Loading FXML for main view from: {}", fxmlFile );
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        AnchorPane pane = loader.load();

        log.debug( "Showing Welcome scene" );
        Scene scene = new Scene( pane );
        Stage stage = new Stage();
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.initStyle( StageStyle.UNDECORATED );
        stage.setScene( scene );

        return stage;
    }

    public static void main(String[] args) throws Exception {
        launch( args );

    }
}


