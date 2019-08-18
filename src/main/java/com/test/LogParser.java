package com.test;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LogParser {
    Log log;

    Map<String, Log> records = new HashMap<String, Log>();

    public void parseRecord(String recordAsJson) {
        Gson json = new Gson();

        Log log = json.fromJson(recordAsJson, Log.class);

        records.put(log.id, log);
    }

    public Map<String, Log> getRecords() {
        return records;
    }
}
