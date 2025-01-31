package spribe.api.tests;

import io.qameta.allure.Allure;
import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import spribe.api.Request;
import spribe.api.RequestDto;
import spribe.data.GrantedPlayerDataSource;
import spribe.data.GrantedPlayerDataSourceImpl;
import spribe.data.fetch.EnumGrantedPlayerFetcher;
import spribe.data.fetch.GrantedPlayerFetcher;
import spribe.listeners.ResponsiveExecutionListener;
import spribe.listeners.ResponsiveSuiteListener;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Log4j2
@Listeners({ResponsiveExecutionListener.class, ResponsiveSuiteListener.class})
public abstract class AbstractBaseTest {

    protected final GrantedPlayerFetcher grantedPlayerFetcher;
    protected final GrantedPlayerDataSource grantedPlayerDataSource;

    protected AbstractBaseTest() {
        grantedPlayerFetcher = new EnumGrantedPlayerFetcher();
        grantedPlayerDataSource = new GrantedPlayerDataSourceImpl(grantedPlayerFetcher);
    }

    protected void validateSchema(Request request,
                                  RequestDto requestDto,
                                  String path) {
        validateSchema(request, requestDto, HttpStatus.SC_OK, ContentType.JSON, path);
    }

    protected void validateSchema(Request request,
                                  RequestDto requestDto,
                                  Integer code,
                                  ContentType type,
                                  String path) {
        Allure.step(String.format("Validate JSON schema in path '%s'", path), () -> {
            request.call(requestDto)
                    .then()
                    .statusCode(code)
                    .contentType(type)
                    .body(matchesJsonSchemaInClasspath(path));
        });
    }

    protected void assertValidStatusCodeAndContentType(Request request,
                                                       RequestDto requestDto,
                                                       Integer code,
                                                       ContentType type) {
        Allure.step("Assert valid status code and content type", () -> {
            request.call(requestDto)
                    .then()
                    .statusCode(code)
                    .contentType(type);
        });
    }

    protected void assertValidStatusCodeAndContentType(Request request,
                                                       RequestDto requestDto,
                                                       Integer code) {
        assertValidStatusCodeAndContentType(request, requestDto, code, ContentType.JSON);
    }

    protected void assertValidStatusCode(Request request,
                                         RequestDto requestDto,
                                         Integer code) {
        Allure.step("Assert valid status code", () -> {
            request.call(requestDto)
                    .then()
                    .statusCode(code);
        });
    }
}
