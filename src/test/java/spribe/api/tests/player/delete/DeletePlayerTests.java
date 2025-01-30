package spribe.api.tests.player.delete;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.delete.PlayerDeleteRequestDto;
import spribe.api.player.requests.PlayerDeleteRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.data.GrantedPlayers;
import spribe.utils.models.PlayerRole;

import static spribe.config.TestGroups.*;

public class DeletePlayerTests extends BasePlayerTest {

    @Step("Delete player with granted permissions")
    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, POSITIVE},
            dataProvider = "grantedPlayersList", dataProviderClass = DeletePlayerDataProvider.class)
    public void deletePlayerWithGrantedUserRoleTest(GrantedPlayers player) {
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        new PlayerDeleteRequest(player.getLogin())
                .call(deleteRequestDto)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Step("Delete player with non-granted permissions")
    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, NEGATIVE})
    public void deletePlayerWithNonGrantedUserRoleTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        new PlayerDeleteRequest(createdPlayer.getLogin())
                .call(deleteRequestDto)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
