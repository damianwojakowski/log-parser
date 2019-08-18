package com.test;

import com.test.log.LogParser;
import com.test.log.LogReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    public static void main(String[] args) {
        try {
            Logger logger = LoggerFactory.getLogger(EventAnalyser.class);

            logger.info("Starting application.");
            logger.debug("Arguments passed via console: {}", args[0]);

            EventAnalyser eventAnalyser = new EventAnalyser(new LogParser(), new LogReader());
            eventAnalyser.readLogsFile(args[0]);

            logger.info("Closing application.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
