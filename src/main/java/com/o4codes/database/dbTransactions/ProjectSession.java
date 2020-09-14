package com.o4codes.database.dbTransactions;

import com.o4codes.database.DbConfig;
import com.o4codes.models.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProjectSession {

    // initialize projects table in database
    public static void createProjectsTable() throws SQLException, IOException {
        Connection con = DbConfig.Connector();
        String table_name = "Projects";
        assert con != null;
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rst = dbMeta.getTables( null, null, null, null );
        ArrayList<String> table_names = new ArrayList<>();
        while (rst.next()) {
            table_names.add( rst.getString( "TABLE_NAME" ).toLowerCase() );
        }
        if (!table_names.contains( table_name.toLowerCase() )) {
            String query = "CREATE TABLE " + table_name + " (`Id` VARCHAR PRIMARY KEY , `ColorTheme` TEXT, `Title` TEXT, `Description` TEXT, `BeginDate` TEXT, " +
                    "`DueDate` TEXT, `CompletionDate` TEXT);";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate();
            System.out.println("Projects Table is created");
        } else {
            System.out.println("Projects Table already exists");
        }
        con.close();
    }

    //insert project into projects Table
    public static boolean insertProject(Project project) throws SQLException, IOException {
        Connection con = DbConfig.Connector();
        project.setId( String.valueOf( LocalDateTime.now().hashCode() ) );
        String query = "INSERT INTO Projects (Id,ColorTheme, Title, Description, BeginDate, DueDate, CompletionDate) " +
                "VALUES (?,?,?,?,?,?,?)";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.setString( 1, project.getId() );
        pst.setString( 2, project.getColorTheme() );
        pst.setString( 3, project.getTitle() );
        pst.setString( 4, project.getDescription() );
        pst.setString( 5, project.getBeginDate().toString() );
        pst.setString( 6, project.getDueDate().toString() );
        pst.setString( 7,null );
        pst.execute();
        con.close();

        return getProjectById( project.getId() ).getId().equals( project.getId() );
    }

    //delete a project from projects Table
    public static boolean deleteProject(Project project) throws SQLException, IOException{
        Connection connection = DbConfig.Connector();
        String query = "DELETE FROM Projects WHERE Id ='"+project.getId()+"' ";
        assert connection != null;
        PreparedStatement pst = connection.prepareStatement( query );
        pst.executeUpdate();
        connection.close();
        return getProjectById( project.getId() ) == null;
    }

    // delete all project from projects Table
    public static boolean deleteAllProject() throws SQLException, IOException{
        Connection connection = DbConfig.Connector();
        String query = "DELETE FROM Projects";
        assert connection != null;
        PreparedStatement pst = connection.prepareStatement( query );
        pst.executeUpdate();
        connection.close();
        return projectCount() <= 0;
    }

    // update a project details from projects Table
    public static void updateProject(Project project) throws SQLException, IOException{
        Connection connection = DbConfig.Connector();
        String query = "UPDATE Projects SET ColorTheme = ?, Title = ?, Description = ?, DueDate = ? " +
                "WHERE Id ='"+project.getId()+"' ";
        assert connection != null;
        PreparedStatement pst = connection.prepareStatement( query );
        pst.setString( 1, project.getColorTheme() );
        pst.setString( 2, project.getTitle() );
        pst.setString( 3, project.getDescription() );
        pst.setString( 4, project.getDueDate().toString() );
        pst.execute();
        connection.close();
    }

    //select a particular project from projects Table
    public static Project getProjectById(String Id) throws IOException, SQLException {
        Connection connection = DbConfig.Connector();
        Project project = null;
        String query = "SELECT * FROM Projects WHERE Id='" + Id + "' ";
        assert connection != null;
        ResultSet rst = connection.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            project = new Project(
                    rst.getString( "Id" ),
                    rst.getString( "ColorTheme" ),
                    rst.getString( "Title" ),
                    rst.getString( "Description" ),
                    LocalDate.parse( rst.getString( "BeginDate" ) ),
                    LocalDate.parse( rst.getString( "DueDate" ) ),
                    rst.getString( "CompletionDate") == null ? null : LocalDate.parse( rst.getString( "CompletionDate") ) );
        }
        connection.close();
        return project;
    }

    // select all projects from projects Table
    public static ObservableList<Project> getProjects() throws IOException, SQLException, NullPointerException {
        ObservableList<Project> projects = FXCollections.observableArrayList();
        Connection connection = DbConfig.Connector();
        String query = "SELECT * FROM Projects ORDER BY BeginDate";
        assert connection != null;
        ResultSet rst = connection.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            projects.add( new Project(
                    rst.getString( "Id" ),
                    rst.getString( "ColorTheme" ),
                    rst.getString( "Title" ),
                    rst.getString( "Description" ),
                    LocalDate.parse( rst.getString( "BeginDate" ) ),
                    LocalDate.parse( rst.getString( "DueDate" ) ),
                    rst.getString( "CompletionDate" ) == null ? null : LocalDate.parse( rst.getString( "CompletionDate" )) ));
        }
        connection.close();
        return projects;
    }

    // get count of all projects in projects Table
    public static int projectCount() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        int projectNo = 0;
        String query = "SELECT COUNT(*) FROM Projects";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            projectNo = rst.getInt( 1 );
        }
        con.close();

        return projectNo;
    }
}