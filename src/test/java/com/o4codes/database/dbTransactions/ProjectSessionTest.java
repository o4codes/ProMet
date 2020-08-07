package com.o4codes.database.dbTransactions;

import com.o4codes.models.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ProjectSessionTest {
    @Before
    public void setupProjectTable() throws IOException, SQLException {
        ProjectSession.createProjectsTable();
        System.out.println( "Projects Table created" );
    }

    @Test
    public void createCountDeleteAllProject() throws IOException, SQLException {
        Project project = new Project( "#ff9800", "LMS", "Web app to manage library books", LocalDate.now(), LocalDate.now().plusDays( 3 ) );
        assertTrue( "Project Created", ProjectSession.insertProject( project ) );
        assertEquals( "Project count matches", 1, ProjectSession.projectCount() );
        assertTrue( "Project Records Deleted", ProjectSession.deleteAllProject() );
    }

    @Test
    public void createDeleteCountProject() throws IOException, SQLException {
        Project project = new Project( "#ff9800", "LMS", "Web app to manage library books", LocalDate.now(), LocalDate.now().plusDays( 3 ) );
        Project project1 = new Project( "#ffffff", "DMS", "Web app to manage Dolea books", LocalDate.now(), LocalDate.now().plusDays( 3 ) );
        assertTrue( "Project Created", ProjectSession.insertProject( project ) );
        assertTrue( "Project Created", ProjectSession.insertProject( project1 ) );
        assertTrue( "Project Records Deleted", ProjectSession.deleteProject( project ) );
        assertEquals( "Project count matches", 1, ProjectSession.projectCount() );

    }

    @Test
    public void createUpdateDeleteProject() throws IOException, SQLException {
        Project project = new Project( "#ff9800", "LMS", "Web app to manage library books", LocalDate.now(), LocalDate.now().plusDays( 3 ) );
        assertTrue( "Project Created", ProjectSession.insertProject( project ) );
        project.setColorTheme( "#ffffff" );
        ProjectSession.updateProject( project );
        assertTrue( "Project not updated", ProjectSession.getProjectById( project.getId() ).getColorTheme().equals( project.getColorTheme() ) );
        assertTrue( "Project Records Deleted", ProjectSession.deleteProject( project ) );
    }

    @After
    public void clearRecords() throws IOException, SQLException {
        ProjectSession.deleteAllProject();
        System.out.println( "All projects are cleared from projects" );
    }
}