package com.comeon.gamereviewservice.v1.service;

import com.comeon.gamereviewservice.exceptions.ResourceNotFoundException;
import com.comeon.gamereviewservice.exceptions.ValidationException;
import com.comeon.gamereviewservice.v1.dtos.PlayerRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.PlayerResponse;
import com.comeon.gamereviewservice.v1.mapper.PlayerMapperImpl;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import com.comeon.gamereviewservice.v1.repository.PlayerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService service;

    @BeforeEach
    public void setUp() {
        service = new PlayerService(playerRepository, new PlayerMapperImpl());
    }

    @Test
    @DisplayName("Taking pageNo, pageSize should return PlayerResponse Object List")
    void getAllPlayersTest() {
        Integer pageNo = 0;
        Integer pageSize = 10;
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());

        List<PlayerEntity> playerEntities = List.of(PlayerEntity.builder().firstName("Palash").build());
        when(playerRepository.findBy(paging)).thenReturn(new SliceImpl<>(playerEntities));

        List<PlayerResponse> playerResponses = service.getAllPlayers(pageNo, pageSize);
        assertEquals(1, playerResponses.size());
    }

    @Test
    @DisplayName("Test when playerId is present, should return the Player entity.")
    void getPlayerById_when_present_should_return_entity_Test() {
        Long existPlayerId = 10l;
        when(playerRepository.findById(existPlayerId)).thenReturn(Optional.of(PlayerEntity.builder().playerId(existPlayerId).firstName("Palash").build()));
        PlayerResponse playerResponse = service.getPlayerById(existPlayerId);
        Assertions.assertNotNull(playerResponse);
    }

    @Test
    @DisplayName("When gameId is not present then should return resource not found exception with message")
    void getPlayerById_when_notPresent_should_return_exceptionThrow_Test() {
        Long notExistPlayerId = 100l;
        when(playerRepository.findById(notExistPlayerId)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> service.getPlayerById(notExistPlayerId));
        assertEquals("No player match was found by player id [" + notExistPlayerId + "]", resourceNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Player email is unique. DB should not allow to insert duplicate player email. This method should return validation exception")
    void addNewPlayer_return_validation_exception_when_playerEmail_existTest() {
        String email = "example@example.com";
        when(playerRepository.existsByEmail(email)).thenReturn(true);
        ValidationException validationException = assertThrows(ValidationException.class, () -> service.addNewPlayer(PlayerRequestPayload.builder().emailAddress(email).build()));
        assertEquals("Player email [{" + email + "}] is already in use", validationException.getMessage());
    }

}
