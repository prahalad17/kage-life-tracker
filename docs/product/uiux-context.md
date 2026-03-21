# Life OS – UI/UX Design & Application Flow (V1)

---

## 1. Purpose

This document defines:

* UI/UX principles of Life OS
* Application flow and user journey
* Screen structure and responsibilities
* Interaction patterns
* Mapping of UI to system concepts

This serves as the **source of truth for frontend design and behavior**.

All UI implementations must align with:

* product philosophy
* system architecture
* execution-first design

---

## 2. Core UX Philosophy

Life OS is **not a task manager UI**.

It is a **personal life operating system interface**.

### 2.1 Key Principle

> The UI must reinforce that **actions (execution) are the source of truth**

---

### 2.2 UX Goals

* Minimize friction to log actions
* Clearly separate:

  * Plans (intent)
  * Actions (reality)
* Provide lightweight feedback (not overwhelming analytics)
* Keep UI clean while supporting power usage

---

### 2.3 UX Tone

* Clean and minimal
* Slightly motivational
* System-oriented (not gamified habit app)

---

## 3. Target User (V1)

Primary user:

* Power user (self)
* Comfortable with structured systems
* Accepts moderate complexity

Future:

* Can be simplified for general users

---

## 4. Core Interaction Model

The system is **day-centric**.

### Daily Flow:

1. Open app → lands on "Today"
2. View plans
3. Execute actions
4. Log actions
5. Review summary

---

### Core Loop:

Plan → Action → Result → Reflection → Improved Plan

---

## 5. Application Structure

### 5.1 Navigation (Sidebar)

Primary navigation:

* Today (core)
* Plans
* Metrics
* Goals
* Structure

---

### 5.2 Layout

Desktop-first layout:

* Left sidebar (navigation)
* Main content area

---

## 6. Core Screen: TODAY

This is the most important screen in the system.

---

### 6.1 Layout Structure

Three-column layout:

* Left → Plans
* Center → Actions (core)
* Right → Insights

---

## 7. TODAY SCREEN – DETAILED DESIGN

---

### 7.1 Left Panel: Plans (Intent)

Purpose:

* Show what was planned for the day

Content:

* List of ActionPlans for today

Behavior:

* Checkbox interaction does NOT mean completion
* Represents “starting / attempting”

Interaction:

* On click → triggers action logging flow

Rules:

* Plans do not store execution state
* UI must not imply completion from plans

---

### 7.2 Center Panel: Actions (Execution – Source of Truth)

Purpose:

* Log and display actual actions

This is the most critical UI component.

---

#### Action Logging Modes

1. Quick Add

   * Free text input
   * Minimal friction
   * Fast logging

2. Detailed Add

   * Structured form
   * Activity selection
   * Attribute input

---

#### Hybrid Behavior

* User can log quickly
* Later expand to add details

---

#### Action List

Displays:

* time
* name
* attributes

Interactions:

* click → open edit panel
* supports updating attributes

---

#### System Rules

* Every action must belong to a day
* Action may or may not link to a plan

---

### 7.3 Right Panel: Insights (Feedback)

Purpose:

* Provide lightweight daily summary

Content:

* planned count
* executed count
* alignment %
* highlights (top activity, missed items)

---

#### Design Rule

* Keep simple in V1
* Avoid heavy analytics

---

## 8. PLANS SCREEN

Purpose:

* Manage ActionPlans

---

### Features:

* Create plan
* Edit plan
* View upcoming plans
* View recurring plans

---

### Types:

* Single day
* Date range
* Recurring

---

## 9. METRICS SCREEN

Purpose:

* Track outcomes over time

---

### Displays:

* graphs (time series)
* metric values

Sources:

* Day metrics
* Pillar metrics

---

### Rule:

Metrics represent outcomes, not effort

---

## 10. GOALS SCREEN

Purpose:

* Long-term direction

---

### Displays:

* goal list
* progress tracking

---

### Data Source:

* Goal
* GoalProgress

---

## 11. STRUCTURE SCREEN

Purpose:

* Configure system definitions

---

### Sections:

* Pillars
* Activities
* Metric Definitions

---

### Rule:

* This is a setup/config area
* Not part of daily usage

---

## 12. KEY UX RULES

---

### 12.1 Actions First

UI must always emphasize:

* What user DID (actions)
* Not what was planned

---

### 12.2 Plan ≠ Completion

Plans must never appear as completed tasks

---

### 12.3 Low Friction Logging

* Logging must be extremely fast
* Avoid forcing forms

---

### 12.4 Progressive Complexity

* Show minimal UI initially
* Expand when needed

---

### 12.5 Separation of Layers

UI must reflect system separation:

* Plans (intent)
* Actions (execution)
* Metrics (outcomes)
* Reflection (context)

---

## 13. COMPONENT ARCHITECTURE (FRONTEND)

---

### Layout

* AppLayout

  * Sidebar
  * Content Area

---

### Pages

* TodayPage
* PlansPage
* MetricsPage
* GoalsPage
* StructurePage

---

### TodayPage Components

* PlanList
* ActionLog
* InsightsPanel
* ActionFormDrawer

---

### 13.1 Design Token System (Implemented)

Frontend styles are organized as a token-driven system:

* `abstracts` → variables, typography, spacing
* `base` → reset and globals
* `components` → buttons, forms, cards, tables
* `theme` → light (default) and dark overrides

---

### 13.2 Color System (Current Tokens)

Primary brand:

* `--color-primary`: `#7C3AED`
* `--color-primary-hover`: `#6D28D9`
* `--color-primary-soft`: `#F3E8FF`

Neutrals:

* `--color-bg`: `#F8FAFC`
* `--color-surface`: `#FFFFFF`
* `--color-surface-alt`: `#F1F5F9`
* `--color-border`: `#E2E8F0`
* `--color-text-primary`: `#0F172A`
* `--color-text-secondary`: `#475569`
* `--color-text-muted`: `#94A3B8`

Semantic states:

* success: `#16A34A`
* warning: `#F59E0B`
* error: `#DC2626`
* info: `#2563EB`

---

### 13.3 Typography Scale (Current Tokens)

Font family:

* Inter, sans-serif

Scale:

* xs: 12px
* sm: 14px
* base: 16px
* lg: 18px
* xl: 24px
* 2xl: 32px
* 3xl: 40px

Heading intent:

* h1: major page hero
* h2: primary section heading
* h3/h4: subsection hierarchy

Rule:

* body text defaults to `base` with line-height `1.6`
* secondary and muted copy must use text token colors, not raw hex

---

### 13.4 Spacing, Radius, Shadows, Motion

Spacing tokens:

* 4, 8, 16, 24, 32, 48, 64 px

Radius tokens:

* sm: 10px
* md: 12px
* lg: 16px
* xl: 20px

Shadow tokens:

* sm: subtle card/table depth
* md: hover elevation
* lg: high emphasis surfaces

Motion:

* fast transition baseline: `0.2s ease`
* hover and focus states should feel responsive but calm

---

### 13.5 Theme Support

Light theme:

* default token values declared at `:root`

Dark theme:

* enabled via `.dark-theme` class override
* updates background, surface, border, and text tokens
* keeps the same semantic structure so components remain consistent

Rule:

* new UI should always use CSS variables/tokens so light/dark themes work automatically

---

### 13.6 Component Visual Patterns

Buttons:

* primary button uses brand color with elevated shadow
* outline button uses border + text emphasis
* ghost button uses transparent surface with soft hover fill

Forms:

* rounded medium inputs
* clear focus state with primary border + soft ring
* labels use muted text to maintain hierarchy

Cards and tables:

* surface + border + radius + subtle shadow baseline
* hover states can use slight elevation/soft background for affordance

Navigation/layout:

* app shell uses fixed sidebar + topbar + scrollable content area
* sidebar uses soft gradient surface and primary-accent active states
* interaction feedback favors color + subtle transform (not heavy animation)

---

### 13.7 UX Styling Rules for AI

When AI generates frontend UI code:

* Use existing tokens (`--color-*`, radius, shadow, transition) before introducing new values
* Prefer existing utility patterns and shared component styles
* Avoid hardcoded colors when a token exists
* Keep visual hierarchy through text-primary, text-secondary, and text-muted roles
* Preserve consistent spacing rhythm with tokenized scale
* Ensure hover/focus/active states are always defined for interactive elements

---

## 14. UI → BACKEND MAPPING

---

### Today Screen

Load:

* DayEntry (today)
* ActionPlans (today)
* ActionEntries (today)
* Metrics (today)

---

### Actions

* Create ActionEntry
* Add attributes

---

### Plans

* Fetch plans
* Create/update plans

---

## 15. FUTURE EXTENSIONS (NOT IN V1)

* AI recommendations
* Advanced analytics
* Automation
* Notifications

---

## 16. SUMMARY

The UI of Life OS must:

* Center around daily execution
* Keep plans separate from actions
* Enable fast logging
* Provide simple feedback
* Support structured life management without overwhelming the user

---

This document defines the UX foundation for Life OS.
All frontend implementations must align with it.
