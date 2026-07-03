package com.rainstragger.game_backlog_backend.dto;

import java.time.LocalDateTime;

public record ItemLibraryDTO(
	Integer id,
	Integer gameId,
	Integer libraryId,
	LocalDateTime createdAt
) {
}
