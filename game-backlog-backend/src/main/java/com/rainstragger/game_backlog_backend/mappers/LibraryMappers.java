package com.rainstragger.game_backlog_backend.mappers;

import com.rainstragger.game_backlog_backend.dto.LibraryDto;
import com.rainstragger.game_backlog_backend.model.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryMappers {
    public Library toDTO (Library library){
        return new LibraryDTO(
            library.getId(), 
            library.getName(), 
            library.getDescription(), 
            library.getActive());
    }

    public Library toEntity(LibraryDto libraryDto){
        Library library = new Library();
        library.setId(libraryDto.getId());
        library.setName(libraryDto.getName());
        library.setDescription(libraryDto.getDescription());
        library.setActive(libraryDto.getActive());
        return library;
    }
}
