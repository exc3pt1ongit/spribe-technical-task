package spribe.api;

import io.restassured.response.Response;

public interface Request {
    Response call(RequestDto requestDto);
}
