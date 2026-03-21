# Life OS - Frontend AI Context Document

---

## 1. Purpose

This document defines frontend implementation context for AI-assisted development.

It should be used as the source of truth when creating or modifying Angular UI code.

This document covers:

- frontend architecture and module boundaries
- routing and role-based access structure
- shared UI patterns and reusable building blocks
- API integration conventions
- coding rules for safe, consistent AI changes

---

## 2. Frontend Stack Overview

Current frontend stack:

- Angular 21 (standalone components)
- TypeScript
- Angular Router
- Angular HttpClient with interceptor pipeline
- Reactive Forms
- SCSS + Bootstrap
- Vitest for unit tests

High-level idea:

- backend models business truth
- frontend provides role-based workflows to create, update, and view that data

---

## 3. Application Structure

Main source root:

- `frontend/src/app`

Primary layers:

- `auth` -> authentication pages, models, and auth state/init services
- `core` -> guards, interceptors, base layouts/pages, global services
- `layout` -> main app layout pieces (header, sidebar, shell containers)
- `pages` -> feature pages split by role (`admin`, `user`)
- `shared` -> reusable UI components and cross-feature models/configs

Style organization:

- global SCSS under `frontend/src/styles`
- component-level SCSS near each component

---

## 4. Routing and Access Model

Top-level routes are defined in:

- `frontend/src/app/app.routes.ts`

Public area:

- wrapped by `PublicLayout`
- guarded by `guestGuard`
- includes landing + auth routes (`login`, `register`, `check-email`)

Protected area:

- wrapped by `MainLayout`
- guarded by `authGuard`
- role split with `roleGuard` and route metadata:
  - `ROLE_ADMIN` -> lazy-loaded admin routes
  - `ROLE_USER` -> lazy-loaded user routes

Feature route files:

- `frontend/src/app/pages/admin/admin.routes.ts`
- `frontend/src/app/pages/user/user.routes.ts`

Routing pattern used in features:

- shell component as parent
- `overview` and `list` child routes
- default redirect to `overview`

---

## 5. Current Feature Modules

Admin features:

- dashboard
- users
- pillars
- activity

User features:

- dashboard
- daily-log
- pillars
- activity

Common folder pattern inside features:

- `models` (request/response/data contracts)
- `service` (HTTP + domain operations)
- `pages` (shell/overview/list components)

---

## 6. State, Auth, and Request Pipeline

Auth state management:

- `AuthStateService` stores in-memory user/token session state
- `AuthInitService` initializes auth on app bootstrap

HTTP interceptor pipeline is configured in:

- `frontend/src/app/app.config.ts`

Current interceptor order:

1. logging
2. loader
3. api-response unwrap
4. auth token attach
5. error normalization
6. refresh on 401

Important behavior:

- API wrapper interceptor unwraps `{ success, data, message }` style responses
- specific requests can bypass wrapper with `X-Skip-Api-Wrapper`
- loader can be bypassed with `X-Skip-Loader`
- refresh interceptor retries unauthorized non-auth requests

---

## 7. Shared UI Patterns

Reusable components include:

- generic `DataForm` (config-driven reactive form)
- `DataTable` for list views
- `ConfirmDialog`
- `Loader` and `Overlay`

Config-driven UI model lives under:

- `frontend/src/app/shared/models/form`
- `frontend/src/app/shared/models/table`

Pattern used across features:

- keep feature forms/tables declarative via config objects
- keep component templates thin by moving behavior to TypeScript + configs

---

## 8. API and Model Conventions

Service conventions:

- one service per feature domain
- base URL defined in service
- typed request/response models used for public methods

API contracts commonly used:

- `ApiResponse<T>`
- `PageResponse<T>`
- `SearchRequestDto`

Guideline:

- prefer typed model files in feature `models` folders
- avoid `any` unless truly unavoidable for dynamic forms or transitional code

---

## 9. AI Implementation Rules (Frontend)

When AI edits frontend code, follow these rules:

1. Preserve role-based boundaries
- Admin code stays under `pages/admin/*`
- User code stays under `pages/user/*`

2. Follow existing route composition
- use shell + child routes pattern
- include default redirects where needed

3. Reuse shared components first
- prefer `shared/components` (`DataForm`, `DataTable`, dialog)
- avoid duplicating generic form/table logic

4. Keep contracts explicit
- create/update request models in feature `models`
- strongly type service methods and component data

5. Respect interceptor behavior
- do not duplicate response unwrap logic in services
- only use skip headers when there is a clear reason

6. Keep UI/business separation clean
- component = UI interaction + orchestration
- service = HTTP/domain operations
- config objects = table/form structure

7. Maintain consistency with backend truth
- frontend naming and payload shape must align to backend DTOs/endpoints
- actions/execution data should not be conflated with planning intent

---

## 10. AI Checklist Before Finishing Frontend Changes

- route registered in correct module (`app`, `admin`, or `user`)?
- guard/role rules correct?
- request and response models typed?
- service method signatures typed and minimal?
- reusable shared component usage considered first?
- no duplicated interceptor concerns in feature code?
- unit tests updated for changed behavior?
- code style and naming consistent with neighboring files?

---

## 11. V1 Alignment Notes

Product docs define execution-first behavior (actions as source of truth).

Frontend implementations should continue to enforce:

- clear separation of planning vs execution
- lightweight, low-friction data entry
- modular role-based screens with reusable UI primitives

If implementation decisions conflict with this principle:

- prefer execution data fidelity over visual convenience

---

## 12. Summary

This frontend is a role-based Angular application built around:

- protected routing + auth state
- feature-scoped services and models
- reusable config-driven components
- interceptor-managed API behavior

AI should use this document to produce safe, consistent, and architecture-aligned frontend code changes.

