package spribe.utils;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ResponsiveMapper {

    public static <T> T map(Response response, Class<T> type) {
        return Allure.step("Map API response", () -> {
            log.info("map {}", type.getName());
            return response.as(type);
        });
    }

    public static <T> List<T> map(Response response, String path, Class<T> tClass) {
        return Allure.step("Map API response by path", () -> {
            log.info("map list of {} by path '{}'", tClass.getName(), path);
            return response.jsonPath().getList(path, tClass);
        });
    }
}
