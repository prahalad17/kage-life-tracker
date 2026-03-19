# Life OS (Kage) — Backend AI Context (V1)

This document gives an AI (and future contributors) the **backend-specific** context needed to work safely inside the `backend/` Spring Boot project.

It complements:

- `docs/product/project-context.md` (product + system philosophy)
- `docs/product/core-design.md` (domain design spec)
- `docs/database/db-context.md` (DB + relationship rules)
- `docs/database/db-design.md` (schema-level design)

If there is any conflict, **product + DB rules win**, and backend code should be refactored to match them.

---

## 1. Tech Stack & Runtime Assumptions

- **Backend**: Spring Boot (Maven), Java 21
- **Web**: Spring MVC (`spring-boot-starter-webmvc`)
- **Persistence**: Spring Data JPA + Hibernate
- **Database**: MySQL (runtime dependency)
- **Security**: Spring Security + JWT
- **Docs**: SpringDoc OpenAPI (Swagger UI enabled)

---

## 2. Repository Layout (Backend)

Primary backend root: `backend/src/main/java/com/kage/`

Common packages:

- **`controller/`**: REST controllers (HTTP boundary)
- **`service/` + `service/impl/`**: business logic (authoritative rules live here)
- **`entity/`**: JPA domain entities (aggregates, value rules, invariants)
- **`repository/`**: Spring Data repositories
- **`dto/` + `common/dto/`**: request/response models (API contracts)
- **`mapper/`**: MapStruct mappers (entity ↔ DTO)
- **`security/`**: JWT filter, user details, refresh token, etc.
- **`exception/`**: business + HTTP exception mapping
- **`util/`**: paging/spec builders, guard helpers
- **`config/`**: Spring configuration (security, OpenAPI, auditing)

Main app entry: `com.kage.BackendApplication` (also enables scheduling).

---

## 3. API Conventions

### 3.1 Response envelope

Most endpoints return:

- `ApiResponse<T>` on success
- `ApiResponse<Void>` on errors (via exception handler)

### 3.2 Validation

Request DTOs often use:

- `jakarta.validation` annotations
- controller parameters with `@Valid`

Validation failures are mapped by `GlobalExceptionHandler` into a `400` with a single field error message.

### 3.3 Authentication boundary

Controllers typically use:

- `@AuthenticationPrincipal CustomUserDetails user`

Then pass `user.getUser().getId()` into services.

Rule: **All user-scoped operations must enforce ownership**, ideally in the service layer (not just the controller).

---

## 4. Security Model (JWT + Refresh Token Cookie)

### 4.1 Protected vs public endpoints

Configured in `SecurityConfig`:

- **Public**: `/api/auth/**`, Swagger `/swagger-ui/**`, OpenAPI `/v3/api-docs/**`
- **Protected**: everything else requires authentication

### 4.2 Request authentication

Authentication is performed via `Authorization: Bearer <access_token>` in `JwtAuthenticationFilter`.

- Invalid access token is ignored (request proceeds unauthenticated and will be rejected by the security chain).

### 4.3 Refresh tokens

Refresh token is managed in `AuthController` via **HttpOnly cookie** named `refresh_token`.

Backend behavior:

- Login sets refresh cookie.
- Refresh reads refresh cookie (and has a backward-compatible fallback reading Bearer header as refresh token).
- Logout clears refresh cookie.

Important rule for local dev:

- refresh cookie is configured with `secure=false` for localhost HTTP.

---

## 5. Persistence Conventions & Soft Delete

### 5.1 `BaseEntity`

Most entities extend `entity/BaseEntity`, which provides:

- `id`
- `createdAt`, `updatedAt` (audited)
- `createdBy`, `updatedBy` (audited)
- `status: RecordStatus` (defaults to `ACTIVE`)
- `remarks`
- `deactivate()` / `activate()` helpers

### 5.2 Soft delete rule

Soft delete is done by setting `status = INACTIVE`.

Default query behavior in many services:

- Only operate on `RecordStatus.ACTIVE` entities.

If you add new repositories, strongly prefer methods that include `status` in query criteria (or consistently apply a specification).

---

## 6. Search, Filters, Sorting, Pagination

There is a generic search model:

- `common/dto/request/SearchRequestDto`
  - includes `page`, `size`, `filters`, `sort`
- `PageableBuilderUtil.build(request)` builds `Pageable`
- `SpecificationBuilderUtil.build(request)` builds a dynamic JPA `Specification<T>`
- `UserSpecificationBuilderUtil.build(userId)` enforces:
  - `root.user.id = userId`
  - `root.status = ACTIVE`

Important limitation:

- `SpecificationBuilderUtil` uses `root.get(field)` and supports only **top-level fields** (not nested paths like `activity.name`).
  - If you need nested filtering later, extend it deliberately (do not hack around it in controllers).

---

## 7. Exception & Error Handling

HTTP error mapping is centralized in `exception/GlobalExceptionHandler`.

Common patterns:

- `NotFoundException` / `ResourceNotFoundException` → `404`
- `BadRequestException` / `BusinessException` → `400`
- `AuthenticationException` / invalid refresh token → `401`
- `MethodArgumentNotValidException` → `400`
- `DataIntegrityViolationException` → currently mapped to `500` (database constraint errors surface message)

Backend rule:

- Prefer throwing **domain-meaningful** exceptions in services (e.g. `BusinessException`, `NotFoundException`) rather than leaking low-level exceptions.

---

## 8. Domain Rules That Must Be Preserved (Life OS V1)

These rules are the heart of your refactor; backend code must enforce them over time.

### 8.1 Separation of intent vs reality

- **Planning (intent)**:
  - `ActionPlan`, `Plan`, `PlanItem`
  - may exist with no execution
  - must not store execution outcome fields as source-of-truth
- **Execution (reality)**:
  - `DayEntry`, `ActionEntry`, `ActionEntryAttributes`, `Journal`, `DayMetric`
  - execution is the source of truth
- **Measurement (outcomes)**:
  - `PillarMetric`, `PillarMetricLog`, `GoalProgress`

### 8.2 Day-centric invariants

For the Execution layer:

- Exactly one `DayEntry` per user per date (enforced by DB constraint and service logic).
- Every `ActionEntry` must belong to a `DayEntry` (mandatory association).
- `ActionEntry.user` must match `DayEntry.user` (ownership consistency).
- If `loggedAt` exists, it must fall within the `DayEntry.date` day window (UTC/local-time handling must be consistent—choose one strategy and apply it everywhere).

### 8.3 Definition-based extensibility

- Attributes and metrics must use definition tables:
  - `ActionAttributeDefinition` → allowed action attributes
  - `DayMetricDefinition` → allowed daily metrics

### 8.4 Flexible value fields (exactly one non-null)

For entities with multi-type value columns (examples):

- `DayMetric`
- `ActionEntryAttributes`
- `GoalProgress`

Rule:

- Exactly **one** of the value fields must be non-null per record (int/decimal/boolean/text).

Implementation guidance:

- Enforce in the service layer (and/or entity-level guard method).
- Prefer also adding DB constraints later (if feasible) to protect against bugs.

---

## 9. Current “V1 Backend” vs “Life OS V1 Design” (Known gaps)

These are not “bugs” — they’re normal for incremental refactoring — but they matter as you migrate:

- The existing `ActivityDailyLog` module overlaps conceptually with `ActionEntry` + `DayEntry`.
  - During migration, avoid mixing “old daily log” with “new day/action entry” semantics in one endpoint.
- Dashboard “to-do” currently appears to be derived from *logs for today* rather than *expected plans/schedules*.
  - In Life OS, “intent” (plans) and “execution” (actions) should be comparable but separate.

---

## 10. Safe Implementation Strategy (Incremental Refactor)

When adding the new Life OS entities/services:

- **Build the spine first**:
  - `DayEntry` creation/loading (by user + date)
  - `ActionEntry` creation (must attach to a day)
- **Keep ownership checks at service boundaries**:
  - Controllers pass `userId`; services validate relationships.
- **Do not leak JPA entities to controllers**:
  - Always map to DTO responses.
- **Prefer aggregate methods**:
  - Encapsulate invariants via entity methods (e.g. `deactivate()`, `rename()`, `updateTracking()`), then rely on JPA dirty checking.
- **Avoid cross-module coupling**:
  - Plans must not “control” actions; they can suggest, link, and be evaluated against execution.

---

## 11. What the AI Should Do When Modifying Backend Code

When implementing a new feature or refactor:

- Identify the **layer** you are touching (planning / execution / measurement / reflection).
- Enforce **user scoping** and **status=ACTIVE** queries everywhere.
- Prefer adding **service-level helper loaders** like:
  - `loadOwnedActiveX(id, userId)`
- Prefer composition:
  - create related entities in one transaction (aggregate root owns dependents)
- Keep endpoints consistent with existing `ApiResponse<T>` patterns.

---

## 12. Quick Glossary (Backend Mapping)

- **Pillar**: life domain
- **Activity**: standardized action type (optional linkage from actions/plans)
- **ActivitySchedule**: recurrence rules (often used to generate `ActionPlan`)
- **DayEntry**: one row per user/day; anchors daily context + execution
- **ActionEntry**: what happened (execution)
- **ActionPlan**: what was intended (planning)
- **DayMetricDefinition / DayMetric**: daily measurements (inputs/outcomes)
- **PillarMetric / PillarMetricLog**: long-term outcomes (time-series)
- **Goal / GoalProgress**: desired outcomes + tracked progress snapshots

