package com.rainstragger.game_backlog_backend.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record CoverMediaDTO(
    Integer id,
    String storageKey,
    String name,
    String checksum,
    String contentType,
    BigInteger sizeBytes,
    Boolean active,
    LocalDateTime createdAt
) {
    
}
