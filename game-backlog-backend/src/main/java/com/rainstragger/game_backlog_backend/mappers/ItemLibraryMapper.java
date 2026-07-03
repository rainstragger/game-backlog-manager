package com.rainstragger.game_backlog_backend.mappers;

import org.springframework.stereotype.Component;

import com.rainstragger.game_backlog_backend.dto.ItemLibraryDTO;
import com.rainstragger.game_backlog_backend.entities.Games;
import com.rainstragger.game_backlog_backend.entities.ItemLibrary;
import com.rainstragger.game_backlog_backend.entities.Library;

@Component
public class ItemLibraryMapper {
    public ItemLibraryDTO toDTO(ItemLibrary itemLibrary) {
        return new ItemLibraryDTO(
                itemLibrary.getId(),
                itemLibrary.getGameId().getId(),
                itemLibrary.getLibraryId().getId(),
                itemLibrary.getCreatedAt()
        );
    }

    public ItemLibrary toEntity(ItemLibraryDTO itemLibraryDTO) {
        ItemLibrary itemLibrary = new ItemLibrary();
        Games game = new Games();
        Library library = new Library();

        game.setId(itemLibraryDTO.gameId());
        library.setId(itemLibraryDTO.libraryId());

        itemLibrary.setId(itemLibraryDTO.id());
        itemLibrary.setGameId(game);
        itemLibrary.setLibraryId(library);

        return itemLibrary;
    }
}
