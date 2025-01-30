package spribe.api.tests.player.create;

import io.qameta.allure.Step;
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
import spribe.utils.ResponsiveMapper;
import spribe.utils.models.PlayerRole;

import static spribe.config.TestGroups.*;

@Log4j2
public class CreatePlayerTests extends BasePlayerTest {

    @Step("Create player")
    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, POSITIVE})
    public void createPlayerTest() {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerCreateRequestDto playerCreateRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);

        Response response = new CreatePlayerRequest(supervisor.getLogin()).call(playerCreateRequestDto);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "player not created successfully");
        PlayerResponseDto playerResponseDto = ResponsiveMapper.map(response, PlayerResponseDto.class);

        log.info("assert player after create");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(playerResponseDto.getAge(), playerCreateRequestDto.getAge(), "age is incorrect");
        softAssert.assertEquals(playerResponseDto.getGender(), playerCreateRequestDto.getGender(), "gender is incorrect");
        softAssert.assertEquals(playerResponseDto.getLogin(), playerCreateRequestDto.getLogin(), "login is incorrect");
        softAssert.assertEquals(playerResponseDto.getPassword(), playerCreateRequestDto.getPassword(), "password is incorrect");
        softAssert.assertEquals(playerResponseDto.getRole(), playerCreateRequestDto.getRole(), "role is incorrect");
        softAssert.assertEquals(playerResponseDto.getScreenName(), playerCreateRequestDto.getScreenName(), "screenName is incorrect");
        softAssert.assertAll();
    }

    @Step("Create with invalid age")
    @Test(groups = {ALL, PLAYER, PLAYER_CREATE, NEGATIVE},
            dataProvider = "invalidAgeParameters", dataProviderClass = CreatePlayerDataProvider.class)
    public void createPlayerWithInvalidAgeTest(Integer age) {
        PlayerResponseDto supervisor = findSupervisor();
        PlayerCreateRequestDto playerRequestDto = buildPlayerCreateRequestDto(PlayerRole.USER, Boolean.TRUE);
        playerRequestDto.setAge(age);
        new CreatePlayerRequest(supervisor.getLogin())
                .call(playerRequestDto)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
