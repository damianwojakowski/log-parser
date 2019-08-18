package com.test;

import com.test.log.EventLogSaver;
import com.test.log.LogParser;
import com.test.log.LogReader;
import com.test.log.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class EventAnalyserIntegrationTest {

    private static final String testLogsFilename = "logs";
    EventAnalyser eventAnalyser;
    String databasePath = "/src/test/resources/db/";
    String databaseName = "eventlogdb";

    @Before
    public void setUp() throws Exception {
        eventAnalyser = new EventAnalyser(new LogParser(), new LogReader(), new EventLogSaver());
    }

    @After
    public void tearDown() throws Exception {
        eventAnalyser = null;
    }

    @Test
    public void givenFilePath_readsLogsAndCreatesEventLog() throws IOException, SQLException {
        int expectedEventLogsToBeSaved = 2;

        eventAnalyser.readLogsFile(getPathToTestLogs(), getPathToDatabase());
        Parser logsParser = eventAnalyser.getLogsParser();

        assertEquals(expectedEventLogsToBeSaved, logsParser.getEventsToBeSaved().size());
    }

    private String getPathToTestLogs() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(testLogsFilename).getFile());
        return file.getAbsolutePath();
    }

    private String getPathToDatabase() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = new File(".").getAbsolutePath();
        return path + databasePath + databaseName;
    }

}