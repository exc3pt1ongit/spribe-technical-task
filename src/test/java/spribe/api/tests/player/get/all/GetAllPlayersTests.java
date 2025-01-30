package spribe.api.tests.player.get.all;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
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

    @Issue("ISSUE-006")
    @Test(groups = {ALL, PLAYER, PLAYER_GET_ALL, POSITIVE, SMOKE})
    public void getAllPlayersSchemaTest() {
        validateSchema(new GetAllPlayersRequest(), null,
                "json.schemas/player/GetAllPlayersPositiveSchema.json");
    }

    @Test(groups = {ALL, PLAYER, PLAYER_GET_ALL, POSITIVE})
    public void getAllPlayersTest() {
        PlayerCreateResponseDto createdPlayer = createPlayer();
        Response response = new GetAllPlayersRequest().call();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "status code isn't OK");

        List<PlayerItemResponseDto> players = ResponsiveMapper.map(response, "players", PlayerItemResponseDto.class);
        PlayerItemResponseDto player = findPlayer(players, p -> p.getId().equals(createdPlayer.getId()));

        Allure.step("Assert player fields after getting and filtering", () -> {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(player.getAge(), createdPlayer.getAge(), "age is incorrect");
            softAssert.assertEquals(player.getGender(), createdPlayer.getGender(), "gender is incorrect");
            softAssert.assertEquals(player.getRole(), createdPlayer.getRole(), "role is incorrect");
            softAssert.assertEquals(player.getScreenName(), createdPlayer.getScreenName(), "screenName is incorrect");
            softAssert.assertAll();
        });
    }
}
