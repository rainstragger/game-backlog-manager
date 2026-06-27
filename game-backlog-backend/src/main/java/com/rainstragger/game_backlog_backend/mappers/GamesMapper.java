package com.rainstragger.game_backlog_backend.mappers;

import org.springframework.stereotype.Component;

import com.rainstragger.game_backlog_backend.dto.GamesDTO;
import com.rainstragger.game_backlog_backend.entities.Games;

@Component
public class GamesMapper {
    public GamesDTO toDTO(Games game) {
        return new GamesDTO(
                game.getId(),
                game.getName(),
                game.getActive(),
                game.getResume(),
                game.getLaunchDate(),
                game.getDeveloper(),
                game.getPublisher(),
                game.getGenre(),
                game.getMinReq(),
                game.getRecomReq(),
                game.getCoverId(),
                game.getCreatedAt()
        );
    }

    public Games toEntity(GamesDTO gameDTO) {
        Games game = new Games();
        game.setId(gameDTO.id());
        game.setName(gameDTO.name());
        game.setActive(gameDTO.active());
        game.setResume(gameDTO.resume());
        game.setLaunchDate(gameDTO.launchDate());
        game.setDeveloper(gameDTO.developer());
        game.setPublisher(gameDTO.publisher());
        game.setGenre(gameDTO.genre());
        game.setMinReq(gameDTO.minReq());
        game.setRecomReq(gameDTO.recomReq());
        game.setCoverId(gameDTO.coverId());
        return game;
    }
}
