package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

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
        String inputValue = "{\"id\":\"1\"}";
        Log expectedValue = new Log();
        expectedValue.id = "1";

        logParser.parseRecord(inputValue);

        Assert.assertEquals(expectedValue.id, logParser.getRecords().get("1").id);
    }

    @Test
    public void GivenJsonString_ParseState() {
        String inputValue = "{\"id\":\"1\",\"state\":\"STARTED\"}";
        Log expectedValue = new Log();
        expectedValue.id = "1";
        expectedValue.state = "STARTED";

        logParser.parseRecord(inputValue);

        Assert.assertEquals(expectedValue.state, logParser.getRecords().get("1").state);
    }

    @Test
    public void GivenJsonString_ParseHost() {
        String inputValue = "{\"id\":\"1\",\"host\":\"1234\"}";
        Log expectedValue = new Log();
        expectedValue.id = "1";
        expectedValue.host = "1234";

        logParser.parseRecord(inputValue);

        Assert.assertEquals(expectedValue.host, logParser.getRecords().get("1").host);
    }

    @Test
    public void GivenJsonString_ParseTimestamp() {
        String inputValue = String.format("{\"id\":\"1\",\"timestamp\":\"1566113940774\"}");
        Log expectedValue = new Log();
        expectedValue.id = "1";
        expectedValue.timestamp = 1566113940774L;

        logParser.parseRecord(inputValue);

        Assert.assertEquals(expectedValue.timestamp, logParser.getRecords().get("1").timestamp);
    }

    @Test
    public void GivenRecordWithStartState_CacheIt() {
        String inputValue = String.format("{\"id\":\"1\",\"state\":\"%s\"}", Log.STARTED_STATE);
        int expectedSize = 1;

        logParser.parseRecord(inputValue);

        Assert.assertEquals(expectedSize, logParser.getRecords().size());
    }
}