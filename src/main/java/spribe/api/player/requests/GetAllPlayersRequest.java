package spribe.api.player.requests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class GetAllPlayersRequest extends AbstractBaseRequest {

    public GetAllPlayersRequest() {
        super(PlayerControllerEndpoints.GET_ALL.getValue());
    }

    @Override
    public Response call(RequestDto requestDto) {
        return Allure.step("Get all players list by request", () -> {
            log.info("get all players list");
            return requestConfig()
                    .when()
                    .log().all()
                    .get();
        });
    }

    public Response call() {
        return call(null);
    }

    @Override
    public Response methodNotAllowed(RequestDto requestDto) {
        return Allure.step("Get all players list by request (with not allowed method)", () ->
                requestConfig()
                        .when()
                        .log().all()
                        .delete()
        );
    }
}
