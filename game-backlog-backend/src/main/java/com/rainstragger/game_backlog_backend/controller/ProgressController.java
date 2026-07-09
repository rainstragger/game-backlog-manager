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

import com.rainstragger.game_backlog_backend.dto.ProgressDTO;
import com.rainstragger.game_backlog_backend.entities.Progress;
import com.rainstragger.game_backlog_backend.mappers.ProgressMapper;
import com.rainstragger.game_backlog_backend.services.ProgressService;

@RestController
@RequestMapping("/progress")
public class ProgressController {
    private final ProgressService progressService;
    private final ProgressMapper progressMapper;

    public ProgressController(ProgressService progressService, ProgressMapper progressMapper) {
        this.progressService = progressService;
        this.progressMapper = progressMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProgressDTO>> findAll() {
        List<ProgressDTO> progressList = progressService.findAll().stream()
                .map(progressMapper::toDTO)
                .toList();
        return ResponseEntity.ok(progressList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressDTO> findById(@PathVariable Integer id) {
        Optional<ProgressDTO> progress = progressService.findById(id)
                .map(progressMapper::toDTO);
        return progress.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<ProgressDTO> findByGameId(@PathVariable Integer gameId) {
        Optional<ProgressDTO> progress = progressService.findByGameId(gameId)
                .map(progressMapper::toDTO);
        return progress.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProgressDTO>> findAllByStatus(@RequestParam String status) {
        List<ProgressDTO> progressList = progressService.findAllByStatus(status).stream()
                .map(progressMapper::toDTO)
                .toList();
        return ResponseEntity.ok(progressList);
    }

    @PostMapping
    public ResponseEntity<?> createProgress(@RequestBody ProgressDTO progressDTO) {
        try {
            Progress newProgress = progressService.create(progressMapper.toEntity(progressDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(progressMapper.toDTO(newProgress));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgress(@PathVariable Integer id, @RequestBody ProgressDTO progressDTO) {
        try {
            Progress updatedProgress = progressService.update(id, progressMapper.toEntity(progressDTO));
            return ResponseEntity.ok(progressMapper.toDTO(updatedProgress));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgress(@PathVariable Integer id) {
        try {
            progressService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
