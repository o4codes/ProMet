package com.o4codes.database.dbTransactions;

import com.o4codes.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserSessionTest {
    @Before
    public void setUpTable() throws IOException, SQLException {
        UserSession.createUserTable();
        System.out.println( "Db Connection and User Table created" );
    }

    @Test
    public void registerUserAndSignInCorrectlyTest() throws IOException, SQLException {
        User user = new User( "Shadrach","07068360667","Adam","computer123",null );
        System.out.println( "User will be saved into database" );
        assertTrue("User entered into db", UserSession.insertBasicUserDetails( user ) );
        System.out.println( "Login Session" );
        assertTrue( "Login Success", UserSession.comparePassword( user.getDevicePassword() ) );
    }

    @Test
    public void registerUserAndSignInWronglyTest() throws IOException, SQLException {
        User user = new User( "Shadrach","07068360667","Adam","computer123",null );
        System.out.println( "User will be saved into database" );
        assertTrue("User entered into db", UserSession.insertBasicUserDetails( user ) );
        System.out.println( "Login Session" );
        assertFalse( "Login Success", UserSession.comparePassword( "1234" ) );
    }

    @Test
    public void forgotPasswordTest() throws IOException, SQLException{
        User user = new User( "Shadrach","07068360667","Adam","computer123",null );
        System.out.println( "User will be saved into database" );
        assertTrue("User Details not saved into db", UserSession.insertBasicUserDetails( user ) );
        System.out.println( "Password Forgot Session" );
        String newPassword = "1234";
        assertTrue( "Mobile Number Forgotten", UserSession.isUserRegistered( user ) );
        user.setDevicePassword( newPassword );
        UserSession.updateUserDetails( user, user.getName() );
        assertEquals( "Password not changed", UserSession.getUser( user.getName(), user.getMobileNumber() ).getDevicePassword(), String.valueOf(newPassword.hashCode()) );
    }
    @Test
    public void getWrongUserTest() throws IOException, SQLException{
        User user = new User( "Shadrach","07068360667","Adam","computer123",null );
        System.out.println( "User will be saved into database" );
        assertTrue("User Details not saved into db", UserSession.insertBasicUserDetails( user ) );
        assertNull( "User exists", UserSession.getUser( user.getName(), "0901234567" ) );
    }

    @After
    public void clearResults() throws IOException, SQLException {
        if (UserSession.isTableNotEmpty()){
            UserSession.deleteTableRecords();
            System.out.println( "Values found and cleared" );
            System.out.println( "Db Connection and User Table Records deleted" );
        }
    }
}