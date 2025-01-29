package spribe.api.player.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;
import spribe.api.player.dto.create.PlayerCreateRequestDto;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class PlayerCreateRequest extends AbstractBaseRequest {

    private final String editor;

    public PlayerCreateRequest(String editor) {
        super(PlayerControllerEndpoints.CREATE.getValue());
        this.editor = editor;
    }

    @Step("Create player")
    @Override
    public Response call(RequestDto requestDto) {
        PlayerCreateRequestDto playerCreateRequestDto = (PlayerCreateRequestDto) requestDto;
        log.info("create player");

        return requestConfig()
                .pathParam("editor", editor)
                .queryParams(toQueryParameterMap(playerCreateRequestDto))
                .when()
                .log().all()
                .get();
    }

    private Map<String, Object> toQueryParameterMap(PlayerCreateRequestDto playerCreateRequestDto) {

        Map<String, Object> playerQueryParameters = new HashMap<>();

        playerQueryParameters.put("age", playerCreateRequestDto.getAge());
        playerQueryParameters.put("gender", playerCreateRequestDto.getGender());
        playerQueryParameters.put("login", playerCreateRequestDto.getLogin());
        playerQueryParameters.put("password", playerCreateRequestDto.getPassword());
        playerQueryParameters.put("role", playerCreateRequestDto.getRole());
        playerQueryParameters.put("screenName", playerCreateRequestDto.getScreenName());

        return playerQueryParameters;
    }
}
