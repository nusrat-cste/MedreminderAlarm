package com.example.nusra.medreminder;

import java.io.Serializable;

public class AnAlarmTask implements Serializable {
    String alarmTaskName;
    int alarmTaskId;
    String alarmFrequency;
    String alarmDays;
    String alarmSetDate;
    int alarmSetHour;
    int alarmSetMinutes;
    int alarmSetSeconds;
    int alarmSetMilliSec;


    public AnAlarmTask() {
    }

    public AnAlarmTask(String alarmTaskName, int alarmTaskId, String alarmFrequency, String alarmDays, String alarmSetDate, int alarmSetHour, int alarmSetMinutes) {
        this.alarmTaskName = alarmTaskName;
        this.alarmTaskId = alarmTaskId;
        this.alarmFrequency = alarmFrequency;
        this.alarmDays = alarmDays;
        this.alarmSetDate = alarmSetDate;
        this.alarmSetHour = alarmSetHour;
        this.alarmSetMinutes = alarmSetMinutes;
        this.alarmSetSeconds = 0;
        this.alarmSetMilliSec = 0;
    }

    public String getAlarmTaskName() {
        return alarmTaskName;
    }

    public int getAlarmTaskId() {
        return alarmTaskId;
    }

    public String getAlarmFrequency() {
        return alarmFrequency;
    }

    public String getAlarmDays() {
        return alarmDays;
    }

    public String getAlarmSetDate() {
        return alarmSetDate;
    }

    public int getAlarmSetHour() {
        return alarmSetHour;
    }

    public int getAlarmSetSeconds() {
        return alarmSetSeconds;
    }

    public int getAlarmSetMilliSec() {
        return alarmSetMilliSec;
    }

    public int getAlarmSetMinutes() {
        return alarmSetMinutes;
    }

    public void setAlarmTaskName(String alarmTaskName) {
        this.alarmTaskName = alarmTaskName;
    }

    public void setAlarmTaskId(int alarmTaskId) {
        this.alarmTaskId = alarmTaskId;
    }

    public void setAlarmFrequency(String alarmFrequency) {
        this.alarmFrequency = alarmFrequency;
    }

    public void setAlarmDays(String alarmDays) {
        this.alarmDays = alarmDays;
    }

    public void setAlarmSetDate(String alarmSetDate) {
        this.alarmSetDate = alarmSetDate;
    }

    public void setAlarmSetHour(int alarmSetHour) {
        this.alarmSetHour = alarmSetHour;
    }

    public void setAlarmSetMinutes(int alarmSetMinutes) {
        this.alarmSetMinutes = alarmSetMinutes;
    }

    public void setAlarmSetSeconds(int alarmSetSeconds) {
        this.alarmSetSeconds = alarmSetSeconds;
    }

    public void setAlarmSetMillis(int alarmSetMilliSec) {
        this.alarmSetMilliSec = alarmSetMilliSec;
    }
}
