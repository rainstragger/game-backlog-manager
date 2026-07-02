package com.rainstragger.game_backlog_backend.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.rainstragger.game_backlog_backend.repository.GamesRepository;
import com.rainstragger.game_backlog_backend.entities.Games;

@Service
public class GamesService {

    private final GamesRepository gamesRepository;

    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public Optional<List<Games>> findAll(boolean active) {
        return gamesRepository.findAllByActive(active);
    }

    public Optional<Games> findById(Integer id) {
        return gamesRepository.findById(id);
    }

    public Optional<Games> findByName(String name) {
        return gamesRepository.findByName(name);
    }

    public Optional<List<Games>> findByTerm(String term) {
        return gamesRepository.findByTerm(term);
    }

    public Games create(Games game) {
        if (game.getId() != null) {
            throw new IllegalArgumentException("Game already exists!");
        }else{
            if (game.getActive() == null) {
            game.setActive(true);
            }
            return gamesRepository.save(game);
        }
        
    }

    public Games update(Integer id, Games games) {
		Games existent = findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Game not found!"));

		existent.setName(games.getName());
        existent.setActive(games.getActive());
        existent.setResume(games.getResume());
        existent.setLaunchDate(games.getLaunchDate());
        existent.setDeveloper(games.getDeveloper());
        existent.setPublisher(games.getPublisher());
        existent.setGenre(games.getGenre());
        existent.setMinReq(games.getMinReq());
        existent.setRecomReq(games.getRecomReq());
        existent.setCoverId(games.getCoverId());

		return gamesRepository.save(existente);
	}

    public void remove(Integer id) {
        Games game = findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Game not found!"));
        
        //Soft Delete
        game.setActive(false);
        gamesRepository.save(game);

    }
}
