package com.rainstragger.game_backlog_backend.mappers;

import org.springframework.stereotype.Component;

import com.rainstragger.game_backlog_backend.dto.ProgressDTO;
import com.rainstragger.game_backlog_backend.entities.ItemLibrary;
import com.rainstragger.game_backlog_backend.entities.Progress;

@Component
public class ProgressMapper {
    public ProgressDTO toDTO(Progress progress) {
        return new ProgressDTO(
                progress.getId(),
                progress.getItemLibrary().getId(),
                progress.getStatus(),
                progress.getStartedAt(),
                progress.getCompletedAt(),
                progress.getCreatedAt()
        );
    }

    public Progress toEntity(ProgressDTO progressDTO) {
        Progress progress = new Progress();
        ItemLibrary itemLibrary = new ItemLibrary();

        itemLibrary.setId(progressDTO.itemLibraryId());

        progress.setId(progressDTO.id());
        progress.setItemLibrary(itemLibrary);
        progress.setStatus(progressDTO.status());
        progress.setStartedAt(progressDTO.startedAt());
        progress.setCompletedAt(progressDTO.completedAt());

        return progress;
    }
}
