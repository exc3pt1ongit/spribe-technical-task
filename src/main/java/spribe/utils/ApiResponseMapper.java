package spribe.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ApiResponseMapper {
    @Step("Map API response")
    public static <T> T map(Response response, Class<T> type) {
        log.info("map {}", type.getName());
        return response.as(type);
    }

    @Step("Map API response by path")
    public static <T> List<T> map(Response response, String path, Class<T> tClass) {
        log.info("map list of {} by path '{}'", tClass.getName(), path);
        return response.jsonPath().getList(path, tClass);
    }
}
