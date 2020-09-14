package com.o4codes.database.dbTransactions;

import com.o4codes.models.Project;
import com.o4codes.models.Task;
import com.o4codes.models.TaskTimeline;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskSessionTest {
    @Before
    public void setupProjectAndTaskTable() throws IOException, SQLException {
        ProjectSession.createProjectsTable();
        System.out.println( "Projects Table created" );
        TaskSession.createTaskTable();
        System.out.println( "Tasks Table created" );
        TaskTimelineSession.createTaskTimeLineTable();
        System.out.println( "TasksTimeLine Table created" );

    }

    @Test
    public void createProjectTaskUpdateTask() throws IOException, SQLException {
        Project project = new Project( "#ff9800", "LMS", "Web app to manage library books", LocalDate.now(), LocalDate.now().plusDays( 3 ) );
        assertTrue( "Project not Created", ProjectSession.insertProject( project ) );
        Task task = new Task( project.getId(), "Create Database","Project to test db", 2, LocalDate.now(), LocalTime.now(), LocalDate.now().plusDays( 5 ), LocalTime.now(), false );
        assertTrue( "Task not created", TaskSession.insertTask( task ) );
        assertEquals( "not equal", TaskSession.getLastTask().getProjectId(), project.getId() );
        task.setTitle( "Modify Database" );
        TaskSession.updateTask( task );
        assertEquals( "Not Equal", TaskSession.getTaskById( Integer.parseInt( task.getTaskId() ) ).getCompletionDate(), task.getCompletionDate() );
        assertTrue( "Not deleted", TaskSession.deleteTask( Integer.parseInt( task.getTaskId() ) ) );
        TaskTimeline taskTimeline = new TaskTimeline( Integer.parseInt( task.getTaskId() ), LocalDate.now(), LocalTime.now() );
        TaskTimelineSession.insertTaskTimeLine( taskTimeline );
        assertEquals( "Not equals", TaskTimelineSession.getTaskAllTimeLine().size(), 1 );
    }

    @After
    public void clearRecords() throws IOException, SQLException {
        ProjectSession.deleteAllProject();
        System.out.println( "All projects are cleared from projects" );
        TaskSession.deleteAllTasks();
        System.out.println( "All tasks set are being deleted from tasks" );
        TaskTimelineSession.removeAllTaskTimeLine();
        System.out.println( "All tasks time line history are being deleted from tasks" );
    }
}