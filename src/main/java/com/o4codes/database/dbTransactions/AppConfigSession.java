package com.o4codes.database.dbTransactions;

import com.o4codes.database.DbConfig;
import com.o4codes.helpers.Utils;
import com.o4codes.models.AppConfiguration;
import org.sqlite.JDBC;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AppConfigSession {

    //create app configuration persistence table
    public static void createAppConfigTable() throws SQLException, IOException {
        Connection con = DbConfig.Connector();
        String table_name = "AppConfig";
        assert con != null;
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rst = dbMeta.getTables( null, null, null, null );
        ArrayList<String> table_names = new ArrayList<>();
        while (rst.next()) {
            table_names.add( rst.getString( "TABLE_NAME" ).toLowerCase() );
        }
        if (!table_names.contains( table_name.toLowerCase() )) {
            String query = "CREATE TABLE " + table_name + " (`MaxProjects` INTEGER, `TaskDuration` INTEGER, " +
                    "`ShortBreakDuration` INTEGER, `LongBreakDuration` INTEGER, `AppTheme` TEXT);";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate();
            con.close();

            //insert default configurations on app install;
            insertDefaultAppConfigurations();
        }

    }

    //insert default configurations to table
    private static void insertDefaultAppConfigurations() throws IOException, SQLException {
        Connection connection = DbConfig.Connector();
        AppConfiguration appConfiguration = new AppConfiguration( 5, 25, 5, 15, Utils.appTheme.get( 0 ) );
        String query = "INSERT INTO AppConfig (MaxProjects, TaskDuration, ShortBreakDuration, LongBreakDuration, AppTheme) " +
                "VALUES (?,?,?,?,?)";
        assert connection != null;
        PreparedStatement pst = connection.prepareStatement( query );
        pst.setInt( 1, appConfiguration.getMaxProjects() );
        pst.setInt( 2, appConfiguration.getTaskDuration() );
        pst.setInt( 3, appConfiguration.getShortBreakDuration() );
        pst.setInt( 4, appConfiguration.getLongBreakDuration() );
        pst.setString( 5, appConfiguration.getAppTheme() );

        pst.execute();
        System.out.println( "Default details inserted into table" );
        connection.close();
    }

    //update configurations
    public static void updateAppConfigurations(AppConfiguration appConfiguration) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "UPDATE AppConfig SET MaxProjects = ?, TaskDuration = ?, " +
                "ShortBreakDuration = ?, LongBreakDuration = ?, AppTheme = ?";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.setInt( 1,appConfiguration.getMaxProjects() );
        pst.setInt( 2,appConfiguration.getTaskDuration() );
        pst.setInt( 3, appConfiguration.getShortBreakDuration() );
        pst.setInt( 4,appConfiguration.getLongBreakDuration() );
        pst.setString( 5, appConfiguration.getAppTheme() );

        pst.execute();
        con.close();

    }

    //get configurations
    public static AppConfiguration getAppConfig() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        AppConfiguration appConfiguration = new AppConfiguration();
        String query = "SELECT * FROM AppConfig";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        ResultSet rst = pst.executeQuery();
        while (rst.next()){
            appConfiguration.setMaxProjects( rst.getInt( "MaxProjects" ) );
            appConfiguration.setAppTheme( rst.getString( "AppTheme" ));
            appConfiguration.setLongBreakDuration( rst.getInt( "LongBreakDuration" ) );
            appConfiguration.setShortBreakDuration( rst.getInt( "ShortBreakDuration") );
            appConfiguration.setTaskDuration(rst.getInt( "TaskDuration" ));
        }
        con.close();
        return appConfiguration;
    }

    //delete configuration
    public static void deleteConfigurations() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "DELETE FROM AppConfig";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.executeUpdate();
        con.close();
    }
}
