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

    @Test
    public void GivenStartAndFinishedLogWithMoreThan4MillisecondGap_ShouldSetAlertAsTrue() {
        Log startLog = createStartLog();
        Log finishLog = createFinishLog(8);

        eventLog = new EventLog(startLog, finishLog);

        assertTrue(eventLog.alert);
    }

    @Test
    public void GivenStartAndFinishedLogWithLessThan4MillisecondGap_ShouldSetAlertAsFalse() {
        Log startLog = createStartLog();
        Log finishLog = createFinishLog(2);

        eventLog = new EventLog(startLog, finishLog);

        assertFalse(eventLog.alert);
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