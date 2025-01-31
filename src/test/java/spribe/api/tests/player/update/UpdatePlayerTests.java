package spribe.api.tests.player.update;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.update.PlayerUpdateRequestDto;
import spribe.api.player.dto.update.PlayerUpdateResponseDto;
import spribe.api.player.requests.UpdatePlayerByIdRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.api.tests.player.CheckPlayerGrantedPermissionsTests;
import spribe.helpers.ResponsiveMapper;
import spribe.helpers.models.PlayerGender;
import spribe.helpers.models.PlayerRole;

import static spribe.config.TestGroups.*;
import static spribe.helpers.PlayerMapper.fromCreateResponseDto;

@Log4j2
public class UpdatePlayerTests extends BasePlayerTest implements CheckPlayerGrantedPermissionsTests {

    @Test(groups = {ALL, PLAYER, PLAYER_UPDATE, POSITIVE, SCHEMA, SMOKE})
    public void updatePlayerSchemaTest() {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerResponseDto createdPlayer = fromCreateResponseDto(createPlayer(PlayerRole.USER));
        PlayerUpdateRequestDto updateRequestDto = buildFullUpdateRequestDto(createdPlayer);
        validateSchema(new UpdatePlayerByIdRequest(supervisor.getLogin(), createdPlayer.getId()),
                updateRequestDto, "json.schemas/player/UpdatePlayerPositiveSchema.json");
    }

    @Test(groups = {ALL, PLAYER, PLAYER_UPDATE, POSITIVE, SCHEMA},
            dataProvider = "rolesWithAvailablePermissionsToMutate", dataProviderClass = UpdatePlayerDataProvider.class)
    public void playerPermissionsAvailableTest(PlayerRole role) {
        PlayerResponseDto player = fromCreateResponseDto(createPlayer(role));
        PlayerResponseDto createdPlayer = fromCreateResponseDto(createPlayer(PlayerRole.USER));
        PlayerUpdateRequestDto updateRequestDto = buildFullUpdateRequestDto(createdPlayer);
        validateSchema(new UpdatePlayerByIdRequest(player.getLogin(), createdPlayer.getId()),
                updateRequestDto, "json.schemas/player/UpdatePlayerPositiveSchema.json");
    }

    @Issue("ISSUE-002")
    @Test(groups = {ALL, PLAYER, PLAYER_UPDATE, NEGATIVE, SCHEMA},
            dataProvider = "rolesWithNotAvailablePermissionsToMutate", dataProviderClass = UpdatePlayerDataProvider.class)
    public void playerPermissionsNotAvailableTest(PlayerRole role) {
        PlayerResponseDto player = fromCreateResponseDto(createPlayer(role));
        PlayerResponseDto createdPlayer = fromCreateResponseDto(createPlayer(PlayerRole.USER));
        PlayerUpdateRequestDto updateRequestDto = buildFullUpdateRequestDto(createdPlayer);
        assertValidStatusCodeAndContentType(new UpdatePlayerByIdRequest(player.getLogin(), createdPlayer.getId()),
                updateRequestDto, HttpStatus.SC_FORBIDDEN);
    }

    @Issue("ISSUE-004")
    @Test(groups = {ALL, PLAYER, PLAYER_UPDATE, POSITIVE, SMOKE})
    public void updatePlayerWithValidFullMutatedValuesTest() {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerResponseDto createdPlayer = fromCreateResponseDto(createPlayer(PlayerRole.USER));
        PlayerUpdateRequestDto updateRequestDto = buildFullUpdateRequestDto(createdPlayer);
        Response response = new UpdatePlayerByIdRequest(supervisor.getLogin(), createdPlayer.getId()).call(updateRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "player is not updated successfully");
        PlayerUpdateResponseDto updatedPlayer = ResponsiveMapper.map(response, PlayerUpdateResponseDto.class);
        assertPlayerUpdateResponse(updatedPlayer, updateRequestDto, createdPlayer);
    }

    @Issue("ISSUE-005")
    @Test(groups = {ALL, PLAYER, PLAYER_UPDATE, POSITIVE},
            dataProvider = "updatePlayerAvailableMutations", dataProviderClass = UpdatePlayerDataProvider.class)
    public void updatePlayerWithValidMutateValueTest(PlayerUpdateRequestDto updateRequestDto) {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerResponseDto createdPlayer = fromCreateResponseDto(createPlayer(PlayerRole.USER));
        Response response = new UpdatePlayerByIdRequest(supervisor.getLogin(), createdPlayer.getId()).call(updateRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "player is not updated successfully");
        PlayerUpdateResponseDto updatedPlayer = ResponsiveMapper.map(response, PlayerUpdateResponseDto.class);
        assertPlayerUpdateResponse(updatedPlayer, updateRequestDto, createdPlayer);
    }

    @Issue("ISSUE-003")
    @Test(groups = {ALL, PLAYER, PLAYER_UPDATE, NEGATIVE, SMOKE})
    public void updatePlayerWithNonGrantedUserRoleTest() {
        PlayerResponseDto nonGrantedPlayer = fromCreateResponseDto(createPlayer(PlayerRole.USER));
        PlayerResponseDto createdPlayer = fromCreateResponseDto(createPlayer(PlayerRole.ADMIN));
        PlayerUpdateRequestDto updateRequestDto = buildFullUpdateRequestDto(createdPlayer);
        assertValidStatusCodeAndContentType(new UpdatePlayerByIdRequest(nonGrantedPlayer.getLogin(), createdPlayer.getId()),
                updateRequestDto, HttpStatus.SC_FORBIDDEN);
    }

    private void assertPlayerUpdateResponse(PlayerUpdateResponseDto actual,
                                            PlayerUpdateRequestDto updated,
                                            PlayerResponseDto created) {
        Allure.step("Assert player fields after update", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actual.getId(), created.getId(), "id after update isn't equals");
            softAssert.assertEquals(actual.getAge(), updated.getAge(), "age after update isn't equals");
            softAssert.assertEquals(actual.getGender(), updated.getGender(), "gender after update isn't equals");
            softAssert.assertEquals(actual.getRole(), updated.getRole(), "role after update isn't equals");
            softAssert.assertEquals(actual.getLogin(), created.getLogin(), "login after update isn't equals");
            softAssert.assertEquals(actual.getScreenName(), created.getScreenName(), "screenName after update isn't equals");
            softAssert.assertAll();
        });
    }

    private PlayerUpdateRequestDto buildFullUpdateRequestDto(PlayerResponseDto createdPlayer) {
        return PlayerUpdateRequestDto.builder()
                .age(19)
                .gender(PlayerGender.MALE.getValue())
                .role(PlayerRole.ADMIN.getValue())
                .login("upd_" + createdPlayer.getLogin())
                .password("upd_" + createdPlayer.getPassword())
                .screenName("upd_" + createdPlayer.getScreenName())
                .build();
    }
}
