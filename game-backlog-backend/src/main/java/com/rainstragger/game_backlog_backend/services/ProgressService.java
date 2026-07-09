package com.rainstragger.game_backlog_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rainstragger.game_backlog_backend.entities.Progress;
import com.rainstragger.game_backlog_backend.enums.ProgressEnum;
import com.rainstragger.game_backlog_backend.repository.GamesRepository;
import com.rainstragger.game_backlog_backend.repository.ProgressRepository;

@Service
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final GamesRepository gamesRepository;

    public ProgressService(ProgressRepository progressRepository, GamesRepository gamesRepository) {
        this.progressRepository = progressRepository;
        this.gamesRepository = gamesRepository;
    }

    public List<Progress> findAll() {
        return progressRepository.findAll();
    }

    public List<Progress> findAllByStatus(String status) {
        return progressRepository.findAllByStatus(ProgressEnum.valueOf(status.toUpperCase()));
    }

    public Optional<Progress> findById(Integer id) {
        return progressRepository.findById(id);
    }

    public Optional<Progress> findByGameId(Integer gameId) {
        return progressRepository.findByGame_Id(gameId);
    }

    public Progress create(Progress progress) {
        if (progress.getId() != null) {
            throw new IllegalArgumentException("Progress already exists");
        }

        Integer gameId = progress.getGame().getId();
        boolean gameExists = gamesRepository.findById(gameId).isPresent();
        boolean progressAlreadyExists = progressRepository.findByGame_Id(gameId).isPresent();

        if (!gameExists) {
            throw new IllegalArgumentException("Game not found");
        }

        if (progressAlreadyExists) {
            throw new IllegalArgumentException("Progress already exists for this game");
        }

        return progressRepository.save(progress);
    }

    public Progress update(Integer id, Progress progress) {
        Progress existingProgress = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progress not found"));

        existingProgress.setStatus(progress.getStatus());
        existingProgress.setStartedAt(progress.getStartedAt());
        existingProgress.setCompletedAt(progress.getCompletedAt());

        return progressRepository.save(existingProgress);
    }

    public void delete(Integer id) {
        Progress progress = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progress not found"));

        progressRepository.delete(progress);
    }
}
