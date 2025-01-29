package spribe.api.player.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class PlayerUpdateRequest extends AbstractBaseRequest {

    private final Integer playerId;
    private final String editor;

    public PlayerUpdateRequest(String editor, Integer playerId) {
        super(PlayerControllerEndpoints.UPDATE.getValue());
        this.playerId = playerId;
        this.editor = editor;
    }

    @Step("Update player")
    @Override
    public Response call(RequestDto requestDto) {
        log.info("updating player {} with editor {}", playerId, editor);
        return requestConfig()
                .pathParam("editor", editor)
                .pathParam("id", playerId)
                .body(requestDto)
                .when()
                .log().all()
                .patch();
    }
}
