package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class LogReaderTest {
    private LogReader logReader;
    private static final String testLogsFilename = "logs";

    @Before
    public void setUp() throws Exception {
        logReader = new LogReader();
    }

    @After
    public void tearDown() throws Exception {
        logReader = null;
    }

    @Test
    public void GivenFilePath_loadLogsFileAndReturnsWhetherCanRead() throws IOException {
        logReader.loadFile(getPathToTestLogs());

        assertTrue(logReader.hasNextLine());

        logReader.closeStream();
    }

    @Test
    public void GivenFilePath_loadReadsFirstLine() throws IOException {
        logReader.loadFile(getPathToTestLogs());

        logReader.hasNextLine();
        assertNotNull(logReader.getNextLine());

        logReader.closeStream();
    }

    private String getPathToTestLogs() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(testLogsFilename).getFile());
        return file.getAbsolutePath();
    }
}