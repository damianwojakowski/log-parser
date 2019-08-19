package com.test.log;

import java.util.List;
import java.util.Map;

public interface Parser {
    public void parseRecord(String recordAsJson);
    public Map<String, Log> getRecords();
    public List<EventLog> getEventsToBeSaved();
    public void cleanToBeSavedList();
}
