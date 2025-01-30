package spribe.api.tests.player.get.all;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.all.PlayerItemResponseDto;
import spribe.api.player.requests.GetAllPlayersRequest;
import spribe.api.tests.player.BasePlayerTest;
import spribe.utils.ResponsiveMapper;

import java.util.List;

import static spribe.config.TestGroups.*;

@Log4j2
public class GetAllPlayersTests extends BasePlayerTest {

    @Step("Get players")
    @Test(groups = {ALL, PLAYER, PLAYER_GET_ALL, POSITIVE})
    public void getAllPlayersTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer();
        Response response = new GetAllPlayersRequest().call();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");

        List<PlayerItemResponseDto> players = ResponsiveMapper.map(response, "players", PlayerItemResponseDto.class);
        PlayerItemResponseDto player = findPlayer(players, p -> p.getId().equals(createdPlayer.getId()));

        log.info("assert player response");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(player.getAge(), createdPlayer.getAge(), "age is incorrect");
        softAssert.assertEquals(player.getGender(), createdPlayer.getGender(), "gender is incorrect");
        softAssert.assertEquals(player.getRole(), createdPlayer.getRole(), "role is incorrect");
        softAssert.assertEquals(player.getScreenName(), createdPlayer.getScreenName(), "screenName is incorrect");
        softAssert.assertAll();
    }
}
