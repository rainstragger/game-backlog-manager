package com.rainstragger.game_backlog_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProgressDTO(
    Integer id,
    Integer gameId,
    String status,
    LocalDate startedAt,
    LocalDate completedAt,
    LocalDateTime createdAt
) {
}
