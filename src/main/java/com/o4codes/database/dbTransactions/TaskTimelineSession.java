package com.o4codes.database.dbTransactions;

import com.o4codes.database.DbConfig;
import com.o4codes.models.TaskTimeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TaskTimelineSession {

    //create Table
    public static void createTaskTimeLineTable() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String table_name = "TaskTimeLine";
        assert con != null;
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rst = dbMeta.getTables( null, null, null, null );
        ArrayList<String> table_names = new ArrayList<>();
        while (rst.next()) {
            table_names.add( rst.getString( "TABLE_NAME" ).toLowerCase() );
        }
        if (!table_names.contains( table_name.toLowerCase() )) {
            String query = "CREATE TABLE " + table_name + " (`TaskId` INTEGER REFERENCES `Tasks` (`TaskId`), `Title` TEXT, `TimeConsumed` INTEGER " +
                    "`ExecutionDate` TEXT, `ExecutionTime` TEXT);";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate();
        }
        con.close();
    }

    //insert into Task Timeline Table
    public static void insertTaskTimeLine(TaskTimeline taskTimeline) throws IOException, SQLException {
        Connection connection = DbConfig.Connector();
        String query = "INSERT INTO TaskTimeLine (TaskId, TimeConsumed ExecutionDate, ExecutionTime) VALUES (?,?,?,?)";
        assert connection != null;
        PreparedStatement pst = connection.prepareStatement( query );
        pst.setInt( 1, taskTimeline.getTaskId() );
        pst.setInt( 2,taskTimeline.getTaskTimeSpent() );
        pst.setString( 3, taskTimeline.getExecutionDate().toString() );
        pst.setString( 4, taskTimeline.getExecutionTime().toString() );
        pst.execute();
        connection.close();
    }

    //get all TaskTimeline
    public static ObservableList<TaskTimeline> getTaskTimeLineByTaskId(String taskId) throws IOException, SQLException {
        ObservableList<TaskTimeline> timelineList = FXCollections.observableArrayList();
        Connection connection = DbConfig.Connector();
        String query = "SELECT * FROM TaskTimeLine WHERE TaskId = '"+taskId+"' ";
        assert connection != null;
        ResultSet rst = connection.prepareStatement( query ).executeQuery();
        while(rst.next()){
            timelineList.add( new TaskTimeline( rst.getInt( "TaskId" ),
                    rst.getInt( "TimeConsumed" ),
                    rst.getString( "ExecutionDate" ) == null ? null : LocalDate.parse(rst.getString( "ExecutionDate" )),
                    rst.getString( "ExecutionTime" ) == null ? null : LocalTime.parse(  rst.getString( "ExecutionTime" ))) );
        }
        return timelineList;
    }

    //get all TaskTimeline
    public static ObservableList<TaskTimeline> getTaskAllTimeLine() throws IOException, SQLException {
        ObservableList<TaskTimeline> timelineList = FXCollections.observableArrayList();
        Connection connection = DbConfig.Connector();
        String query = "SELECT * FROM TaskTimeLine";
        assert connection != null;
        ResultSet rst = connection.prepareStatement( query ).executeQuery();
        while(rst.next()){
            timelineList.add( new TaskTimeline( rst.getInt( "TaskId" ),
                    rst.getInt( "TimeConsumed" ),
                    rst.getString( "ExecutionDate" ) == null ? null : LocalDate.parse(rst.getString( "ExecutionDate" )),
                    rst.getString( "ExecutionTime" ) == null ? null : LocalTime.parse(  rst.getString( "ExecutionTime" ))) );
        }
        return timelineList;
    }

    //remove all task timeline history
    public static void removeAllTaskTimeLine() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "DELETE FROM TaskTimeLine";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.execute();
        con.close();
    }

    //remove particular task time line
    public static void removeTaskTimeLine(TaskTimeline taskTimeline) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "DELETE FROM TaskTimeLine WHERE TaskId ='"+taskTimeline.getTaskId()+"' ";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.execute();
        con.close();
    }
}
