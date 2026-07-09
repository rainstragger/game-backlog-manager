package com.rainstragger.game_backlog_backend.mappers;

import org.springframework.stereotype.Component;

import com.rainstragger.game_backlog_backend.dto.ProgressDTO;
import com.rainstragger.game_backlog_backend.entities.Games;
import com.rainstragger.game_backlog_backend.entities.Progress;
import com.rainstragger.game_backlog_backend.enums.ProgressEnum;

@Component
public class ProgressMapper {
    public ProgressDTO toDTO(Progress progress) {
        return new ProgressDTO(
                progress.getId(),
                progress.getGame().getId(),
                progress.getStatus().toString(),
                progress.getStartedAt(),
                progress.getCompletedAt(),
                progress.getCreatedAt()
        );
    }

    public Progress toEntity(ProgressDTO progressDTO) {
        Progress progress = new Progress();
        Games game = new Games();

        game.setId(progressDTO.gameId());

        progress.setId(progressDTO.id());
        progress.setGame(game);
        progress.setStatus(ProgressEnum.valueOf(progressDTO.status()));
        progress.setStartedAt(progressDTO.startedAt());
        progress.setCompletedAt(progressDTO.completedAt());

        return progress;
    }
}
