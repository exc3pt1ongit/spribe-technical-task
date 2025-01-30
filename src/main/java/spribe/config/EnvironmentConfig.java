package spribe.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import spribe.api.ValueRetriever;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

@Log4j2
@Getter
@RequiredArgsConstructor
public enum EnvironmentConfig implements ValueRetriever {
    ENV_SERVICE_URL("http://3.68.165.45"),
    ENV_SERVICE_TIMEOUT("20000");

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private final String defaultValue;

    private static void loadProperties() {
        String propertiesFilePath = constructPropertiesFilePath();
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
            log.info("Loaded properties from `environment.properties`");
        } catch (IOException ex) {
            log.warn("Could not load properties file from {}: {}", propertiesFilePath, ex.getMessage());
        }
    }

    private static String constructPropertiesFilePath() {
        String workingDir = System.getProperty("user.dir");
        return Paths.get(workingDir, "src", "main", "resources", "environment.properties").toString();
    }

    public String getValue() {
        String key = name().toLowerCase().replace("_", ".");
        String value = properties.getProperty(key, defaultValue);

        if (properties.containsKey(key)) {
            log.info("Loaded value for {} from environment properties: {}", key, value);
        } else {
            log.info("Using default value for {}: {}", key, defaultValue);
        }

        return value;
    }
}
