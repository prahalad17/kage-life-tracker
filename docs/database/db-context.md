# Life OS – Database AI Context (V1)

## 1. Purpose

This document provides complete database understanding for Life OS.

AI should use this as the source of truth for:

- queries
- entity logic
- relationships
- validations

---

## 2. Core Principle

Actions are the source of truth.

All evaluations (plans, goals, analytics) depend on:

- ActionEntry
- Metric logs

---

## 3. Data Model Summary

### Execution

- DayEntry → daily context
- ActionEntry → actual behavior
- Journal → notes

---

### Measurement

- DayMetric → daily metrics
- PillarMetric → definitions
- PillarMetricLog → time-series values

---

### Planning

- ActionPlan → intended actions
- Plan → time windows
- PlanItem → measurable targets

---

### Structure

- Pillar → life domains
- Activity → standardized actions
- ActivitySchedule → recurrence rules

---

### Goals

- Goal → outcomes
- GoalProgress → tracking

---

## 4. Key Relationships

- All data belongs to a user
- ActionEntry → must belong to DayEntry
- PlanItem → evaluated using actions/metrics
- Metric logs → source of historical data

---

## 5. Important Rules

- Plans do NOT store execution
- Metrics do NOT store current value (logs do)
- Only one flexible value field must be used
- All queries must be user-scoped

---

## 6. Querying Strategy

### Actions

- Filter by user + date range
- Join with Activity

---

### Metrics

- Use logs for trends
- Do NOT compute from definitions

---

### Plans

- Activity targets → count ActionEntry
- Metric targets → use PillarMetricLog

---

## 7. Performance Guidelines

- Use indexes on user_id and date fields
- Avoid N+1 queries
- Use projections for large queries

---

## 8. Expected AI Behavior

- Respect all constraints
- Follow normalized design
- Maintain data integrity
- Do not introduce denormalization unless explicitly required

---

This document fully defines the database understanding of the system.