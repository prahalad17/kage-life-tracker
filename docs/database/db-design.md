# Life OS – Database Design Document

---

## 1. Overview

This document defines the database structure for Life OS.

The database is designed based on the core principle:

**Daily actions are the source of truth, and all higher-level constructs (plans, goals, metrics) derive meaning from them.**

The schema follows:

- normalized relational design
- modular entity grouping
- extensibility via definition tables
- strong user-level data isolation

---

## 2. Core Design Principles

### 2.1 User-Scoped Data

All entities belong to a user.

---

### 2.2 Separation of Concerns

- Execution → ActionEntry
- Planning → ActionPlan, Plan
- Measurement → Metrics
- Reflection → DayEntry, Journal

---

### 2.3 Definition-Based Extensibility

Flexible attributes and metrics use definition tables:

- DayMetricDefinition
- ActionAttributeDefinition

---

### 2.4 Historical Integrity

- Logs are stored separately (e.g., PillarMetricLog)
- Snapshot fields prevent future data corruption

---

### 2.5 Flexible Value Storage

Entities supporting dynamic values use multi-type fields:

- intValue
- decimalValue
- booleanValue
- textValue

Rule:

Only one value field must be non-null per record.

---

## 3. Entity Groups

---

### 3.1 Execution Layer

### DayEntry

Stores daily context per user.

Constraints:

- Unique (user_id, date)

---

### DayMetricDefinition

Defines allowed daily metrics.

Constraints:

- Unique (user_id, name)

---

### DayMetric

Stores metric values per day.

Constraints:

- Unique (day_entry_id, definition_id)

---

### Journal

Stores notes and reflections.

Behavior:

- Optional link to DayEntry

---

### ActionEntry

Stores actual actions performed.

Constraints:

- Must belong to DayEntry
- Must belong to User

Indexes:

- user_id, logged_at
- day_entry_id

---

### ActionEntryAttributes

Stores attribute values for actions.

Constraints:

- Unique (action_entry_id, definition_id)

---

### ActionAttributeDefinition

Defines allowed attributes.

Constraints:

- Unique (user_id, name)

---

### ActionPlan

Stores intended actions.

Rules:

- Either plannedDate OR (startDate + endDate)

---

### ActionPlanAttributes

Stores expected attributes for plans.

---

### 3.2 Life Structure Layer

### Pillar

Represents life domains.

Constraints:

- Unique (user_id, name)

---

### Activity

Defines types of actions.

Constraints:

- Unique (user_id, pillar_id, name)

---

### ActivitySchedule

Defines recurrence rules.

Rules:

- One schedule per activity
- CUSTOM type requires days

---

### PillarMetric

Defines measurable outcomes.

---

### PillarMetricLog

Stores metric values over time.

Constraints:

- Unique (pillar_metric_id, logged_date)

---

### 3.3 Goal Layer

### Goal

Represents desired outcomes.

Optional relations:

- Pillar
- PillarMetric

---

### GoalProgress

Tracks progress over time.

Constraints:

- Unique (goal_id, date)

---

### 3.4 Planning Layer

### Plan

Represents time-bound planning window.

Constraints:

- startDate ≤ endDate

---

### PlanItem

Represents measurable targets.

Types:

- ACTIVITY
- METRIC
- MILESTONE

Rules:

- ACTIVITY → requires activity
- METRIC → requires pillarMetric
- targetValue required for measurable types

---

## 4. Key Relationships

- User → owns all entities
- DayEntry → central to daily tracking
- ActionEntry → must belong to DayEntry
- Activity → optional reference in ActionEntry
- ActionPlan → optional reference in ActionEntry
- Pillar → groups activities and metrics
- Plan → groups PlanItems
- Goal → independent but may link to metrics

---

## 5. Data Integrity Rules

- Only one value field must be non-null in flexible entities
- ActionEntry.user must match DayEntry.user
- loggedAt must fall within DayEntry.date
- PlanItem must follow type-based constraints
- ActivitySchedule rules must be enforced

---

## 6. Indexing Strategy

Indexes are created for:

- user-based queries
- time-based queries
- relationship lookups

Examples:

- action_entry(user_id, logged_at)
- day_entry(user_id, date)
- pillar_metric_log(pillar_metric_id, logged_date)

---

## 7. Scalability Considerations

- Definition tables allow dynamic extensibility
- Logs are separated for time-series queries
- Snapshot fields prevent cascading updates
- Loose coupling between modules ensures flexibility

---

## 8. Summary

The database is designed to:

- capture real-world behavior accurately
- support flexible tracking
- enable long-term analytics
- maintain data integrity and scalability

---

# Life OS – Complete Database Design Specification (V1)

---

## 1. Overview

This document defines the complete database structure for Life OS.

The system is designed around the principle:

**Daily actions are the source of truth.**

All other entities (plans, goals, metrics) are evaluated based on actions and logs.

The database follows:

- normalized relational design
- user-scoped data ownership
- extensibility via definition tables
- separation of execution, planning, and measurement

---

## 2. Global Conventions

### 2.1 BaseEntity

All tables include:

- id (PK)
- created_at
- updated_at

---

### 2.2 User Ownership

All entities are scoped to a user either:

- directly (user_id)
- or indirectly via parent entity

---

### 2.3 Flexible Value Pattern

Used in:

- DayMetric
- ActionEntryAttributes
- GoalProgress

Fields:

- int_value
- decimal_value
- boolean_value
- text_value

Rule:

Exactly ONE must be non-null.

---

---

# 3. EXECUTION LAYER

---

## 3.1 DayEntry

### Purpose

Represents a single day for a user.

### Table: day_entry

| Column | Type | Constraints |
| --- | --- | --- |
| id | PK |  |
| user_id | FK → user | NOT NULL |
| date | DATE | NOT NULL |
| mood | ENUM | NOT NULL |
| day_score | INT | NULL |
| day_status | ENUM | NOT NULL |

### Constraints

- UNIQUE (user_id, date)

### Relationships

- One → Many → DayMetric
- One → Many → ActionEntry
- One → Many → Journal

---

## 3.2 DayMetricDefinition

### Purpose

Defines allowed daily metrics.

### Table: day_metric_definition

| Column | Type | Constraints |
| --- | --- | --- |
| id | PK |  |
| user_id | FK | NOT NULL |
| name | VARCHAR | NOT NULL |
| type | ENUM | NOT NULL |
| unit | VARCHAR |  |
| description | VARCHAR |  |

### Constraints

- UNIQUE (user_id, name)

---

## 3.3 DayMetric

### Purpose

Stores metric values per day.

### Table: day_metric

| Column | Type |
| --- | --- |
| id | PK |
| day_entry_id | FK |
| definition_id | FK |
| int_value | INT |
| decimal_value | DECIMAL |
| boolean_value | BOOLEAN |
| text_value | TEXT |

### Constraints

- UNIQUE (day_entry_id, definition_id)

---

## 3.4 Journal

### Purpose

Stores notes (daily or independent).

### Table: journal

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| day_entry_id | FK (optional) |
| title | VARCHAR |
| content | TEXT |

---

## 3.5 ActionEntry

### Purpose

Stores actual actions (core entity).

### Table: action_entry

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| day_entry_id | FK |
| activity_id | FK |
| action_plan_id | FK |
| logged_at | TIMESTAMP |
| action_name | VARCHAR |
| action_status | ENUM |
| nature | ENUM |
| tracking_type | ENUM |
| notes | TEXT |

### Rules

- Must belong to DayEntry
- user_id must match DayEntry.user_id
- logged_at must fall within DayEntry.date

---

## 3.6 ActionAttributeDefinition

### Purpose

Defines allowed attributes.

### Table: action_attribute_definition

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| name | VARCHAR |
| type | ENUM |
| unit | VARCHAR |
| description | VARCHAR |

### Constraints

- UNIQUE (user_id, name)

---

## 3.7 ActionEntryAttributes

### Purpose

Stores attribute values.

### Table: action_entry_attributes

| Column | Type |
| --- | --- |
| id | PK |
| action_entry_id | FK |
| definition_id | FK |
| int_value | INT |
| decimal_value | DECIMAL |
| boolean_value | BOOLEAN |
| text_value | TEXT |

### Constraints

- UNIQUE (action_entry_id, definition_id)

---

## 3.8 ActionPlan

### Purpose

Represents intended actions.

### Table: action_plan

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| activity_id | FK |
| pillar_id | FK |
| planned_date | DATE |
| start_date | DATETIME |
| end_date | DATETIME |
| name | VARCHAR |
| source | ENUM |
| status | ENUM |
| nature | ENUM |
| tracking_type | ENUM |
| notes | TEXT |

### Rules

- planned_date XOR (start_date + end_date)

---

## 3.9 ActionPlanAttributes

Same structure as ActionEntryAttributes

---

# 4. LIFE STRUCTURE LAYER

---

## 4.1 Pillar

### Purpose

Life domains.

### Table: pillar

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| name | VARCHAR |
| description | VARCHAR |
| priority_weight | INT |
| order_index | INT |
| color | VARCHAR |

### Constraints

- UNIQUE (user_id, name)

---

## 4.2 Activity

### Purpose

Defines action types.

### Table: activity

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| pillar_id | FK |
| name | VARCHAR |
| nature | ENUM |
| tracking_type | ENUM |
| activity_type | ENUM |
| unit | VARCHAR |
| description | VARCHAR |

---

## 4.3 ActivitySchedule

### Purpose

Defines recurrence.

### Table: activity_schedule

| Column | Type |
| --- | --- |
| id | PK |
| activity_id | FK |
| type | ENUM |

---

## 4.4 ActivityScheduleDays

| Column | Type |
| --- | --- |
| schedule_id | FK |
| day | ENUM |

---

## 4.5 PillarMetric

### Purpose

Defines measurable outcomes.

### Table: pillar_metric

| Column | Type |
| --- | --- |
| id | PK |
| pillar_id | FK |
| type | ENUM |
| target_value | DOUBLE |
| description | VARCHAR |

---

## 4.6 PillarMetricLog

### Purpose

Stores metric values over time.

### Table: pillar_metric_log

| Column | Type |
| --- | --- |
| id | PK |
| pillar_metric_id | FK |
| logged_date | DATE |
| value | DOUBLE |
| notes | VARCHAR |

### Constraints

- UNIQUE (pillar_metric_id, logged_date)

---

# 5. GOAL LAYER

---

## 5.1 Goal

### Table: goal

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| pillar_id | FK |
| pillar_metric_id | FK |
| title | VARCHAR |
| description | VARCHAR |
| goal_type | ENUM |
| status | ENUM |
| start_date | DATE |
| target_date | DATE |

---

## 5.2 GoalProgress

### Table: goal_progress

| Column | Type |
| --- | --- |
| id | PK |
| goal_id | FK |
| date | DATE |
| progress_percent | DECIMAL |
| int_value | INT |
| decimal_value | DECIMAL |
| boolean_value | BOOLEAN |
| text_value | TEXT |
| notes | VARCHAR |

### Constraints

- UNIQUE (goal_id, date)

---

# 6. PLANNING LAYER

---

## 6.1 Plan

### Table: plan

| Column | Type |
| --- | --- |
| id | PK |
| user_id | FK |
| title | VARCHAR |
| description | VARCHAR |
| start_date | DATE |
| end_date | DATE |

---

## 6.2 PlanItem

### Table: plan_item

| Column | Type |
| --- | --- |
| id | PK |
| plan_id | FK |
| pillar_id | FK |
| activity_id | FK |
| pillar_metric_id | FK |
| type | ENUM |
| title | VARCHAR |
| description | VARCHAR |
| target_value | DOUBLE |
| start_date | DATE |
| end_date | DATE |

### Rules

- ACTIVITY → activity required
- METRIC → pillar_metric required
- target_value required for measurable types

---

## 7. Summary

The database enables:

- tracking real-world behavior (actions)
- structuring life (pillars, activities)
- measuring outcomes (metrics)
- planning future (plans, goals)
- maintaining historical logs