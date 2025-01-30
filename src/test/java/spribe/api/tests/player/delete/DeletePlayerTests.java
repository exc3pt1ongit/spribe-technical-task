package spribe.api.tests.player.delete;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.delete.PlayerDeleteRequestDto;
import spribe.api.player.requests.DeletePlayerRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.api.tests.player.CheckPlayerGrantedPermissionsTests;
import spribe.utils.models.PlayerRole;

import static spribe.config.TestGroups.*;

public class DeletePlayerTests extends BasePlayerTest implements CheckPlayerGrantedPermissionsTests {

    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, POSITIVE, SMOKE})
    public void deletePlayerWithSupervisorAuthorities() {
        PlayerResponseDto player = findSupervisor();
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        new DeletePlayerRequest(player.getLogin())
                .call(deleteRequestDto)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, POSITIVE},
            dataProvider = "rolesWithAvailablePermissionsToMutate", dataProviderClass = DeletePlayerDataProvider.class)
    public void playerPermissionsAvailableTest(PlayerRole role) {
        PlayerCreateResponseDto player = createPlayer(role);
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        new DeletePlayerRequest(player.getLogin())
                .call(deleteRequestDto)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, NEGATIVE},
            dataProvider = "rolesWithNotAvailablePermissionsToMutate", dataProviderClass = DeletePlayerDataProvider.class)
    public void playerPermissionsNotAvailableTest(PlayerRole role) {
        PlayerCreateResponseDto player = createPlayer(role);
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        new DeletePlayerRequest(player.getLogin())
                .call(deleteRequestDto)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
