package spribe.api.player.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class PlayerGetByPlayerIdRequest extends AbstractBaseRequest {

    public PlayerGetByPlayerIdRequest() {
        super(PlayerControllerEndpoints.GET.getValue());
    }

    @Step("Get player by player id")
    @Override
    public Response call(RequestDto requestDto) {
        log.info("get player by player id");
        return requestConfig()
                .body(requestDto)
                .when()
                .log().all()
                .post();
    }
}
