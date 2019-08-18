package com.test;

public class EventLog {
    String id;
    String type;
    String host;
    long duration;
    boolean alert;

    public EventLog(Log startLog, Log finishLog) {
        duration = finishLog.timestamp - startLog.timestamp;
        setDurationAndAlert(duration);

        id = startLog.id;
        type = startLog.type != null && !startLog.type.isEmpty() ? startLog.type : finishLog.type;
        host = startLog.host != null && !startLog.host.isEmpty() ? startLog.host : finishLog.host;
    }

    private void setDurationAndAlert(long duration) {
        this.duration = duration;

        if (duration > 4) {
            alert = true;
        } else {
            alert = false;
        }
    }
}
