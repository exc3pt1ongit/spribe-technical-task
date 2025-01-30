package spribe.listeners;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.IExecutionListener;
import spribe.api.player.dto.delete.PlayerDeleteRequestDto;
import spribe.api.player.requests.DeletePlayerRequest;
import spribe.config.EnvironmentConfig;
import spribe.config.GlobalDataContainer;
import spribe.data.GrantedPlayers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Log4j2
public class ResponsiveListener implements IExecutionListener {

    private static final String ENVIRONMENT_PROPERTIES_FILE = "environment.properties";
    private static final String SOURCE_PROPERTIES_PATH = "src/main/resources/";
    private static final String DESTINATION_PROPERTIES_PATH = "target/allure-results/";

    @Override
    public void onExecutionStart() {
        Allure.step("Pre conditions", () -> {
            copyPropertiesFile();
            serviceAvailabilityCheck();
        });
    }

    @Override
    public void onExecutionFinish() {
        Allure.step("Post conditions", () -> GlobalDataContainer.getInstance().getAffectedPlayerIdList()
                .forEach(affectedPlayerId -> {
                    PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                            .playerId(affectedPlayerId).build();
                    new DeletePlayerRequest(GrantedPlayers.SUPERVISOR.getLogin())
                            .call(deleteRequestDto);
                }));
    }

    private void copyPropertiesFile() {
        Allure.step("Copy properties file", () -> {
            try {
                Path sourcePath = new File(SOURCE_PROPERTIES_PATH + ENVIRONMENT_PROPERTIES_FILE).toPath();
                Path destinationPath = new File(DESTINATION_PROPERTIES_PATH + ENVIRONMENT_PROPERTIES_FILE).toPath();
                Files.createDirectories(destinationPath.getParent());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error(e);
            }
        });
    }

    private void serviceAvailabilityCheck() {
        Allure.step("Service availability check", () -> {
            String serviceUrl = EnvironmentConfig.ENV_SERVICE_URL.getValue() + "/swagger-resources/configuration/security";

            int maxCount = 3;
            int count = 0;
            long timeout = Long.parseLong(EnvironmentConfig.ENV_SERVICE_TIMEOUT.getValue());
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
        });
    }
}
