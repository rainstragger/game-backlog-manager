package com.rainstragger.game_backlog_backend.mappers;

import com.rainstragger.game_backlog_backend.dto.LibraryDTO;
import com.rainstragger.game_backlog_backend.entities.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper {
    public LibraryDTO toDTO (Library library){
        return new LibraryDTO(
            library.getId(), 
            library.getName(), 
            library.getDescription(), 
            library.getActive(),
            library.getCreatedAt()
        );
    }

    public Library toEntity(LibraryDTO libraryDTO){
        Library library = new Library();
        library.setId(libraryDTO.id());
        library.setName(libraryDTO.name());
        library.setDescription(libraryDTO.description());
        library.setActive(libraryDTO.active());
        return library;
    }
}
