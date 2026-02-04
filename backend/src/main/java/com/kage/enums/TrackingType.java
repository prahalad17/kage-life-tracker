package com.kage.enums;

/**
 * Defines how an activity is tracked.
 */
public enum TrackingType {
    BOOLEAN,    // did / didn't
    COUNT,      // reps, pages, etc.
    DURATION,   // minutes, seconds
    STREAK,     // repeated streak logic
    AVOIDANCE   // similar to BOOLEAN but flagged as negative habit
}
