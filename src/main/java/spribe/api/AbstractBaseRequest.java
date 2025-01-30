package spribe.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static spribe.config.EnvConfig.ENV_SERVICE_URL;

public abstract class AbstractBaseRequest implements Request {

    private final String baseUrl;
    private final String endpoint;
    private final RequestSpecification requestSpecification;

    public AbstractBaseRequest(String baseUrl, String endpoint) {
        this.baseUrl = baseUrl;
        this.endpoint = endpoint;
        this.requestSpecification = buildBaseRequestSpecification();
    }

    public AbstractBaseRequest(String endpoint) {
        this(ENV_SERVICE_URL.getValue(), endpoint);
    }

    private RequestSpecification buildBaseRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification requestConfig() {
        return RestAssured.given()
                .spec(requestSpecification)
                .baseUri(baseUrl)
                .basePath(endpoint);
    }
}
