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
import spribe.config.ResponsiveDataContainer;
import spribe.data.GrantedPlayers;
import spribe.helpers.AllureHelper;
import spribe.utils.exceptions.ServiceNotReachableException;

@Log4j2
public class ResponsiveExecutionListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        AllureHelper.configAllureEnvironment();
        serviceAvailabilityCheck();
    }

    @Override
    public void onExecutionFinish() {
        Allure.step("Post conditions", () -> ResponsiveDataContainer.getInstance().getAffectedPlayerIdList()
                .forEach(affectedPlayerId -> {
                    PlayerDeleteRequestDto deleteRequestDto = PlayerDeleteRequestDto.builder()
                            .playerId(affectedPlayerId).build();
                    new DeletePlayerRequest(GrantedPlayers.SUPERVISOR.getLogin())
                            .call(deleteRequestDto);
                }));
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
                    throw new ServiceNotReachableException();
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
