package spribe.api.tests.player.get;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdRequestDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdResponseDto;
import spribe.api.player.requests.GetPlayerByPlayerIdRequest;
import spribe.api.tests.MethodNotAllowedTests;
import spribe.api.tests.player.BasePlayerTest;
import spribe.helpers.ResponsiveMapper;

import static spribe.config.TestGroups.*;

@Log4j2
public class GetPlayerTests extends BasePlayerTest implements MethodNotAllowedTests {

    @Test(groups = {ALL, PLAYER, PLAYER_GET, POSITIVE, SMOKE})
    public void getPlayerByPlayerIdSchemaTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer();
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(createdPlayer.getId().toString()).build();
        validateSchema(new GetPlayerByPlayerIdRequest(), playerGetByIdRequestDto,
                "json.schemas/player/GetPlayerByPlayerIdPositiveSchema.json");
    }

    @Test(groups = {ALL, PLAYER, PLAYER_GET, NEGATIVE, METHOD_NOT_ALLOWED})
    public void methodNotAllowedTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer();
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(createdPlayer.getId().toString()).build();
        assertMethodNotAllowed(new GetPlayerByPlayerIdRequest(), playerGetByIdRequestDto);
    }

    @Issue("ISSUE-008")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, POSITIVE, SMOKE})
    public void getPlayerByPlayerIdTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer();
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(createdPlayer.getId().toString()).build();
        Response response = new GetPlayerByPlayerIdRequest().call(playerGetByIdRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");
        PlayerGetByPlayerIdResponseDto playerResponseById = ResponsiveMapper.map(response, PlayerGetByPlayerIdResponseDto.class);

        Allure.step("Assert player fields after getting by id", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(playerResponseById.getAge(), createdPlayer.getAge(), "age isn't equals");
            softAssert.assertEquals(playerResponseById.getGender(), createdPlayer.getGender(), "gender isn't equals");
            softAssert.assertEquals(playerResponseById.getId(), createdPlayer.getId(), "id isn't equals");
            softAssert.assertEquals(playerResponseById.getLogin(), createdPlayer.getLogin(), "login isn't equals");
            softAssert.assertEquals(playerResponseById.getPassword(), createdPlayer.getPassword(), "password isn't equals");
            softAssert.assertEquals(playerResponseById.getRole(), createdPlayer.getRole(), "role isn't equals");
            softAssert.assertEquals(playerResponseById.getScreenName(), createdPlayer.getScreenName(), "screenName isn't equals");
            softAssert.assertAll();
        });
    }

    @Issue("ISSUE-010")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, NEGATIVE},
            dataProvider = "invalidIdNumberParameters", dataProviderClass = GetPlayerDataProvider.class)
    public void getNonExistentPlayerByIdTest(Long id) {
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(id.toString()).build();
        assertValidStatusCodeAndContentType(new GetPlayerByPlayerIdRequest(),
                playerGetByIdRequestDto, HttpStatus.SC_NOT_FOUND);
    }

    @Issue("ISSUE-009")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, NEGATIVE},
            dataProvider = "invalidIdStringParameters", dataProviderClass = GetPlayerDataProvider.class)
    public void getPlayerByNotValidPlayerIdTest(String id) {
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(id).build();
        assertValidStatusCodeAndContentType(new GetPlayerByPlayerIdRequest(),
                playerGetByIdRequestDto, HttpStatus.SC_BAD_REQUEST);
    }
}
