package spribe.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateRequestDto;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdRequestDto;
import spribe.api.player.dto.get.all.PlayerItemResponseDto;
import spribe.api.player.requests.PlayerCreateRequest;
import spribe.api.player.requests.PlayerGetAllRequest;
import spribe.api.player.requests.PlayerGetByPlayerIdRequest;
import spribe.utils.models.PlayerGender;
import spribe.utils.models.PlayerRole;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Predicate;

@Log4j2
public class PlayerUtils {

    @Step("Find supervisor")
    public static PlayerResponseDto findSupervisor() {
        log.info("find supervisor");
        Response playersResponse = new PlayerGetAllRequest().call();
        List<PlayerItemResponseDto> players = ApiResponseMapper.map(playersResponse,
                "players", PlayerItemResponseDto.class);

        Integer supervisorId = GrantedPermissionsUtils.findGrantedPermissionsPlayerIdByRole(
                players,
                GrantedPermissionsUtils.Strategy.KNOWN_IDENTIFIERS_IN_DB,
                PlayerRole.SUPERVISOR
        );
//        PlayerItemResponseDto supervisor = findPlayerByPredicate(players,
//                p -> p.getRole().equals(PlayerRole.SUPERVISOR.getValue()));

        PlayerGetByPlayerIdRequestDto request = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(supervisorId)
                .build();
        Response supervisorResponse = new PlayerGetByPlayerIdRequest().call(request);

        return ApiResponseMapper.map(supervisorResponse, PlayerResponseDto.class);
    }

    @Step("Find player")
    protected static PlayerItemResponseDto findPlayerByPredicate(List<PlayerItemResponseDto> players, Predicate<PlayerItemResponseDto> predicate) {
        return players
                .stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Player not found"));
    }

    public static PlayerCreateResponseDto createPlayer() {
        PlayerCreateRequestDto createPlayerRequestDto = buildPlayerCreateRequestDto(Boolean.FALSE);
        PlayerResponseDto supervisor = findSupervisor();
        Response response = new PlayerCreateRequest(supervisor.getLogin()).call(createPlayerRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");
        return ApiResponseMapper.map(response, PlayerCreateResponseDto.class);
    }

    private static PlayerCreateRequestDto buildPlayerCreateRequestDto(Boolean withPassword) {
        String uniqueIdentifier = UUID.randomUUID().toString();
        PlayerCreateRequestDto playerToCreate = PlayerCreateRequestDto.builder()
                .age(18)
                .login("testLogin_" + uniqueIdentifier)
                .password("testPassword_" + uniqueIdentifier)
                .screenName("testScreenName_" + uniqueIdentifier)
                .role(PlayerRole.ADMIN.getValue())
                .gender(PlayerGender.FEMALE.getValue())
                .build();
        if (withPassword)
            playerToCreate.setPassword("testPassword_" + uniqueIdentifier);
        return playerToCreate;
    }
}
