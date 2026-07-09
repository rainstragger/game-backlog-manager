# 🎮 Game Backlog Manager
### A cozy full stack project for organizing games, tracking backlog progress, and practicing modern web development

---

## ✨ About The Project
Game Backlog Manager is a full stack application designed to help users organize their gaming library, track completed games, manage playtime, and monitor achievement progress.

It is also a personal study project built to practice backend structure, frontend integration, database modeling, and clean project organization.

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

## 🎯 Objective
The main idea of this project is to build something that feels like a real product while serving as a hands-on learning experience.

- Provide a user-friendly interface for managing a gaming library
- Track completed games, playtime, and achievement progress
- Allow users to add, update, and delete games from their library

---

## 🕹️ MVP
The first version focuses on the essential game catalog and backlog flow:

- Register a game
- List all games in the catalog
- See game details such as genre, release date, developer, and publisher
- Update game details
- Delete a game from the catalog
- Create libraries
- Add a game to a library
- Track the game progress status

---

## 🛣️ Roadmap
After the MVP, the project can grow into a more complete backlog platform:

- Register a user account
- Login to the account
- Logout of the account
- See the playtime of the user, for each game and a total playtime
- See each player's library
- Add game achievements
- Add game cover
- Add game tags
- Add game comments
- Add game ratings
- Add game reviews
- Read APIs to get multiple game accounts to track players progress, achievements, and time spent
- Add playtime and session tracking
- Refine progress rules and historical progress updates

---

## 🗂️ Storage Strategy
For media handling, the project follows a simple local-first approach:

- Local development: MinIO via Docker Compose
- Deploy/demo: S3-compatible object storage
- Database stores only metadata and a `storage_key` or URL, not the raw image bytes

---

## 🧰 Stack
- Frontend: Angular
- Backend: Spring Boot + Java
- Database: PostgreSQL
- Containerization: Docker

---

