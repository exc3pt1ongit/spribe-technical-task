package spribe.api.player.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class PlayerGetAllRequest extends AbstractBaseRequest {

    public PlayerGetAllRequest() {
        super(PlayerControllerEndpoints.GET_ALL.getValue());
    }

    @Step("Get all players list")
    @Override
    public Response call(RequestDto requestDto) {
        log.info("get all players list");
        return requestConfig()
                .when()
                .log().all()
                .get();
    }

    public Response call() {
        return call(null);
    }
}
