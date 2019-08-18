package com.test.log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventLogSaverTest {
    EventLogSaver eventLogSaver;
    String databasePath = "/src/test/resources/db/";
    String databaseName = "eventlogdb";

    @Before
    public void setUp() throws Exception {
        eventLogSaver = new EventLogSaver();
    }

    @After
    public void tearDown() throws Exception {
        eventLogSaver = null;
        removeDatabase();
    }

    @Test
    public void connectToDatabase() throws SQLException {
        eventLogSaver.connect(getPathToDatabase());
    }

    @Test
    public void saveAndReadLog() throws SQLException {
        eventLogSaver.connect(getPathToDatabase());
        eventLogSaver.save(generateEventLogsList());
        eventLogSaver.closeConnection();
    }

    private List<EventLog> generateEventLogsList() {
        List<EventLog> eventLogs = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            eventLogs.add(generateEventLog(Integer.toString(i)));
        }

        return eventLogs;
    }

    private EventLog generateEventLog(String id) {
        return new EventLog(createStartLog(id), createFinishLog(id));
    }

    private Log createStartLog(String id) {
        return createLog(id, 22222222L, "type", "host", Log.STARTED_STATE);
    }

    private Log createFinishLog(String id) {
        return createLog(id, 22222226L, "type", "host", Log.FINISHED_STATE);
    }

    private Log createLog(String id, long timestamp, String type, String host, String state) {
        Log log = new Log();
        log.id = id;
        log.timestamp = timestamp;
        log.type = type;
        log.host = host;
        log.state = Log.STARTED_STATE;

        return log;
    }

    private String getPathToDatabase() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = new File(".").getAbsolutePath();
        return path + databasePath + databaseName;
    }

    private void removeDatabase() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = new File(".").getAbsolutePath() + databasePath;

        Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }
}