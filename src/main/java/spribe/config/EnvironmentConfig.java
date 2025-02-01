package spribe.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import spribe.api.ValueRetriever;
import spribe.helpers.ValidationHelper;

import java.util.Map;
import java.util.stream.Collectors;

import static spribe.config.TestGroups.ALL;

@Log4j2
@Getter
@RequiredArgsConstructor
public enum EnvironmentConfig implements ValueRetriever {

    ENV_SERVICE_URL("env_service_url", "http://3.68.165.45"),
    ENV_SERVICE_TIMEOUT("env_service_timeout", "20000"),

    ENV_PARALLEL("env_parallel", "methods"),
    ENV_THREAD_COUNT("env_thread_count", "3"),
    ENV_INCLUDE_GROUPS("env_included_groups", ALL),
    ENV_EXCLUDE_GROUPS("env_excluded_groups", "");

    private final String key;
    private final String value;

    public static Map<String, String> getConfigMap() {
        return java.util.Arrays.stream(EnvironmentConfig.values())
                .collect(Collectors.toMap(EnvironmentConfig::getKey, EnvironmentConfig::getValue));
    }

    public String getValue() {
        return getPropertiesOrDefaultConfig(key, value);
    }

    private static String getPropertiesOrDefaultConfig(String key, String defaultValue) {
        String result = ValidationHelper.isNotNullAndNotBlank(System.getProperty(key)) ?
                System.getProperty(key) : defaultValue;
        log.info("Config [{}]: Using value '{}'", key, result);
        return result;
    }
}
