package spribe.api.tests.player.create;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateRequestDto;
import spribe.api.player.requests.CreatePlayerRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.api.tests.player.CheckPlayerGrantedPermissionsTests;
import spribe.config.ResponsiveDataContainer;
import spribe.helpers.ResponsiveMapper;
import spribe.helpers.models.PlayerRole;

import static spribe.config.TestGroups.*;
import static spribe.helpers.PlayerMapper.fromCreateResponseDto;

@Log4j2
public class CreatePlayerTests extends BasePlayerTest implements CheckPlayerGrantedPermissionsTests {

    @Issue("ISSUE-007")
    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, POSITIVE, SCHEMA, SMOKE})
    public void createPlayerSchemaTest() {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerCreateRequestDto playerCreateRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);
        validateSchema(new CreatePlayerRequest(supervisor.getLogin()), playerCreateRequestDto,
                "json.schemas/player/CreatePlayerPositiveSchema.json");
    }

    @Issue("ISSUE-007")
    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, POSITIVE, SCHEMA},
            dataProvider = "rolesWithAvailablePermissionsToMutate", dataProviderClass = CreatePlayerDataProvider.class)
    public void playerPermissionsAvailableTest(PlayerRole role) {
        PlayerResponseDto player = fromCreateResponseDto(createPlayer(role));
        PlayerCreateRequestDto playerCreateRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);
        validateSchema(new CreatePlayerRequest(player.getLogin()), playerCreateRequestDto,
                "json.schemas/player/CreatePlayerPositiveSchema.json");
    }

    @Issue("ISSUE-007")
    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, NEGATIVE, SCHEMA},
            dataProvider = "rolesWithNotAvailablePermissionsToMutate", dataProviderClass = CreatePlayerDataProvider.class)
    public void playerPermissionsNotAvailableTest(PlayerRole role) {
        PlayerResponseDto player = fromCreateResponseDto(createPlayer(role));
        PlayerCreateRequestDto playerCreateRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);
        assertValidStatusCode(new CreatePlayerRequest(player.getLogin()),
                playerCreateRequestDto, HttpStatus.SC_FORBIDDEN);
    }

    @Issue("ISSUE-007")
    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, POSITIVE})
    public void createPlayerTest() {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerCreateRequestDto playerCreateRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);

        Response response = new CreatePlayerRequest(supervisor.getLogin()).call(playerCreateRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "player not created successfully");
        PlayerResponseDto playerResponseDto = ResponsiveMapper.map(response, PlayerResponseDto.class);
        ResponsiveDataContainer.getInstance().addAffectedPlayerId(playerResponseDto.getId());

        Allure.step("Assert player fields after create", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(playerResponseDto.getAge(), playerCreateRequestDto.getAge(), "age is incorrect");
            softAssert.assertEquals(playerResponseDto.getGender(), playerCreateRequestDto.getGender(), "gender is incorrect");
            softAssert.assertEquals(playerResponseDto.getLogin(), playerCreateRequestDto.getLogin(), "login is incorrect");
            softAssert.assertEquals(playerResponseDto.getPassword(), playerCreateRequestDto.getPassword(), "password is incorrect");
            softAssert.assertEquals(playerResponseDto.getRole(), playerCreateRequestDto.getRole(), "role is incorrect");
            softAssert.assertEquals(playerResponseDto.getScreenName(), playerCreateRequestDto.getScreenName(), "screenName is incorrect");
            softAssert.assertAll();
        });
    }

    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, NEGATIVE},
            dataProvider = "invalidAgeParameters", dataProviderClass = CreatePlayerDataProvider.class)
    public void createPlayerWithInvalidAgeTest(Integer age) {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerCreateRequestDto playerRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);
        playerRequestDto.setAge(age);
        assertValidStatusCode(new CreatePlayerRequest(supervisor.getLogin()),
                playerRequestDto, HttpStatus.SC_BAD_REQUEST);
    }
}
