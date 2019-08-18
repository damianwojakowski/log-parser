package com.test;

import com.test.log.LogParser;
import com.test.log.LogReader;
import com.test.log.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class EventAnalyserTest {

    private static final String testLogsFilename = "logs";
    EventAnalyser eventAnalyser;

    @Before
    public void setUp() throws Exception {
        eventAnalyser = new EventAnalyser(new LogParser(), new LogReader());
    }

    @After
    public void tearDown() throws Exception {
        eventAnalyser = null;
    }

    @Test
    public void givenFilePath_readsLogsAndCreatesEventLog() throws IOException {
        int expectedEventLogsToBeSaved = 2;

        eventAnalyser.readLogsFile(getPathToTestLogs());
        Parser logsParser = eventAnalyser.getLogsParser();

        assertEquals(expectedEventLogsToBeSaved, logsParser.getEventsToBeSaved().size());
    }

    private String getPathToTestLogs() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(testLogsFilename).getFile());
        return file.getAbsolutePath();
    }

}