package spribe.api.tests.player.get;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdRequestDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdResponseDto;
import spribe.api.player.requests.PlayerGetByPlayerIdRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.utils.ApiResponseMapper;

import static spribe.config.TestGroups.*;

@Log4j2
public class GetPlayerTests extends BasePlayerTest {

    @Step("Get player with valid id")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, POSITIVE})
    public void getPlayerByIdTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer();
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(createdPlayer.getId().toString()).build();
        Response response = new PlayerGetByPlayerIdRequest().call(playerGetByIdRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");
        PlayerGetByPlayerIdResponseDto playerResponseById = ApiResponseMapper.map(response, PlayerGetByPlayerIdResponseDto.class);

        log.info("get player with valid id");
        
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(playerResponseById.getAge(), createdPlayer.getAge(), "age isn't equals");
        softAssert.assertEquals(playerResponseById.getGender(), createdPlayer.getGender(), "gender isn't equals");
        softAssert.assertEquals(playerResponseById.getId(), createdPlayer.getId(), "id isn't equals");
        softAssert.assertEquals(playerResponseById.getLogin(), createdPlayer.getLogin(), "login isn't equals");
        softAssert.assertEquals(playerResponseById.getPassword(), createdPlayer.getPassword(), "password isn't equals");
        softAssert.assertEquals(playerResponseById.getRole(), createdPlayer.getRole(), "role isn't equals");
        softAssert.assertEquals(playerResponseById.getScreenName(), createdPlayer.getScreenName(), "screenName isn't equals");

        softAssert.assertAll();
    }

    @Step("Get player with invalid id (number)")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, NEGATIVE},
            dataProvider = "invalidIdNumberParameters", dataProviderClass = GetPlayerDataProvider.class)
    public void getNonExistentPlayerByIdTest(Long id) {
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(id.toString()).build();
        new PlayerGetByPlayerIdRequest().call(playerGetByIdRequestDto)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Step("Get player with invalid id (string)")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, NEGATIVE},
            dataProvider = "invalidIdStringParameters", dataProviderClass = GetPlayerDataProvider.class)
    public void getPlayerByNotValidPlayerIdTest(String id) {
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(id).build();
        new PlayerGetByPlayerIdRequest().call(playerGetByIdRequestDto)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
