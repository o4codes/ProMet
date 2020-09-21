package com.o4codes.database.dbTransactions;

import com.o4codes.database.DbConfig;
import com.o4codes.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskSession {
    //create task table
    public static void createTaskTable() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String table_name = "Tasks";
        assert con != null;
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rst = dbMeta.getTables( null, null, null, null );
        ArrayList<String> table_names = new ArrayList<>();
        while (rst.next()) {
            table_names.add( rst.getString( "TABLE_NAME" ).toLowerCase() );
        }
        if (!table_names.contains( table_name.toLowerCase() )) {
            String query = "CREATE TABLE "+table_name+" (`TaskId` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`ProjectId` TEXT REFERENCES `Projects` (`Id`), `Title` TEXT, `Description` TEXT, `Duration` INTEGER, "+
                    "`BeginDate` TEXT, `BeginTime` TEXT, `DeadlineDate` TEXT, `DeadlineTime` TEXT, " +
                    "`CompletionDate` TEXT, `CompletionTime` TEXT, `Milestone` BOOLEAN);";
            PreparedStatement pst = con.prepareStatement( query );
            pst.executeUpdate();
            System.out.println("Tasks Table created");
        } else {
            System.out.println("Tasks Table already exists");
        }
        con.close();

    }

    //insert task to task table
    public static boolean insertTask(Task task) throws IOException, SQLException {
        Connection con  = DbConfig.Connector();
        String query = "INSERT INTO Tasks (ProjectId, Title, Description, Duration, BeginDate, BeginTime, DeadlineDate, DeadlineTime, Milestone) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.setString(1,task.getProjectId());
        pst.setString( 2,task.getTitle() );
        pst.setString( 3, task.getDescription() );
        pst.setInt( 4, task.getDuration() );
        pst.setString( 5, task.getBeginDate().toString() );
        pst.setString( 6,task.getBeginTime().toString() );
        pst.setString( 7,task.getDeadlineDate().toString() );
        pst.setString( 8,task.getDeadlineTime().toString() );
        pst.setBoolean( 9,task.isMileStone() );

        pst.execute();
        con.close();
        task.setTaskId( getLastTask().getTaskId() );

        return task.getTitle().equalsIgnoreCase( getLastTask().getTitle() ) && task.getBeginDate().equals( getLastTask().getBeginDate() )
                && task.getProjectId().equalsIgnoreCase( getLastTask().getProjectId() );
    }

    //update task in table
    public static void updateTask(Task task) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "UPDATE Tasks SET Title = ?, Description = ?, Duration = ?, DeadlineDate = ?, DeadlineTime = ?, CompletionDate = ?, CompletionTime = ?, Milestone = ? WHERE TaskId ='"+task.getTaskId()+"' ";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.setString( 1, task.getTitle() );
        pst.setString( 2, task.getDescription() );
        pst.setInt( 3,task.getDuration() );
        pst.setString( 4,task.getDeadlineDate().toString() );
        pst.setString( 5,task.getDeadlineTime().toString() );
        pst.setString( 6,task.getCompletionDate() == null ? null : task.getCompletionDate().toString() );
        pst.setString( 7,task.getCompletionTime() == null ? null : task.getCompletionTime().toString() );
        pst.setBoolean( 8,task.isMileStone() );

        pst.executeUpdate();
        con.close();

    }

    //delete s task
    public static boolean deleteTask(int taskId) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "DELETE FROM Tasks WHERE TaskId = '"+taskId+"' ";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.executeUpdate();

        con.close();

        return getTaskById( taskId ) == null;
    }

    //delete all tasks for a project
    public static boolean deleteAllTasksInProject( String projectId) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "DELETE FROM Tasks WHERE ProjectId ='"+projectId+"' ";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.executeUpdate();

        con.close();

        return tasksCountInProject( projectId ) == 0;
    }

    //delete all tasks for a project
    public static boolean deleteAllTasks() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        String query = "DELETE FROM Tasks";
        assert con != null;
        PreparedStatement pst = con.prepareStatement( query );
        pst.executeUpdate();

        con.close();

        return allTasksCount() == 0;
    }

    //get a particular task
    public static Task getTaskById(int TaskId) throws IOException, SQLException {
        Task task = null;
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks WHERE TaskId ='"+TaskId+"' ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()){
            task = new Task( String.valueOf(rst.getInt( "TaskId" )),
                    rst.getString( "ProjectId"), rst.getString( "Title" ),rst.getString( "Description" ),rst.getInt( "Duration" ),
                    LocalDate.parse( rst.getString( "BeginDate" )), LocalTime.parse( rst.getString( "BeginTime" ) ),
                    LocalDate.parse( rst.getString( "DeadlineDate" )),LocalTime.parse( rst.getString( "DeadlineTime" )),
                    (rst.getString( "CompletionDate" ) != null) ? LocalDate.parse( rst.getString( "CompletionDate" ) ):null,
                    (rst.getString( "CompletionTime" ) != null) ? LocalTime.parse( rst.getString( "CompletionTime" ) ) : null,
                    rst.getBoolean( "Milestone" ));
        }
        con.close();
        return task;
    }

    //get a particular task
    public static Task getTaskByTitleAndProjectId(String title, String projectId) throws IOException, SQLException {
        Task task = null;
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks WHERE Title ='"+title+"' AND ProjectId ='"+projectId+"'  ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()){
            task = new Task( String.valueOf(rst.getInt( "TaskId" )),
                    rst.getString( "ProjectId"), rst.getString( "Title" ),rst.getString( "Description" ), rst.getInt(  "Duration"),
                    LocalDate.parse( rst.getString( "BeginDate" )), LocalTime.parse( rst.getString( "BeginTime" ) ),
                    LocalDate.parse( rst.getString( "DeadlineDate" )),LocalTime.parse( rst.getString( "DeadlineTime" )),
                    (rst.getString( "CompletionDate" ) != null) ? LocalDate.parse( rst.getString( "CompletionDate" ) ):null,
                    (rst.getString( "CompletionTime" ) != null) ? LocalTime.parse( rst.getString( "CompletionTime" ) ) : null,
                    rst.getBoolean( "Milestone" ));
        }
        con.close();
        return task;
    }

    //get a particular task
    public static Task getLastTask() throws IOException, SQLException {
        Task task = null;
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks ORDER BY TaskId DESC LIMIT 1 ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()){
            task = new Task( String.valueOf(rst.getInt( "TaskId" )),
                    rst.getString( "ProjectId"), rst.getString( "Title" ),rst.getString( "Description" ), rst.getInt( "Duration" ),
                    LocalDate.parse( rst.getString( "BeginDate" )), LocalTime.parse( rst.getString( "BeginTime" ) ),
                    LocalDate.parse( rst.getString( "DeadlineDate" )),LocalTime.parse( rst.getString( "DeadlineTime" )),
                    (rst.getString( "CompletionDate" ) != null) ? LocalDate.parse( rst.getString( "CompletionDate" ) ):null,
                    (rst.getString( "CompletionTime" ) != null) ? LocalTime.parse( rst.getString( "CompletionTime" ) ) : null,
                    rst.getBoolean( "Milestone" ));
        }
        con.close();
        return task;
    }

    //get a particular task
    public static Task getLastTask(String projectId) throws IOException, SQLException {
        Task task = null;
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks WHERE ProjectId = '" + projectId + "' ORDER BY TaskId DESC LIMIT 1 ";
        assert con != null;
        ResultSet rst = con.prepareStatement(query).executeQuery();
        while (rst.next()) {
            task = new Task(String.valueOf(rst.getInt("TaskId")),
                    rst.getString("ProjectId"), rst.getString("Title"), rst.getString("Description"), rst.getInt("Duration"),
                    LocalDate.parse(rst.getString("BeginDate")), LocalTime.parse(rst.getString("BeginTime")),
                    LocalDate.parse(rst.getString("DeadlineDate")), LocalTime.parse(rst.getString("DeadlineTime")),
                    (rst.getString("CompletionDate") != null) ? LocalDate.parse(rst.getString("CompletionDate")) : null,
                    (rst.getString("CompletionTime") != null) ? LocalTime.parse(rst.getString("CompletionTime")) : null,
                    rst.getBoolean("Milestone"));
        }
        con.close();
        return task;
    }

    //get all tasks
    public static ObservableList<Task> getAllTasks() throws IOException, SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()){
            tasks.add(new Task( String.valueOf(rst.getInt( "TaskId" )),
                    rst.getString( "ProjectId"), rst.getString( "Title" ), rst.getString( "Description" ), rst.getInt( "Duration" ),
                    LocalDate.parse( rst.getString( "BeginDate" )), LocalTime.parse( rst.getString( "BeginTime" ) ),
                    LocalDate.parse( rst.getString( "DeadlineDate" )),LocalTime.parse( rst.getString( "DeadlineTime" )),
                    (rst.getString( "CompletionDate" ) != null) ? LocalDate.parse( rst.getString( "CompletionDate" ) ):null,
                    (rst.getString( "CompletionTime" ) != null) ? LocalTime.parse( rst.getString( "CompletionTime" ) ):null,
                    rst.getBoolean( "Milestone" )));
        }
        con.close();
        return tasks;
    }

    //get all tasks
    public static ObservableList<Task> getAllTasks(String projectId) throws IOException, SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks WHERE TaskId ='" + projectId + "' ";
        assert con != null;
        ResultSet rst = con.prepareStatement(query).executeQuery();
        while (rst.next()) {
            tasks.add(new Task(String.valueOf(rst.getInt("TaskId")),
                    rst.getString("ProjectId"), rst.getString("Title"), rst.getString("Description"), rst.getInt("Duration"),
                    LocalDate.parse(rst.getString("BeginDate")), LocalTime.parse(rst.getString("BeginTime")),
                    LocalDate.parse(rst.getString("DeadlineDate")), LocalTime.parse(rst.getString("DeadlineTime")),
                    (rst.getString("CompletionDate") != null) ? LocalDate.parse(rst.getString("CompletionDate")) : null,
                    (rst.getString("CompletionTime") != null) ? LocalTime.parse(rst.getString("CompletionTime")) : null,
                    rst.getBoolean("Milestone")));
        }
        con.close();
        return tasks;
    }

    //get all unfinished tasks in project
    public static ObservableList<Task> getUnfinishedTasks(String projectId) throws IOException, SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        Connection con = DbConfig.Connector();
        String query = "SELECT * FROM Tasks WHERE ProjectId = ? ";
        assert con != null;
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, projectId);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            tasks.add(new Task(String.valueOf(rst.getInt("TaskId")),
                    rst.getString("ProjectId"), rst.getString("Title"), rst.getString("Description"), rst.getInt("Duration"),
                    LocalDate.parse(rst.getString("BeginDate")), LocalTime.parse(rst.getString("BeginTime")),
                    LocalDate.parse(rst.getString("DeadlineDate")), LocalTime.parse(rst.getString("DeadlineTime")),
                    (rst.getString("CompletionDate") != null) ? LocalDate.parse(rst.getString("CompletionDate")) : null,
                    (rst.getString("CompletionTime") != null) ? LocalTime.parse(rst.getString("CompletionTime")) : null,
                    rst.getBoolean("Milestone")));
        }
        con.close();
        tasks.sort(Comparator.comparing(Task::getDeadlineDate));
        return tasks;
    }

    // get count of all tasks in a  project in projects Table
    public static int tasksCountInProject(String projectId) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        int taskNo = 0;
        String query = "SELECT COUNT(*) FROM Tasks WHERE ProjectId ='"+projectId+"' ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            taskNo = rst.getInt( 1 );
        }
        con.close();

        return taskNo;
    }

    // get number of completed tasks within projects
    public static int finishedTasksCountInProject(String projectId) throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        int taskNo = 0;
        String query = "SELECT COUNT(*) FROM Tasks WHERE ProjectId ='"+projectId+"' AND CompletionDate = ? ";
        assert con != null;
        PreparedStatement preparedStatement = con.prepareStatement( query );
        preparedStatement.setString( 1, null );
        ResultSet rst = preparedStatement.executeQuery();
        while (rst.next()) {
            taskNo = rst.getInt( 1 );
        }
        con.close();

        return taskNo;
    }

    // get count of all tasks in a  project in projects Table
    public static int allTasksCount() throws IOException, SQLException {
        Connection con = DbConfig.Connector();
        int taskNo = 0;
        String query = "SELECT COUNT(*) FROM Tasks  ";
        assert con != null;
        ResultSet rst = con.prepareStatement( query ).executeQuery();
        while (rst.next()) {
            taskNo = rst.getInt( 1 );
        }
        con.close();

        return taskNo;
    }


}
