package com.model;

public enum RequestStatus {
    PENDING("Pending"),
    ASSIGNED("Assigned"),
    HELP_ON_THE_WAY("Help is on the way"),
    AT_SITE("At site"),
    BACKUP_REQUESTED("Backup requested"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    public final String label;
    private RequestStatus(String label){
        this.label = label;
    }
}
