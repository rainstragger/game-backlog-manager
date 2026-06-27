package com.rainstragger.game_backlog_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ItemLibraryDTO(
	Integer id,
	Integer gameId,
	Integer libraryId,
	String status,
	Boolean active,
	LocalDate startedAt,
	LocalDate completedAt,
	LocalDateTime createdAt
) {
}