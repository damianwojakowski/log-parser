package com.test;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser {
    Gson json = new Gson();
    Map<String, Log> records = new HashMap<String, Log>();
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

    private void calculateTimeDifference(Log log) {
        if (records.containsKey(log.id)) {
            Log startedState = records.get(log.id);
            long timeDifference = log.timestamp - startedState.timestamp;

            EventLog eventLog = new EventLog();
            if (timeDifference > 4) {
                eventLog.alert = true;
            } else {
                eventLog.alert = false;
            }

            eventLog.duration = timeDifference;
            eventLog.id = log.id;
            eventLog.host = log.host;
            eventLog.type = log.type;

            eventsToBeSaved.add(eventLog);

            records.remove(log.id);
        } else {
            //TODO: What when there is FINISHED state without STARTED state?
            cacheRecord(log);
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
