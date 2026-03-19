# Life OS – Core Design Specification

---

## 1. System Vision

Life OS is a structured system designed to help users:

- Plan life direction
- Execute daily activities
- Track measurable outcomes
- Reflect on daily experiences
- Analyze long-term trends

The system is built around the idea:

**Daily actions are the fundamental building blocks of life.**

All higher-level constructs such as plans, goals, and metrics ultimately derive meaning from what the user actually does.

---

## 2. Core Philosophy

The system separates four key layers:

- **Planning** → what the user intends to do
- **Execution** → what the user actually does
- **Measurement** → how outcomes change over time
- **Reflection** → daily context and meaning

This separation ensures:

- clarity between intention and reality
- flexibility in design
- scalability for future features

---

## 3. Execution Layer (Daily Life Core)

This is the core of the system. All other layers depend on this.

---

### 3.1 DayEntry

Represents a single calendar day for a user.

**Purpose:**

- Provides daily context
- Anchors all daily data

**Contains:**

- date (unique per user)
- mood
- notes

**Behavior:**

- One DayEntry per user per date
- Acts as the parent for daily metrics and actions

---

### 3.2 DayMetricDefinition

Defines allowed daily metrics.

**Purpose:**

- Standardize measurable daily inputs
- Ensure data consistency

**Examples:**

- sleep hours
- study time
- workout duration

---

### 3.3 DayEntryMetric

Stores metric values for a specific day.

**Behavior:**

- References DayEntry
- References DayMetricDefinition
- Optional per day

---

### 3.4 Journal

Stores user notes.

**Purpose:**

- Capture thoughts, ideas, and reflections

**Behavior:**

- Can be linked to DayEntry (daily journal)
- Can exist independently (general notes)

---

### 3.5 ActionEntry

Represents an action actually performed by the user.

**Purpose:**

- Capture real behavior (source of truth)

**Examples:**

- ran 5 km
- studied for 2 hours

**Contains:**

- timestamp
- reference to DayEntry (mandatory)
- optional reference to Activity
- optional reference to ActionPlan

**Behavior:**

- Can exist independently
- Can originate from ActionPlan
- Always belongs to a day

---

### 3.6 ActionAttributeDefinition

Defines attributes applicable to actions.

**Purpose:**

- Enable flexible and extensible action tracking

**Examples:**

- duration
- difficulty
- distance

---

### 3.7 ActionEntryAttribute

Stores attribute values for an action.

**Behavior:**

- References ActionEntry
- References ActionAttributeDefinition
- Optional

---

### 3.8 ActionPlan

Represents intended actions.

**Purpose:**

- Capture user intention

**Examples:**

- run tomorrow
- meditate daily

**Behavior:**

- Can be:
    - single-day (one-time task)
    - date range
    - recurring
- Does NOT store execution data

---

### 3.9 ActionPlanAttribute

Stores expected attributes for plans.

**Purpose:**

- Define expected effort or characteristics

**Behavior:**

- References ActionAttributeDefinition
- Optional

---

## 4. Life Structure Layer

Organizes life into meaningful domains.

---

### 4.1 Pillar

Represents a major life area.

**Examples:**

- Health
- Career
- Finance

**Purpose:**

- Categorize activities and metrics

---

### 4.2 Activity

Defines a type of action.

**Examples:**

- running
- reading

**Purpose:**

- Standardize actions
- Enable aggregation and tracking
- Support scheduling

**Behavior:**

- Actions may reference Activity
- Belongs to a Pillar (optional)

---

### 4.3 ActivitySchedule

Defines recurrence rules for an activity.

**Examples:**

- daily
- weekly
- custom

**Purpose:**

- Automatically generate ActionPlans

---

### 4.4 PillarMetric

Represents measurable outcomes for a pillar.

**Examples:**

- weight (Health)
- savings (Finance)

---

### 4.5 PillarMetricLog

Stores metric values over time.

**Behavior:**

- References PillarMetric
- Contains value and date

**Purpose:**

- Track trends and long-term outcomes

---

## 5. Goal Layer

---

### 5.1 Goal

Represents a desired outcome.

**Examples:**

- lose 5 kg
- save 1 lakh

**Behavior:**

- Can link to:
    - Pillar (optional)
    - PillarMetric (optional)
- Has startDate and endDate

---

### 5.2 GoalProgress

Tracks progress toward a goal.

**Behavior:**

- References Goal
- Stores value at a given date

---

## 6. Planning Layer

---

### 6.1 Plan

Represents a time-bound planning window.

**Examples:**

- Q2 Plan
- January Plan

**Contains:**

- startDate
- endDate

---

### 6.2 PlanItem

Represents a measurable target within a plan.

**Types:**

1. **Activity Target**
    
    Example: run 50 times
    
2. **Metric Target**
    
    Example: reach 65 kg
    
3. **Milestone**
    
    Example: complete marathon
    

**Purpose:**

- Define success criteria

**Behavior:**

- Evaluated using actions and metric logs

---

## 7. System Flow

1. User defines pillars and activities
2. User defines activity schedules
3. System generates action plans
4. User can also create independent action plans
5. User performs and logs actions daily
6. User logs metrics over time
7. User creates plans and goals
8. System evaluates alignment and progress

**Feedback Loop:**

Plan → Action → Result → Reflection → Improved Plan

---

## 8. Design Principles

- Actions are the source of truth
- Plans represent intention, not execution
- Metrics represent outcomes, not effort
- Definitions ensure consistency and extensibility
- All data is user-scoped
- Execution data (actions) should remain loosely coupled from planning

---

## 9. Scope (V1)

**Included:**

- Day tracking
- Action logging
- Action planning
- Metrics tracking
- Pillars and activities
- Goals and plans

**Excluded:**

- Advanced analytics
- AI recommendations
- Complex automation

---

## 10. Summary

Life OS connects:

- what users plan
- what they actually do
- what results they achieve

By structuring this relationship, the system enables users to understand and improve their life through consistent feedback and data-driven insights.