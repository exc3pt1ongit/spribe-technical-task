package spribe.helpers;

import lombok.extern.log4j.Log4j2;
import spribe.config.EnvironmentConfig;
import spribe.utils.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

@Log4j2
public class AllureHelper {

    private static volatile Properties allureProperties;
    
    private static final String ALLURE_PROPERTIES_PATH = "test/resources/allure.properties";

    public static void configAllureEnvironment() {
        Path pathAllureResultsDir = Paths.get(System.getProperty("user.dir"), "target", "allure-results");
        Path envFilePath = Path.of(pathAllureResultsDir + "/environment.properties");
        try {
            if (!IOUtils.exists(pathAllureResultsDir)) {
                Files.createDirectories(pathAllureResultsDir);
            }

            if (!IOUtils.exists(envFilePath)) {
                IOUtils.createFile(envFilePath);
            } else {
                IOUtils.cleanFile(envFilePath.toString());
            }

            try (OutputStream output = new FileOutputStream(envFilePath.toFile())) {
                Properties p = new Properties();
                EnvironmentConfig.getConfigMap().forEach(p::setProperty);
                p.store(output, null);
            }
        } catch (IOException e) {
            log.error("Failed to configure Allure Environment ('{}').\nStacktrace: {}",
                    envFilePath, Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }

    private static synchronized String getAllureResultsDirectory() {
        return getAllureProperties().getProperty("allure.results.directory");
    }

    private static Properties getAllureProperties() {
        if (allureProperties == null) {
            synchronized (AllureHelper.class) {
                if (allureProperties == null)
                    allureProperties = loadProperties();
            }
        }
        return allureProperties;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(ALLURE_PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            log.error("Failed to load allure.properties", e);
        }
        return properties;
    }
}
