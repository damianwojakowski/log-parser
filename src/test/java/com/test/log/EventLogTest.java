package com.test.log;

import org.junit.After;
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

        assertTrue(eventLog.isAlert());
    }

    @Test
    public void GivenStartAndFinishedLogWithLessThan4MillisecondGap_ShouldSetAlertAsFalse() {
        Log startLog = createStartLog();
        Log finishLog = createFinishLog(2);

        eventLog = new EventLog(startLog, finishLog);

        assertFalse(eventLog.isAlert());
    }

    @Test
    public void GivenLogs_ShouldSaveLogId() {
        int duration = 2;
        String logId = "someId";
        Log startLog = createStartLog();
        startLog.id =  logId;

        Log finishLog = createFinishLog(duration);
        finishLog.id = logId;

        eventLog = new EventLog(startLog, finishLog);

        assertEquals(logId, eventLog.getId());
    }

    @Test
    public void GivenLogs_ShouldSaveDurationTime() {
        int duration = 2;
        Log startLog = createStartLog();
        Log finishLog = createFinishLog(duration);

        eventLog = new EventLog(startLog, finishLog);

        assertEquals(duration, eventLog.getDuration());
    }

    @Test
    public void GivenLogs_ShouldSaveHost() {
        int duration = 5;
        String host = "someHost";
        Log startLog = createStartLog();
        startLog.host = host;

        Log finishLog = createFinishLog(duration);
        finishLog.host = host;

        eventLog = new EventLog(startLog, finishLog);

        assertEquals(host, eventLog.getHost());
    }

    @Test
    public void GivenLogs_ShouldSaveType() {
        int duration = 5;
        String type = "APPLICATION_LOG_2";
        Log startLog = createStartLog();
        startLog.type = type;

        Log finishLog = createFinishLog(duration);
        finishLog.type = type;

        eventLog = new EventLog(startLog, finishLog);

        assertEquals(type, eventLog.getType());
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