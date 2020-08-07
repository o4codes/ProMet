package com.o4codes.database;

import org.sqlite.SQLiteConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    private static void createFolder() throws IOException {
        // check if folder exists before creating it
        String userDir = System.getProperty( "user.dir" );
        String dbDir = userDir+ File.separator+".persist";
        if (!Files.exists( Paths.get(dbDir))){
            Files.createDirectory( Paths.get(dbDir));
        }
       // C:\Windows\System32 production database path
    }

    public static Connection Connector() throws IOException {
        try {
            createFolder();
            Class.forName( "org.sqlite.JDBC" );
            return DriverManager.getConnection( "jdbc:sqlite:.persist/Data.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println( e.toString() );
            return null;
        }

    }
}
