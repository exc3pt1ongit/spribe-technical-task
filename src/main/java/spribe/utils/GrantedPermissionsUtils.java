package spribe.utils;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import spribe.api.ValueRetriever;
import spribe.api.player.dto.get.PlayerGetByPlayerIdRequestDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdResponseDto;
import spribe.api.player.dto.get.all.PlayerItemResponseDto;
import spribe.api.player.requests.PlayerGetByPlayerIdRequest;
import spribe.utils.models.DatabaseGrantedPlayers;
import spribe.utils.models.PlayerRole;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GrantedPermissionsUtils {

    public static Integer findGrantedPermissionsPlayerIdByRole(List<PlayerItemResponseDto> players,
                                                               Strategy strategy, PlayerRole role) {
        return findGrantedPermissionPlayerByRole(players, strategy, role)
                .getId();
    }

    public static String findGrantedPermissionsPlayerLoginByRole(List<PlayerItemResponseDto> players,
                                                                 Strategy strategy, PlayerRole role) {
        return findGrantedPermissionPlayerByRole(players, strategy, role)
                .getLogin();
    }

    private static OnlyInteractablePlayerFields findGrantedPermissionPlayerByRole(List<PlayerItemResponseDto> players,
                                                                                  Strategy strategy, PlayerRole role) {
        switch (strategy) {
            case BLIND_IDENTIFICATION:
                return byBlindIdentificationStrategy(players, role);
            case KNOWN_IDENTIFIERS_IN_DB:
                return byKnownIdentifiersStrategy(players, role);
            default:
                throw new IllegalArgumentException("Unsupported strategy: " + strategy);
        }
    }

    private static OnlyInteractablePlayerFields byBlindIdentificationStrategy(List<PlayerItemResponseDto> players, PlayerRole role) {
        Assert.assertNotNull(players, "player should not be null");
        List<Integer> playerIdList = players.stream()
                .map(PlayerItemResponseDto::getId)
                .collect(Collectors.toList());

        List<PlayerGetByPlayerIdResponseDto> playersByIdList = new CopyOnWriteArrayList<>();

        playerIdList.forEach(playerId -> {
            PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                    .playerId(playerId).build();
            Response response = new PlayerGetByPlayerIdRequest().call(playerGetByIdRequestDto);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");
            playersByIdList.add(ApiResponseMapper.map(response, PlayerGetByPlayerIdResponseDto.class));
        });

        PlayerGetByPlayerIdResponseDto unmappedPlayer = playersByIdList.stream()
                .filter(p -> p.getRole().equals(role.getValue()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No player with role " + role.getValue() + " found"));
        return OnlyInteractablePlayerFields.builder()
                .id(unmappedPlayer.getId())
                .login(unmappedPlayer.getLogin())
                .role(unmappedPlayer.getRole())
                .build();
    }

    private static OnlyInteractablePlayerFields byKnownIdentifiersStrategy(List<PlayerItemResponseDto> players, PlayerRole role) {
        DatabaseGrantedPlayers player = Arrays.stream(DatabaseGrantedPlayers.values())
                .filter(entity -> entity.getRole().equals(role))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No granted permissions for player found"));
        Assert.assertTrue(players.stream()
                .map(PlayerItemResponseDto::getId)
                .collect(Collectors.toList())
                .contains(player.getId()));
        return OnlyInteractablePlayerFields.builder()
                .id(player.getId())
                .login(player.getLogin())
                .role(player.getRole().getValue())
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Strategy implements ValueRetriever {
        BLIND_IDENTIFICATION("blind_identification"),
        KNOWN_IDENTIFIERS_IN_DB("known_identifiers_in_db");

        private final String value;
    }

    @Data
    @Builder
    public static class OnlyInteractablePlayerFields {
        private Integer id;
        private String login;
        private String role;
    }
}
