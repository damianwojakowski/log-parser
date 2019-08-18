package com.test;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class LogReaderTest {
    LogReader logReader;

    @Before
    public void setUp() throws Exception {
        logReader = new LogReader();
    }

    @After
    public void tearDown() throws Exception {
        logReader = null;
    }


}