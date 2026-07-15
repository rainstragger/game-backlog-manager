# 🎮 Game Backlog Manager
### A cozy full stack project for organizing games, tracking backlog progress, and practicing modern web development

---

## ✨ About The Project
Game Backlog Manager is a full stack application designed to help users organize their gaming library, track completed games, manage playtime, and monitor achievement progress.

It is also a personal study project built to practice backend structure, frontend integration, database modeling, and clean project organization.

The project combines:
- a complete game catalog
- custom libraries for grouping games
- a global progress flow per game
- a modern dark UI designed as a portfolio-ready MVP

It is also a study and portfolio project focused on clean backend structure, Angular frontend architecture, REST integration, and scalable feature organization.

---

## 🚧 Current Status
The backend foundation is already working, and the main core resources are already structured and testable.

- Spring Boot backend initialized
- PostgreSQL and MinIO running locally with Docker Compose
- Liquibase configured for schema versioning
- `Games` resource implemented with REST endpoints
- `Library` resource implemented with REST endpoints
- `ItemLibrary` resource implemented as the link between a library and a game
- `Progress` resource implemented as a global game progress tracker
- `Games` entity separated from API payloads using DTO + mapper
- `Library` entity separated from API payloads using DTO + mapper
- Basic CRUD flow validated with Insomnia

---

## 🕹️ What The MVP Already Covers

### Games
- Add a game to the catalog
- List all games
- Search games by term
- Open a modal-like details overlay
- Edit a game
- Delete a game

### Libraries
- Create libraries
- Edit libraries
- Delete libraries
- Expand a library to reveal linked items
- Add games to a library through an overlay
- Remove a linked game from a library

### Progress
- Load progress by game
- Create progress when it does not exist yet
- Update progress when it already exists
- Control progress status from the UI
- Edit `startedAt` and `completedAt` directly in the overlay

---

## 🎨 Frontend Experience
The current frontend follows a consistent international MVP direction:

- dark teal visual identity
- Bootstrap as the main styling base
- custom overlays for focused interactions
- sidebar navigation with improved visual hierarchy
- reusable detail overlay shared between catalog and library flows

At the moment, game covers are still handled with placeholders in the UI.
Real cover upload, storage, and rendering are intentionally being left for a future iteration.

---

## 🧩 Architecture Highlights
- **Frontend:** Angular standalone components + Signals + Reactive Forms
- **Backend:** Spring Boot + Java 21
- **Database:** PostgreSQL
- **Schema versioning:** Liquibase
- **Containers:** Docker Compose for local services
- **UI foundation:** Bootstrap 5 + Bootstrap Icons

Project structure is intentionally split to reflect real-world growth:
- `game-backlog-frontend/`
- `game-backlog-backend/`

---

## Current Limitation
The main feature intentionally postponed after the MVP is the real cover/media pipeline.

### Not finished yet
- upload real game covers
- persist and serve real media references
- replace placeholder covers with uploaded assets

For now, placeholders keep the interface visually consistent while the core product flows are completed first.

---

## 🛣️ Next Steps
After this MVP milestone, the most natural next improvements are:

- add real cover upload and media rendering
- refine progress UX and validation rules
- add authentication and user ownership
- introduce tags, ratings, reviews, and comments
- add achievements and richer session/playtime tracking
- connect external APIs for richer game metadata

---

## 🗂️ Storage Strategy
For media handling, the project follows a local-first strategy:

- local development: MinIO via Docker Compose
- future deploy/demo: S3-compatible object storage
- database stores metadata and references, not raw image bytes

This strategy is already planned, but the full media flow is still a future milestone.

---

## 🧰 Stack
- Frontend: Angular 22 + TypeScript + Bootstrap 5
- Backend: Spring Boot + Java 21
- Database: PostgreSQL
- Migrations: Liquibase
- Containerization: Docker Compose

---

## 📚 Why This Project Exists
This project exists for two reasons:

1. to build a real, evolving backlog management product
2. to demonstrate full stack engineering decisions in a portfolio-friendly repository

The goal is not only to make the app work, but to make the codebase look intentional, organized, and ready to evolve.

---
