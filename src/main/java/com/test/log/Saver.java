package com.test.log;

import java.sql.SQLException;
import java.util.List;

public interface Saver {
    public void save(List<EventLog> eventLogsList);
    public void closeConnection();
    public void connect(String databasePath) throws SQLException;
}
