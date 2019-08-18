package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventLogTest {
    private EventLog eventLog;
    private final String id = "1";
    private final String type = "APPLICATION_LOG";
    private final String host = "1234";
    private final long startTimestamp = 1566113940774L;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        eventLog = null;
    }

    @Test
    public void GivenStartAndFinishedLog_CreateEventLog() {
        Log startLog = createStartLog();
        Log finishLog = createFinishLog(3);

        eventLog = new EventLog(startLog, finishLog);

        assertNotNull(eventLog);
    }

    private Log createStartLog() {
        Log startLog = new Log();
        startLog.id = id;
        startLog.timestamp = startTimestamp;
        startLog.type = type;
        startLog.host = host;
        startLog.state = Log.STARTED_STATE;

        return startLog;
    }

    private Log createFinishLog(int timeDifference) {
        Log startLog = new Log();
        startLog.id = id;
        startLog.timestamp = startTimestamp + timeDifference;
        startLog.type = type;
        startLog.host = host;
        startLog.state = Log.FINISHED_STATE;

        return startLog;
    }
}