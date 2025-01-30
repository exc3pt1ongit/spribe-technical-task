package spribe.api.tests.player.update;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.update.PlayerUpdateRequestDto;
import spribe.api.player.requests.PlayerUpdateRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.utils.ApiResponseMapper;
import spribe.utils.models.PlayerGender;
import spribe.utils.models.PlayerRole;

import static spribe.config.TestGroups.*;

@Log4j2
public class UpdatePlayerTests extends BasePlayerTest {

    @Step("Update player with granted permissions")
    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, POSITIVE})
    public void updatePlayerWithValidValuesTest() {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerUpdateRequestDto updateRequestDto = PlayerUpdateRequestDto.builder()
                .age(19)
                .gender(PlayerGender.MALE.getValue())
                .role(PlayerRole.ADMIN.getValue())
                .login("upd_" + createdPlayer.getLogin())
                .password("upd_" + createdPlayer.getPassword())
                .screenName("upd_" + createdPlayer.getScreenName())
                .build();
        Response response = new PlayerUpdateRequest(supervisor.getLogin(), createdPlayer.getId()).call(updateRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "player is not updated successfully");
        PlayerResponseDto updatedPlayer = ApiResponseMapper.map(response, PlayerResponseDto.class);

        log.info("update player with valid id");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatedPlayer.getId(), createdPlayer.getId(), "id after update isn't equals");
        softAssert.assertEquals(updatedPlayer.getAge(), updateRequestDto.getAge(), "age after update isn't equals");
        softAssert.assertEquals(updatedPlayer.getGender(), updateRequestDto.getGender(), "gender after update isn't equals");
        softAssert.assertEquals(updatedPlayer.getRole(), updateRequestDto.getRole(), "role after update isn't equals");
        softAssert.assertEquals(updatedPlayer.getLogin(), createdPlayer.getLogin(), "login after update isn't equals");
        softAssert.assertEquals(updatedPlayer.getPassword(), createdPlayer.getPassword(), "password after update isn't equals");
        softAssert.assertEquals(updatedPlayer.getScreenName(), createdPlayer.getScreenName(), "screenName after update isn't equals");
        softAssert.assertAll();
    }

    @Step("Update player with non-granted permissions")
    @Test(groups = {ALL, PLAYER, PLAYER_DELETE, NEGATIVE})
    public void updatePlayerWithNonGrantedUserRoleTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer(PlayerRole.USER);
        PlayerUpdateRequestDto updateRequestDto = PlayerUpdateRequestDto.builder()
                .age(19)
                .gender(PlayerGender.MALE.getValue())
                .role(PlayerRole.ADMIN.getValue())
                .login("upd_" + createdPlayer.getLogin())
                .password("upd_" + createdPlayer.getPassword())
                .screenName("upd_" + createdPlayer.getScreenName())
                .build();
        new PlayerUpdateRequest(createdPlayer.getLogin(), createdPlayer.getId())
                .call(updateRequestDto)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
