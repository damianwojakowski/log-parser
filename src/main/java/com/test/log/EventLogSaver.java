package com.test.log;

import java.sql.*;
import java.util.List;

public class EventLogSaver implements Saver {
    Connection connection;
    Statement stmt = null;

    public void connect(String databasePath) throws SQLException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(String.format("jdbc:hsqldb:file:%s", databasePath), "SA", "");
            stmt = connection.createStatement();

            ResultSet result = stmt.executeQuery("SELECT * FROM EVENT_LOGS");
        } catch (Exception e) {
            try {
                System.out.println("Creating Table...");

            int result = stmt.executeUpdate("CREATE TABLE EVENT_LOGS (" +
                    "id VARCHAR(50) NOT NULL, duration INT NOT NULL, " +
                    "type VARCHAR(50), host VARCHAR(50), alert BIT NOT NULL, " +
                    "PRIMARY KEY (id));");
                connection.commit();
            } catch (Exception e2) {
                System.out.println();
                connection.close();
            }
        }
    }

    public void save(List<EventLog> eventLogsList) {
        eventLogsList.forEach(eventLog -> {
            try {
                String INSERT_RECORD = "insert into EVENT_LOGS(id, duration, type, host, alert) values(?, ?, ?, ?, ?)";

                PreparedStatement pstmt = connection.prepareStatement(INSERT_RECORD);
                pstmt.setString(1, eventLog.getId());
                pstmt.setInt(2, eventLog.getDuration());
                pstmt.setBoolean(5, eventLog.isAlert());
                pstmt.setString(3, eventLog.getType());
                pstmt.setString(4, eventLog.getHost());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
