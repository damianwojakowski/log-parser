package com.test;

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
        String inputValue = String.format("{\"id\":\"%s\"}", expectedId);

        logParser.parseRecord(inputValue);

        assertEquals(expectedId, logParser.getRecords().get("1").id);
    }

    @Test
    public void GivenJsonString_ParseState() {
        String expectedState = Log.STARTED_STATE;
        String inputValue = String.format("{\"id\":\"1\",\"state\":\"%s\"}", Log.STARTED_STATE);

        logParser.parseRecord(inputValue);

        assertEquals(expectedState, logParser.getRecords().get("1").state);
    }

    @Test
    public void GivenJsonString_ParseHost() {
        String expectedHost = "1234";
        String inputValue = String.format("{\"id\":\"1\",\"host\":\"%d\"}", expectedHost);

        logParser.parseRecord(inputValue);

        assertEquals(expectedHost, logParser.getRecords().get("1").host);
    }

    @Test
    public void GivenJsonString_ParseTimestamp() {
        long timestamp = 1566113940774L;
        String inputValue = String.format("{\"id\":\"1\",\"timestamp\":\"%d\"}", timestamp);

        logParser.parseRecord(inputValue);

        assertEquals(timestamp, logParser.getRecords().get("1").timestamp);
    }

    @Test
    public void GivenJsonString_ParseType() {
        String type = "APPLICATION_LOG";
        String inputValue = String.format("{\"id\":\"1\",\"type\":\"%s\"}", type);

        logParser.parseRecord(inputValue);

        assertEquals(type, logParser.getRecords().get("1").type);
    }

    @Test
    public void GivenRecordWithStartState_CacheIt() {
        String inputValue = String.format("{\"id\":\"1\",\"state\":\"%s\"}", Log.STARTED_STATE);
        int expectedSize = 1;

        logParser.parseRecord(inputValue);

        assertEquals(expectedSize, logParser.getRecords().size());
    }

    @Test
    public void GivenRecordWithFinishedState_CalculateTimeDifference() {
        long startedTimestamp = 1566113940774L;
        long finisedTimestamp = 1566113940776L;
        long timeDifference = finisedTimestamp - startedTimestamp;
        String startedEvent = String.format(
                "{\"id\":\"1\",\"state\":\"%s\",\"timestamp\":\"%d\"}",
                Log.STARTED_STATE,
                startedTimestamp
        );

        String finishedEvent = String.format(
                "{\"id\":\"1\",\"state\":\"%s\",\"timestamp\":\"%d\"}",
                Log.FINISHED_STATE,
                finisedTimestamp
        );

        logParser.parseRecord(startedEvent);
        logParser.parseRecord(finishedEvent);

        EventLog eventLog = logParser.getEventsToBeSaved().get(0);

        assertEquals(timeDifference, eventLog.duration);
    }
}