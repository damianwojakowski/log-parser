package com.test.log;

public class EventLog {
    private String id;
    private String type;
    private String host;
    private long duration;
    private boolean alert;

    public EventLog(Log startLog, Log finishLog) {
        duration = finishLog.timestamp - startLog.timestamp;
        setDurationAndAlert(duration);

        id = startLog.id;
        type = startLog.type != null && !startLog.type.isEmpty() ? startLog.type : finishLog.type;
        host = startLog.host != null && !startLog.host.isEmpty() ? startLog.host : finishLog.host;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isAlert() {
        return alert;
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
