package spribe.api.player.requests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class DeletePlayerRequest extends AbstractBaseRequest {

    private final String editor;

    public DeletePlayerRequest(String editor) {
        super(PlayerControllerEndpoints.DELETE.getValue());
        this.editor = editor;
    }

    @Override
    public Response call(RequestDto requestDto) {
        return Allure.step("Delete player by request", () -> {
            log.info("delete player");
            return requestConfig()
                    .pathParam("editor", editor)
                    .body(requestDto)
                    .when()
                    .log().all()
                    .delete();
        });
    }

    @Override
    public Response methodNotAllowed(RequestDto requestDto) {
        return Allure.step("Delete player by request (with not allowed method)", () ->
                requestConfig()
                        .pathParam("editor", editor)
                        .body(requestDto)
                        .when()
                        .log().all()
                        .put()
        );
    }
}
