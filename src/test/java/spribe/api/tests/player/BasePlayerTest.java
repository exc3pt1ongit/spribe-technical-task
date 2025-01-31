package spribe.api.tests.player;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateRequestDto;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdRequestDto;
import spribe.api.player.dto.get.all.PlayerItemResponseDto;
import spribe.api.player.requests.CreatePlayerRequest;
import spribe.api.player.requests.GetPlayerByPlayerIdRequest;
import spribe.api.tests.AbstractBaseTest;
import spribe.config.ResponsiveDataContainer;
import spribe.data.entity.GrantedPlayer;
import spribe.helpers.ResponsiveMapper;
import spribe.helpers.models.PlayerGender;
import spribe.helpers.models.PlayerRole;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Predicate;

@Log4j2
public class BasePlayerTest extends AbstractBaseTest {

    protected PlayerResponseDto findSupervisor() {
        return Allure.step("Find supervisor", () -> {
            log.info("find supervisor");
            GrantedPlayer supervisor = grantedPlayerDataSource.getPlayerByRole(PlayerRole.SUPERVISOR);

            PlayerGetByPlayerIdRequestDto request = PlayerGetByPlayerIdRequestDto.builder()
                    .playerId(supervisor.getId().toString())
                    .build();
            Response supervisorResponse = new GetPlayerByPlayerIdRequest().call(request);

            return ResponsiveMapper.map(supervisorResponse, PlayerResponseDto.class);
        });
    }

    protected PlayerItemResponseDto findPlayer(List<PlayerItemResponseDto> players, Predicate<PlayerItemResponseDto> predicate) {
        return Allure.step("Find player", () -> players
                .stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No player found by predicate")));
    }

    protected PlayerCreateResponseDto createPlayer() {
        return createPlayer(PlayerRole.ADMIN);
    }

    protected PlayerCreateResponseDto createPlayer(PlayerRole role) {
        return Allure.step("Create player", () -> {
            PlayerCreateRequestDto createPlayerRequestDto = buildPlayerCreateRequestDto(role, Boolean.FALSE);
            PlayerResponseDto supervisor = findSupervisor();
            Response response = new CreatePlayerRequest(supervisor.getLogin()).call(createPlayerRequestDto);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");
            PlayerCreateResponseDto createdPlayer = ResponsiveMapper.map(response, PlayerCreateResponseDto.class);
            ResponsiveDataContainer.getInstance().addAffectedPlayerId(createdPlayer.getId());
            return createdPlayer;
        });
    }

    protected PlayerCreateRequestDto buildPlayerCreateRequestDto(PlayerRole role, Boolean withPassword) {
        return Allure.step("Build player create request dto", () -> {
            String uniqueIdentifier = UUID.randomUUID().toString();
            PlayerCreateRequestDto playerToCreate = PlayerCreateRequestDto.builder()
                    .age(18)
                    .login("testLogin_" + uniqueIdentifier)
                    .password("testPassword_" + uniqueIdentifier)
                    .screenName("testScreenName_" + uniqueIdentifier)
                    .role(role.getValue())
                    .gender(PlayerGender.FEMALE.getValue())
                    .build();
            if (withPassword)
                playerToCreate.setPassword("testPassword_" + uniqueIdentifier);
            return playerToCreate;
        });
    }
}
