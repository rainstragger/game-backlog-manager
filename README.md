# GAME BACKLOG MANAGER
## Description
Game Backlog Manager is a full stack application designed to help users organize their gaming library, track completed games, manage playtime, and monitor achievement progress.

## Objective
- Provide a user-friendly interface for managing a gaming library.
- Track completed games, playtime, and achievement progress.
- Allow users to add, update, and delete games from their library.

## MVP
- Register a game
- List all games in the library
- See game details (cover, genre, release date, developer, publisher)
- Update game details
- Delete a game from the library
- Status of the game (completed, not completed)

## Roadmap
- Register a user account
- Login to the account
- Logout of the account
- See the playtime of the user, for each game and a total playtime
- See each players library
- Add game achievements
- Add game tags
- Add game comments
- Add game ratings
- Add game reviews
- Read APIs to get multiple game accounts to get players track progress of games, achievements and time spent

## Storage Strategy
- Local development: MinIO via Docker Compose
- Deploy/demo: S3-compatible object storage
- Database stores only metadata and a `storage_key`/URL (not the image bytes)

## Stack
- Frontend: Angular
- Backend: Spring Boot + Java
- Database: PostgreSQL
- Containerization: Docker




