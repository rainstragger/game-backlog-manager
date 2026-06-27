package com.rainstragger.game_backlog_backend.dto;

import java.time.LocalDateTime;

public record LibraryDTO(
    Integer id,
    String name,
    String description,
    Boolean active,
    LocalDateTime createdAt
) {
    
}
