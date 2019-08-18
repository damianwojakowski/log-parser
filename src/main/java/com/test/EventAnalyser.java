package com.test;

import com.test.log.Parser;
import com.test.log.Reader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventAnalyser {
    Logger logger;
    private Parser parser;
    private Reader reader;

    private static final int SAVE_LIMIT_AT_ONCE = 100;

    public EventAnalyser(Parser parser, Reader reader) {
        this.parser = parser;
        this.reader = reader;
    }

    public void readLogsFile(String pathToLogs) throws IOException {
        logger = LoggerFactory.getLogger(EventAnalyser.class);

        logger.info("Reading logs file.");
        logger.debug("Reading file: {}", pathToLogs);

        reader.loadFile(pathToLogs);
        int logsParsed = 0;

        logger.debug("File loaded.");

        while (reader.hasNextLine()) {
            parser.parseRecord(reader.getNextLine());
            logsParsed++;

            if (logsParsed >= SAVE_LIMIT_AT_ONCE) {
                logsParsed = 0;
                saveLogs();
            }
        }

        saveAnyLogsLeft();

        logger.debug("Closing I/O stream.");
        reader.closeStream();

        logger.info("Reading has finished.");
    }

    private void saveAnyLogsLeft() {
        if (parser.getEventsToBeSaved().size() > 0) {
            saveLogs();
        }
    }

    private void saveLogs() {
        logger.debug("Saving logs.");
    }

    public Parser getLogsParser() {
        return parser;
    }
}
