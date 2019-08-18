package com.test;

import com.test.log.Parser;
import com.test.log.Reader;

import java.io.IOException;

public class EventAnalyser {
    private Parser parser;
    private Reader reader;

    private static final int SAVE_LIMIT_AT_ONCE = 100;

    public EventAnalyser(Parser parser, Reader reader) {
        this.parser = parser;
        this.reader = reader;
    }

    public void readLogsFile(String pathToLogs) throws IOException {
        reader.loadFile(pathToLogs);
        int logsParsed = 0;

        while (reader.hasNextLine()) {
            parser.parseRecord(reader.getNextLine());
            logsParsed++;

            if (logsParsed >= SAVE_LIMIT_AT_ONCE) {
                logsParsed = 0;
                saveLogs();
            }
        }

        saveAnyLogsLeft();

        reader.closeStream();
    }

    private void saveAnyLogsLeft() {
        if (parser.getEventsToBeSaved().size() > 0) {
            saveLogs();
        }
    }

    private void saveLogs() {
        System.out.println("Saving logs at one go...");
    }

    public Parser getLogsParser() {
        return parser;
    }
}
