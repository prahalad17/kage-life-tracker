package com.kage.enums;

/**
 * Defines whether an activity is something to do or avoid.
 */
public enum LogStatus {
    PENDING,    // baseline created, user has not acted yet
    DONE,       // user completed
    MISSED      // day passed, not completed
}
