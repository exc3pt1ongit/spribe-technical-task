package spribe.api.tests.player.get;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdRequestDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdResponseDto;
import spribe.api.player.requests.PlayerGetByPlayerIdRequest;
import spribe.api.tests.AbstractBaseTest;
import spribe.utils.ApiResponseMapper;
import spribe.utils.PlayerUtils;

import static spribe.config.TestGroups.*;

public class GetPlayerTests extends AbstractBaseTest {

    @Step("Get player")
    @Test(groups = {ALL, PLAYER, PLAYER_GET, POSITIVE})
    public void getPlayerByIdTest() {
        PlayerCreateResponseDto createdPlayer = PlayerUtils.createPlayer();
        PlayerGetByPlayerIdRequestDto playerGetByIdRequestDto = PlayerGetByPlayerIdRequestDto.builder()
                .playerId(createdPlayer.getId()).build();
        Response response = new PlayerGetByPlayerIdRequest().call(playerGetByIdRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");
        PlayerGetByPlayerIdResponseDto playerResponseById = ApiResponseMapper.map(response, PlayerGetByPlayerIdResponseDto.class);

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
}
