package com.rainstragger.game_backlog_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainstragger.game_backlog_backend.dto.GamesDTO;
import com.rainstragger.game_backlog_backend.entities.Games;
import com.rainstragger.game_backlog_backend.mappers.GamesMapper;
import com.rainstragger.game_backlog_backend.services.GamesService;



@RestController
@RequestMapping("/games")
public class GamesController {

    private final GamesService gamesService;
    private final GamesMapper gamesMapper;
    
    public GamesController(GamesService gamesService, GamesMapper gamesMapper) {
        this.gamesService = gamesService;
        this.gamesMapper = gamesMapper;
    }

    @GetMapping
	public ResponseEntity<List<GamesDTO>> findAll() {
		List<GamesDTO> games = gamesService.findAll(true).stream()
				.map(gamesMapper::toDTO)
				.toList();
		return ResponseEntity.ok(games);
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<GamesDTO> findById(@PathVariable Integer id) {
		Optional<GamesDTO> games = gamesService.findById(id)
				.map(gamesMapper::toDTO);
		return games.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<GamesDTO>> findByTerm(@RequestParam String term) {
		List<GamesDTO> games = gamesService.findByTerm(term).stream()
				.map(gamesMapper::toDTO)
				.toList();
		return ResponseEntity.ok(games);
	}

    @PostMapping
    public ResponseEntity<?> createGame(@RequestBody GamesDTO gameDTO) {
        try {
            Games newGame = gamesService.create(gamesMapper.toEntity(gameDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(gamesMapper.toDTO(newGame));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Integer id, @RequestBody GamesDTO gameDTO) {
        try {
            Games updatedGame = gamesService.update(id, gamesMapper.toEntity(gameDTO));
            return ResponseEntity.ok(gamesMapper.toDTO(updatedGame));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Integer id) {
        try {
            gamesService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}