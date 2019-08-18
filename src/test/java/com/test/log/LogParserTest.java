package com.test.log;

import com.test.log.EventLog;
import com.test.log.Log;
import com.test.log.LogParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogParserTest {

    LogParser logParser;

    @Before
    public void setUp() throws Exception {
        logParser = new LogParser();
    }

    @After
    public void tearDown() throws Exception {
        logParser = null;
    }

    @Test
    public void GivenJsonString_ParseId() {
        String expectedId = "1";
        String inputValue = generateEventString(expectedId, Log.STARTED_STATE, 23423L);

        logParser.parseRecord(inputValue);

        assertEquals(expectedId, logParser.getRecords().get("1").id);
    }

    @Test
    public void GivenJsonString_ParseState() {
        String expectedState = Log.STARTED_STATE;
        String inputValue = generateEventString("1", Log.STARTED_STATE, 23423L);

        logParser.parseRecord(inputValue);

        assertEquals(expectedState, logParser.getRecords().get("1").state);
    }

    @Test
    public void GivenJsonString_ParseHost() {
        String expectedHost = "1234";
        String inputValue = String.format("{\"id\":\"1\",\"host\":\"%s\",\"state\":\"STARTED\"}", expectedHost);

        logParser.parseRecord(inputValue);

        assertEquals(expectedHost, logParser.getRecords().get("1").host);
    }

    @Test
    public void GivenJsonString_ParseTimestamp() {
        long timestamp = 1566113940774L;
        String inputValue = String.format("{\"id\":\"1\",\"timestamp\":\"%d\",\"state\":\"STARTED\"}", timestamp);

        logParser.parseRecord(inputValue);

        assertEquals(timestamp, logParser.getRecords().get("1").timestamp);
    }

    @Test
    public void GivenJsonString_ParseType() {
        String type = "APPLICATION_LOG";
        String inputValue = String.format("{\"id\":\"1\",\"type\":\"%s\",\"state\":\"STARTED\"}", type);

        logParser.parseRecord(inputValue);

        assertEquals(type, logParser.getRecords().get("1").type);
    }

    @Test
    public void GivenRecordWithStartState_CacheIt() {
        String inputValue = generateEventString("1", Log.STARTED_STATE, 12312L);
        int expectedSize = 1;

        logParser.parseRecord(inputValue);

        assertEquals(expectedSize, logParser.getRecords().size());
    }

    @Test
    public void GivenRecordWithFinishedState_CalculateTimeDifferenceAndCacheEventLogInToBeSavedList() {
        String id = "1";
        long startedTimestamp = 1566113940774L;
        long finishedTimestamp = 1566113940776L;
        long timeDifference = finishedTimestamp - startedTimestamp;
        String startedEvent = generateEventString(id, Log.STARTED_STATE, startedTimestamp);
        String finishedEvent = generateEventString(id, Log.FINISHED_STATE, finishedTimestamp);

        logParser.parseRecord(startedEvent);
        logParser.parseRecord(finishedEvent);

        EventLog eventLog = logParser.getEventsToBeSaved().get(0);

        assertEquals(timeDifference, eventLog.getDuration());
    }

    @Test
    public void GivenRecordWithStartAndFinishedState_RemoveLogsFromCachedList() {
        int expectedNumberOfRecords = 0;
        String id = "1";
        long startedTimestamp = 1566113940774L;
        long finishedTimestamp = 1566113940776L;
        String startedEvent = generateEventString(id, Log.STARTED_STATE, startedTimestamp);
        String finishedEvent = generateEventString(id, Log.FINISHED_STATE, finishedTimestamp);

        logParser.parseRecord(startedEvent);
        logParser.parseRecord(finishedEvent);

        assertEquals(expectedNumberOfRecords, logParser.getRecords().size());
    }

    @Test
    public void GivenRecords_EventLogShouldContainAllInformationFromRecords() {
        String id = "asdfasfda";
        String expectedType = "APPLICATION_LOG";
        String expectedHost = "1234";
        boolean isAlert = false;
        long startedTimestamp = 1566113940774L;
        long finishedTimestamp = 1566113940776L;
        long timeDifference = finishedTimestamp - startedTimestamp;
        String startedEvent = generateCompleteEventString(id, Log.STARTED_STATE, startedTimestamp, expectedType, expectedHost);
        String finishedEvent = generateCompleteEventString(id, Log.FINISHED_STATE, finishedTimestamp, expectedType, expectedHost);

        logParser.parseRecord(startedEvent);
        logParser.parseRecord(finishedEvent);

        EventLog eventLog = logParser.getEventsToBeSaved().get(0);

        assertEquals(id, eventLog.getId());
        assertEquals(timeDifference, eventLog.getDuration());
        assertEquals(expectedHost, eventLog.getHost());
        assertEquals(expectedType, eventLog.getType());
        assertEquals(isAlert, eventLog.isAlert());
    }

    private String generateEventString(String id, String state, long timestamp) {
        return String.format("{\"id\":\"%s\",\"state\":\"%s\",\"timestamp\":\"%d\"}", id, state, timestamp);
    }

    private String generateCompleteEventString(String id, String state, long timestamp, String type, String host) {
        return String.format(
                "{\"id\":\"%s\",\"state\":\"%s\",\"timestamp\":\"%d\",\"type\":\"%s\",\"host\":\"%s\"}",
                id, state, timestamp, type, host
        );
    }
}