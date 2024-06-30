package com.comeon.gamereviewservice.v1.repository;

import com.comeon.gamereviewservice.enums.EntityStatus;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("Data persist test")
    void saveTest() {
        GameEntity gameEntity = GameEntity.builder().title("Candy Crush").description("Candy Crush is very popular mobile game").status(EntityStatus.ACTIVE).build();
        GameEntity insertedEntity = gameRepository.save(gameEntity);

        Assertions.assertEquals(gameEntity.getTitle(), testEntityManager.find(GameEntity.class, insertedEntity.getGameId()).getTitle());
    }

    @Test
    @DisplayName("This method should return the initial game list which inserted by data.sql, Base on pageable this method should return correct data")
    void findByTest() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("title").ascending());
        Slice<GameEntity> gameEntities = gameRepository.findBy(pageable);
        Assertions.assertEquals(1, gameEntities.getSize());

        pageable = PageRequest.of(0, 2, Sort.by("title").ascending());
        gameEntities = gameRepository.findBy(pageable);
        Assertions.assertEquals(2, gameEntities.getSize());

        // test sorting
        pageable = PageRequest.of(0, 2, Sort.by("title").descending());
        gameEntities = gameRepository.findBy(pageable);
        Assertions.assertEquals("Call of duty", gameEntities.getContent().get(0).getTitle());
    }
}
