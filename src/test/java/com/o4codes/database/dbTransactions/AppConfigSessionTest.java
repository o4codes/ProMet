package com.o4codes.database.dbTransactions;

import com.o4codes.helpers.Utils;
import com.o4codes.models.AppConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class AppConfigSessionTest {
    @Before
    public void setupAppConfigTable() throws IOException, SQLException {
        AppConfigSession.createAppConfigTable();
        System.out.println( "App Configuration Table Created" );
    }

    @Test
    public void verifyExistingConfig() throws IOException, SQLException {
        assertEquals( "Not Equal", AppConfigSession.getAppConfig().getAppTheme(), Utils.appTheme.get( 0 ) );
    }

    @Test
    public void updateExistingConfig() throws IOException, SQLException {
        AppConfiguration appConfiguration = AppConfigSession.getAppConfig();
        appConfiguration.setMaxProjects( 6 );
        AppConfigSession.updateAppConfigurations( appConfiguration );
        assertEquals( "Assert Equals", appConfiguration.getMaxProjects(), 6 );
    }

}

