# 🧠 Kage — Life & Habit Tracker

> **Kage** is a full-stack life and habit tracking application built with  
> **Spring Boot (backend)** and **Angular (frontend)**.

> A productivity and habit-tracking system designed around **intent, consistency, and gradual improvement** — not just raw tracking.

🚧 **Status: Under Active Development** 🚧

---

## 📌 What is Kage?

Kage helps users organize their life into meaningful areas, track daily activities, and **optionally improve those activities through structured, flexible progression**.

Unlike traditional habit trackers that only record data, Kage is designed to **evolve into a suggestion-based and feedback-driven platform**, while remaining simple and non-restrictive at its core.

The focus is on **building sustainable habits**, not enforcing rigid routines or unrealistic plans.

---

## 🎯 Core Idea

The system is built on a simple principle:

> **Track what matters today.  
> Improve what matters over time.**

Users remain fully in control:
- They decide *what* to track
- They choose *if* and *when* to improve
- They can pause, simplify, or restart at any time

Progression and guidance are **optional layers**, never mandatory.

---

## 🧩 Core Concepts (How Kage Thinks)

Kage models behavior using five clearly separated domain concepts:

---

### 1️⃣ Life Areas (Pillars)
High-level domains that organize a user’s life, such as:
- Health
- Career
- Mind
- Finance

These act as anchors to group related activities meaningfully.

---

### 2️⃣ Activities
Concrete habits or actions under a life area.

Examples:
- Pushups
- Meditation
- Reading
- Avoid smoking

Activities can be:
- Positive (things to do)
- Negative (things to avoid)
- Tracked using different modes (yes/no, count, duration)

---

### 3️⃣ Progression *(Optional)*
A temporary improvement journey tied to an activity.

Examples:
- Pushups: 10 → 100
- Meditation: 0 → 20 minutes
- Smoking: 2 → 0 cigarettes

Progression is:
- User-defined  
- Time-bound  
- Optional  
- Removable once completed  

---

### 4️⃣ Plans *(Optional)*
Rules that describe **how progression should evolve over time**.

Plans store **intent**, not rigid daily instructions:
- Start date
- End date
- Distribution strategy
- Adaptation mode

Daily suggestions are **derived dynamically**, not stored.

---

### 5️⃣ Daily Logs
The most critical layer: **what actually happened**.

Users log:
- What they did on a given day
- Quantitative data (if applicable)
- Optional notes

Logs are immutable and form the foundation for:
- Progress tracking
- Insights
- Dashboards
- Future recommendations

---

## 🔄 How the App Works (High-Level Flow)

Life Areas
↓
Activity Templates (suggestions)
↓
User Activities (commitment)
↓
(Optional) Progression & Plans
↓
Daily Logs (reality)

---

Dashboards and recommendations are **computed from stored intent and historical data**, not hard-coded rules.

---

## 🧠 Design Philosophy

Kage follows a few strict architectural and product principles:

- **Intent is stable; behavior is dynamic**
- **Progression is optional and temporary**
- **Daily recommendations are derived, not persisted**
- **History is immutable**
- **Intelligence lives in services, not the database**

This ensures flexibility, correctness, and long-term scalability.

---

## 🧪 Current Development Status

Kage is under **active development**.

### Currently being built:
- Core domain model
- User authentication
- Life areas (pillars)
- Activities management
- Daily activity logging
- Clean backend architecture

### Planned for future versions:
- Smart daily recommendations
- Progress analytics & dashboards
- Preset templates for faster onboarding
- Adaptive progression logic
- AI-assisted feedback

---

## 🚀 Long-Term Vision

The long-term goal is to evolve Kage into a **personal growth companion** that:

- Learns from user behavior
- Adapts to missed days or slow progress
- Suggests achievable daily actions
- Encourages consistency over perfection

All while keeping users fully in control.

---

## 📄 Documentation Note

This README describes the **core system philosophy and v1 design intent**.

As development progresses:
- More technical documentation may be added
- This README will evolve rather than be replaced

---

## ⚠️ Disclaimer

This is an **in-progress project**.  
Features, APIs, and internal models may change as the system matures.

---

> **Build habits.  
> Not pressure.  
> Not guilt.  
> Just progress.**