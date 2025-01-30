package spribe.api.player.requests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import spribe.api.AbstractBaseRequest;
import spribe.api.RequestDto;
import spribe.api.player.PlayerControllerEndpoints;

@Log4j2
public class GetPlayerByPlayerIdRequest extends AbstractBaseRequest {

    public GetPlayerByPlayerIdRequest() {
        super(PlayerControllerEndpoints.GET.getValue());
    }

    @Override
    public Response call(RequestDto requestDto) {
        return Allure.step("Get player by player id by request", () -> {
            log.info("get player by player id");
            return requestConfig()
                    .body(requestDto)
                    .when()
                    .log().all()
                    .post();
        });
    }
}
