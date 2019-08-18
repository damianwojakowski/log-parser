package com.test;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser {
    Gson json = new Gson();
    Map<String, Log> records = new HashMap<>();
    List<EventLog> eventsToBeSaved = new ArrayList<>();

    public void parseRecord(String recordAsJson) {
        try {
            Log log = json.fromJson(recordAsJson, Log.class);

            if (log.state.equals(Log.FINISHED_STATE)) {
                calculateTimeDifference(log);
            } else {
                cacheRecord(log);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void calculateTimeDifference(Log finishedEvent) {
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
