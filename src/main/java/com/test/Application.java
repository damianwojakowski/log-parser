package com.test;

import com.test.log.LogParser;
import com.test.log.LogReader;

public class Application {
    public static void main(String[] args) {
        try {
            EventAnalyser eventAnalyser = new EventAnalyser(new LogParser(), new LogReader());
            eventAnalyser.readLogsFile(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
