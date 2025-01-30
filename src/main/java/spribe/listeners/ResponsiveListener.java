package spribe.listeners;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.IExecutionListener;
import spribe.config.EnvConfig;

@Log4j2
public class ResponsiveListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        serviceAvailabilityCheck();
    }

    private void serviceAvailabilityCheck() {
        String serviceUrl = EnvConfig.ENV_SERVICE_URL.getValue() + "/swagger-resources/configuration/security";

        int maxCount = 3;
        int count = 0;
        long timeout = Long.parseLong(EnvConfig.ENV_SERVICE_TIMEOUT.getValue());
        while (count <= maxCount) {
            try {
                Response response = RestAssured.given()
                        .get(serviceUrl)
                        .then()
                        .extract()
                        .response();
                if (response.statusCode() == HttpStatus.SC_OK) {
                    log.info("Service at {} is reachable!", serviceUrl);
                    break;
                } else count++;
            } catch (Exception e) {
                log.error("Error while pinging the service: {}. Trying again...", e.getMessage(), e);
                count++;
            }

            if (count == maxCount) {
                log.error("Service at {} is not reachable. Test execution will be stopped.", serviceUrl);
                throw new RuntimeException("Service is not reachable. Aborting test execution.");
            }

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
