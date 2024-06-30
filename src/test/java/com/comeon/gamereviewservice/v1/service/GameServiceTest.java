package com.comeon.gamereviewservice.v1.service;

import com.comeon.gamereviewservice.exceptions.ResourceNotFoundException;
import com.comeon.gamereviewservice.exceptions.ValidationException;
import com.comeon.gamereviewservice.v1.dtos.GameRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.mapper.GameMapperImpl;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import com.comeon.gamereviewservice.v1.repository.GameRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService service;

    @BeforeEach
    public void setUp() {
        service = new GameService(gameRepository, new GameMapperImpl());
    }

    @Test
    @DisplayName("Taking pageNo, pageSize should return GameResponse Object List")
    void getAllGamesTest() {
        Integer pageNo = 0;
        Integer pageSize = 10;
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("title").ascending());

        List<GameEntity> gameEntities = List.of(GameEntity.builder().title("Test Game1").build());
        when(gameRepository.findBy(paging)).thenReturn(new SliceImpl<>(gameEntities));

        List<GameResponse> gameResponses = service.getAllGames(pageNo, pageSize);
        assertEquals(1, gameResponses.size());
    }

    @Test
    @DisplayName("Test when gameId is present, should return the Game entity.")
    void getGameById_when_present_should_return_entity_Test() {
        Long existGameId = 10l;
        when(gameRepository.findById(existGameId)).thenReturn(Optional.of(GameEntity.builder().gameId(existGameId).title("Call of duty").build()));
        GameResponse gameResponse = service.getGameById(existGameId);
        Assertions.assertNotNull(gameResponse);
    }

    @Test
    @DisplayName("When gameId is not present then should return resource not found exception with message")
    void getGameById_when_notPresent_should_return_exceptionThrow_Test() {
        Long notExistGameId = 100l;
        when(gameRepository.findById(notExistGameId)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> service.getGameById(notExistGameId));
        assertEquals("No game match was found by game id [" + notExistGameId + "]", resourceNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Game title is unique. DB should not allow to insert duplicate game title. This method should return validation exception")
    void addNewGame_return_validation_exception_when_gameTile_existTest() {
        String gameTitle = "call of duty";
        when(gameRepository.existsByTitle(gameTitle)).thenReturn(true);
        ValidationException validationException = assertThrows(ValidationException.class, () -> service.addNewGame(GameRequestPayload.builder().gameTitle(gameTitle).build()));
        assertEquals("Game Title [{" + gameTitle + "}] is already in use", validationException.getMessage());
    }
}
