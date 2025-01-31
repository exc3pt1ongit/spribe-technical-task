package spribe.api.tests.player.delete;

import io.qameta.allure.Issue;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.delete.PlayerDeleteRequestDto;
import spribe.api.player.requests.DeletePlayerRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.api.tests.player.CheckPlayerGrantedPermissionsTests;
import spribe.helpers.models.PlayerRole;

import static spribe.config.TestGroups.*;

public class DeletePlayerTests extends BasePlayerTest implements CheckPlayerGrantedPermissionsTests {

    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, POSITIVE, SMOKE})
    public void deletePlayerWithSupervisorAuthorities() {
        PlayerResponseDto player = findSupervisor();
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        assertValidStatusCode(new DeletePlayerRequest(player.getLogin()),
                deleteRequestDto, HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, POSITIVE},
            dataProvider = "rolesWithAvailablePermissionsToMutate", dataProviderClass = DeletePlayerDataProvider.class)
    public void playerPermissionsAvailableTest(PlayerRole role) {
        PlayerCreateResponseDto player = createPlayer(role);
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        assertValidStatusCode(new DeletePlayerRequest(player.getLogin()),
                deleteRequestDto, HttpStatus.SC_NO_CONTENT);
    }

    @Issue("ISSUE-001")
    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, NEGATIVE},
            dataProvider = "rolesWithNotAvailablePermissionsToMutate", dataProviderClass = DeletePlayerDataProvider.class)
    public void playerPermissionsNotAvailableTest(PlayerRole role) {
        PlayerCreateResponseDto player = createPlayer(role);
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        assertValidStatusCode(new DeletePlayerRequest(player.getLogin()),
                deleteRequestDto, HttpStatus.SC_FORBIDDEN);
    }
}
