Life OS – Project Context Document 
1. What This Project Is
Life OS is a structured personal life management system designed to help users:
plan their life direction
execute daily activities
track measurable outcomes
reflect on daily experiences
analyze long-term trends
This is not just a task manager.
It is a behavior + outcome tracking system that connects daily actions with long-term life results.
2. Core Principle
The system is built on one central idea:
Life outcomes are the result of repeated daily actions.
Because of this, the system is designed around execution (actions) as the most important data.
3. System Philosophy
The system separates four core layers:
Planning
Represents intent (what the user plans to do)
Execution
Represents reality (what the user actually does)
Measurement
Represents outcomes (how life changes over time)
Reflection
Represents context (how each day feels and what happened)
Purpose of separation:
avoid mixing intent with reality
maintain clarity in data
enable flexible analysis
4. Core System Model
The system revolves around daily life and actions.
4.1 Day-Centric Design
Each day is a fundamental unit
All execution data is tied to a specific day
Provides context for actions and metrics
4.2 Actions as Source of Truth
Actions represent actual behavior
They are the most reliable data in the system
Plans and goals are evaluated based on actions
4.3 Plans vs Execution
Plans represent intention
Actions represent reality
The system does not assume plans are executed
4.4 Metrics as Outcomes
Metrics track results, not effort
Metrics evolve over time
Used to evaluate effectiveness of actions
4.5 Structured but Flexible Design
Definition-based entities enforce consistency
Relationships are optional where needed
Supports both structured and flexible usage
5. Core Modules (Conceptual Responsibilities)
These describe what the system does functionally.
Day Module
Handles daily context (mood, notes)
Supports optional daily metrics
Action Module
Logs actions (execution)
Supports optional attributes
Can link to plans and activities
Planning Module
Manages action plans
Supports one-time, range, and recurring plans
Activity Module
Defines action types
Supports scheduling
Enables auto-generation of plans
Pillar Module
Organizes life into domains
Groups activities and metrics
Metrics Module
Defines measurable indicators
Tracks values over time
Goal & Plan Module
Defines long-term direction
Tracks goals and plan targets
6. Core Data Model (Entity Grouping)
This defines how data is structured.
Execution Layer
DayEntry → one per user per date
DayEntryMetric → daily metric values
DayMetricDefinition → allowed metrics
Journal → notes (daily or independent)
ActionEntry → actual actions (must belong to DayEntry)
ActionEntryAttribute → attribute values
ActionAttributeDefinition → attribute definitions
ActionPlan → intended actions
ActionPlanAttribute → expected attributes
Life Structure Layer
Pillar → life areas
Activity → standardized action types
ActivitySchedule → recurrence rules
PillarMetric → measurable outcomes
PillarMetricLog → metric logs
Goal Layer
Goal → desired outcomes
GoalProgress → tracked values
Planning Layer
Plan → time window
PlanItem → measurable targets (activity, metric, milestone)
7. Key Relationships
User is the root entity (all data belongs to a user)
DayEntry is central to daily tracking
ActionEntry must belong to a DayEntry
Actions may reference Activity and ActionPlan
Metrics must be predefined before logging
Plans do not directly store execution data
8. Key Design Rules
Every entity is user-scoped
Every action must belong to a day
Plans do not store execution data
Actions can exist without plans
Metrics must be predefined
Attribute systems must use definition tables
Activities are optional but recommended
Modules must remain loosely coupled
9. System Behavior
Typical flow:
User defines pillars and activities
User defines schedules → system generates plans
User logs daily actions
User logs metrics over time
User creates goals and plans
System evaluates alignment and progress
Feedback Loop:
Plan → Action → Result → Reflection → Improved Plan
10. Design Principles for Development
Separation of Concerns
Keep planning, execution, and metrics independent
Data Integrity First
Enforce constraints at system level
Use references and definitions correctly
Extensibility
Prefer definition-based designs
Avoid hardcoding
Loose Coupling
Plans should not control execution tightly
Actions must remain independent
Real-World Modeling
Model actual behavior, not assumptions
Do not assume plans are completed
Incremental Development
Focus on V1 scope
Avoid over-engineering
11. Scope of Current Version (V1)
Included:
Day tracking
Action logging
Action planning
Metrics tracking
Pillars and activities
Goals and plans
Excluded:
AI-based recommendations
Advanced analytics
Automation systems
12. Expected Outcomes
The system enables users to:
understand daily behavior
compare planned vs actual actions
track life metrics over time
reflect on daily experiences
make data-driven decisions
13. AI Usage Guidelines
When generating or modifying code:
Treat actions as the source of truth
Do not tightly couple planning with execution
Maintain modular architecture
Ensure scalable entity design
Prefer clean, production-quality code
Follow this document as the source of truth
This document defines the product, system design, and development principles. All implementations must align with it.