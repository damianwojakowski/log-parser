package com.test;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogParser {
    Log log;

    Gson json = new Gson();
    Map<String, Log> records = new HashMap<String, Log>();
    List<EventLog> eventsToBeSaved;

    public void parseRecord(String recordAsJson) {
        Log log = json.fromJson(recordAsJson, Log.class);

        if (log.state.equals(Log.FINISHED_STATE)) {
            calculateTimeDifference(log);
        } else {
            cacheRecord(log);
        }
    }

    private void calculateTimeDifference(Log log) {
        if (records.containsKey(log.id)) {
            Log startedState = records.get(log.id);
            long timeDifference = log.timestamp - startedState.timestamp;

            EventLog eventLog = new EventLog();
            if (timeDifference > 4) {
                eventLog.alert = true;
            }

            eventLog.duration = timeDifference;
            eventLog.id = log.id;
            eventLog.host = log.host;
            eventLog.type = log.type;

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
}
