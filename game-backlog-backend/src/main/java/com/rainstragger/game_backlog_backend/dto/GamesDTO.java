package com.rainstragger.game_backlog_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GamesDTO(
	Long id,
	String name,
	String resume,
	LocalDate launchDate,
	String developer,
	String publisher,
	String genre,
	String minReq,
	String recomReq,
	Integer coverId,
	LocalDateTime createdAt
) {
}