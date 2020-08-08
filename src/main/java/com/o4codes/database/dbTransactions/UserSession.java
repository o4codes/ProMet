package com.o4codes.database.dbTransactions;

import com.o4codes.database.DbConfig;
import com.o4codes.models.User;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class UserSession {

    // Create the user table if it doesn't exist
    public static void createUserTable() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String table_name = "User";
        assert con != null;
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rst = dbMeta.getTables( null, null, null, null );
        ArrayList<String> table_names = new ArrayList<>();
        while (rst.next()) {
            table_names.add( rst.getString( "TABLE_NAME" ).toLowerCase() );
        }
        if (!table_names.contains( table_name.toLowerCase() )) {
            String query = "CREATE TABLE " + table_name + " (`DeviceImage` BLOB, `Name` TEXT, `MobileNo` TEXT, `DeviceName` TEXT, `DevicePassword` TEXT);";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate();
        }
        con.close();

    }

    //clear records in user database table
    public static void deleteTableRecords() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String table_name = "User";
        assert con != null;
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rst = dbMeta.getTables( null, null, null, null );
        ArrayList<String> table_names = new ArrayList<>();
        while (rst.next()) {
            table_names.add( rst.getString( "TABLE_NAME" ).toLowerCase() );
        }
        if (table_names.contains( table_name.toLowerCase() )) {
            String query = "DELETE FROM User";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate();
        }
        con.close();
    }

    public static boolean insertBasicUserDetails(User user) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "INSERT INTO User(Name,MobileNo,DeviceName,DevicePassword) VALUES (?,?,?,?)";
        assert con != null;

        PreparedStatement pst = con.prepareStatement( query );
        pst.setString( 1, user.getName() );
        pst.setString( 2, user.getMobileNumber() );
        pst.setString( 3, user.getDeviceName() );
        pst.setString( 4, String.valueOf( user.getDevicePassword().hashCode() ) );
        pst.execute();
        con.close();
        return isUserRegistered( user );
    }

    public static void updateUserDetails(User user, String name) throws IOException, SQLException {
        Connection connection = DbConfig.Connector();
        String query = "UPDATE User SET Name = ?, MobileNo = ?, DeviceName = ?, DevicePassword = ? WHERE Name = '" + name + "' ";
        assert connection != null;
        PreparedStatement pst = connection.prepareStatement( query );
        pst.setString( 1, user.getName() );
        pst.setString( 2, user.getMobileNumber() );
        pst.setString( 3, user.getDeviceName() );
        pst.setString( 4, String.valueOf( user.getDevicePassword().hashCode() ) );
        pst.execute();
        connection.close();
    }

    public static boolean isUserRegistered(User user) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM User WHERE Name ='" + user.getName() + "' AND MobileNo = '" + user.getMobileNumber() + "' ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        String name = null;
        boolean status;
        if (rst.next()) {
            user = new User( rst.getString( "Name" ),
                    rst.getString( "MobileNo" ),
                    rst.getString( "DeviceName" ),
                    rst.getString( "DevicePassword" ),
                    null);
            status = true;
        }
        else {
            status = false;
        }
        con.close();
        return status;
    }

    public static boolean comparePassword(String password) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String dbPassword = null;
        String query = "SELECT * FROM User";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            dbPassword = rst.getString( "DevicePassword" );
        }
        assert dbPassword != null;
        boolean status = password.hashCode() == Integer.parseInt( dbPassword );
        con.close();
        return status;
    }

    public static boolean isTableNotEmpty() throws SQLException, IOException {
        Connection con = DbConfig.Connector();
        String query = "SELECT COUNT(*) FROM User";
        int result = 0;
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            result = rst.getInt( 1 );
        }
        boolean status = result > 0;
        con.close();

        return status;
    }

    public static User getUser(String name, String mobileNo) throws SQLException, IOException{
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM User WHERE Name ='" +name+ "' AND MobileNo = '" +mobileNo+ "' ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        User user = null;
        if (rst.next()) {

                user = new User( rst.getString( "Name" ),
                        rst.getString( "MobileNo" ),
                        rst.getString( "DeviceName" ),
                        rst.getString( "DevicePassword" ),
                        null );

        }
        con.close();
        return user;
    }

    public static User getMainUser() throws SQLException, IOException{
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM User  ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        User user = null;
        if (rst.next()) {

            user = new User( rst.getString( "Name" ),
                    rst.getString( "MobileNo" ),
                    rst.getString( "DeviceName" ),
                    rst.getString( "DevicePassword" ),
                    null );

        }
        con.close();
        return user;
    }

    private byte[] readFile(String filePath) throws IOException {
        ByteArrayOutputStream bos = null;
        File file = new File( filePath );
        FileInputStream fis = new FileInputStream( file );
        byte[] buffer = new byte[1024];
        bos = new ByteArrayOutputStream();

        for (int len; (len = fis.read( buffer )) != 1; ) {
            bos.write( buffer, 0, len );
        }
        return bos.toByteArray();
    }

    private void updateDevicePicture(String filePath) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "UPDATE User SET DeviceImage = ?";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.setBytes( 1, readFile( filePath ) );
        pst.executeUpdate();
        con.close();
    }

    private void readDevicePicture(User user) throws IOException, SQLException {
        // direction to save image to
        String userDir = System.getProperty( "user.dir" );
        String dbDir = userDir + File.separator + ".persist/deviceImage.jpg";

        //Sqlite transactions
        Connection con = DbConfig.Connector();
        String query = "SELECT DeviceImage FROM User";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        File file = new File( dbDir );
        FileOutputStream fos = new FileOutputStream( file );
        while (rst.next()) {
            InputStream input = rst.getBinaryStream( "DeviceImage" );
            byte[] buffer = new byte[1024];
            while (input.read( buffer ) > 0) {
                fos.write( buffer );
            }
        }
        con.close();
        user.setUserImage( new Image( dbDir ) );
    }


}






