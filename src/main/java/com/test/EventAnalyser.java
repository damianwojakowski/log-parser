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
        int counter = 0;

        while (reader.hasNextLine()) {
            parser.parseRecord(reader.getNextLine());
            counter++;

            if (counter >= SAVE_LIMIT_AT_ONCE) {
                counter = 0;
                System.out.println("Saving logs at one go...");
            }
        }

        reader.closeStream();
    }

    public Parser getLogsParser() {
        return parser;
    }
}
