package com.rainstragger.game_backlog_backend.services;

import com.rainstragger.game_backlog_backend.model.Library;
import com.rainstragger.game_backlog_backend.repository.LibraryRepository;
import org.springframework.stereotype.Service;

@Service
public class LibraryService implements LibraryRepository {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Optional<List<Library>> findAll (boolean active){
        return libraryRepository.findAllByActive(active);
    }

    public Optional<List<Library>> findByTerm (String term){
        return libraryRepository.findByTerm(term);
    }

    public Optional<Library> findById(Integer id) {
        return libraryRepository.findById(id);
    }

    public Optional<Library> findByName(String name) {
        return libraryRepository.findByName(name);
    }

    public Library create (Library library){
        if (library.getId() != null) {
            throw new IllegalArgumentException("Library already exists!");
        }else{
            if (library.getActive() == null) {
                library.setActive(true);
            }
            return libraryRepository.save(library);
        }
    }

    public Library update (Library library){
        Library existent = findById(library.getId())
                .orElseThrow(() -> new IllegalArgumentException("Library not found!"));
        existent.setName(library.getName());
        existent.setDescription(library.getDescription());
        existent.setActive(library.getActive());
        return libraryRepository.save(existent);
    }

    public Library remove (Library library){
        Library library = findById(library.getId())
                .orElseThrow(() -> new IllegalArgumentException("Library not found!"));
        library.setActive(false);
        return libraryRepository.save(library);
    }
}
