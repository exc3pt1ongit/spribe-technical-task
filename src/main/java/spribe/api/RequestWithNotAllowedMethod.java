package spribe.api;

import io.restassured.response.Response;

public interface RequestWithNotAllowedMethod {
    Response methodNotAllowed(RequestDto requestDto);
}
