package com.test.log;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser implements Parser {
    private Gson json;
    private Map<String, Log> records;
    private List<EventLog> eventsToBeSaved;

    public LogParser() {
        json = new Gson();
        records = new HashMap<>();
        eventsToBeSaved = new ArrayList<>();
    }

    public void parseRecord(String recordAsJson) {
        try {
            Log log = json.fromJson(recordAsJson, Log.class);

            if (log.state.equals(Log.FINISHED_STATE)) {
                lookForStartedEventAndMoveToSavingQueue(log);
            } else {
                cacheRecord(log);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void lookForStartedEventAndMoveToSavingQueue(Log finishedEvent) {
        if (records.containsKey(finishedEvent.id)) {
            Log startedEvent = records.get(finishedEvent.id);

            EventLog eventLog = new EventLog(startedEvent, finishedEvent);

            eventsToBeSaved.add(eventLog);
            records.remove(finishedEvent.id);
        } else {
            //TODO: What when there is FINISHED state without STARTED state?
            cacheRecord(finishedEvent);
        }
    }

    private void cacheRecord(Log log) {
        records.put(log.id, log);
    }

    public Map<String, Log> getRecords() {
        return records;
    }

    public List<EventLog> getEventsToBeSaved() {
        return eventsToBeSaved;
    }
}
