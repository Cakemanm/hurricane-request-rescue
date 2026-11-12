package com.model;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * 
 * Represents the current stage of an assistance request.
 * <p>Each status provides a human-readable display label.</p>
 */
public enum RequestStatus {
    PENDING("Pending"),
    ASSIGNED("Assigned"),
    HELP_ON_THE_WAY("Help is on the way"),
    AT_SITE("At site"),
    BACKUP_REQUESTED("Backup requested"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    /**
     * Creates a request status with a display label.
     *
     * @param label the human-readable status label
     */
    public final String label;
    private RequestStatus(String label){
        this.label = label;
    }
}
