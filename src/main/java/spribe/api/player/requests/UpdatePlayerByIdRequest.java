package spribe.api.player.requests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class UpdatePlayerByIdRequest extends AbstractBaseRequest {

    private final Integer playerId;
    private final String editor;

    public UpdatePlayerByIdRequest(String editor, Integer playerId) {
        super(PlayerControllerEndpoints.UPDATE.getValue());
        this.playerId = playerId;
        this.editor = editor;
    }

    @Override
    public Response call(RequestDto requestDto) {
        return Allure.step("Update player by id by request", () -> {
            log.info("updating player {} with editor {}", playerId, editor);
            return requestConfig()
                    .pathParam("editor", editor)
                    .pathParam("id", playerId)
                    .body(requestDto)
                    .when()
                    .log().all()
                    .patch();
        });
    }

    @Override
    public Response methodNotAllowed(RequestDto requestDto) {
        return requestConfig()
                .pathParam("editor", editor)
                .pathParam("id", playerId)
                .body(requestDto)
                .when()
                .log().all()
                .delete();
    }
}
