package spribe.api.player.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class PlayerDeleteRequest extends AbstractBaseRequest {

    private final String editor;

    public PlayerDeleteRequest(String editor) {
        super(PlayerControllerEndpoints.DELETE.getValue());
        this.editor = editor;
    }

    @Step("Delete player")
    @Override
    public Response call(RequestDto requestDto) {
        log.info("delete player");
        return requestConfig()
                .pathParam("editor", editor)
                .body(requestDto)
                .when()
                .log().all()
                .delete();
    }
}
