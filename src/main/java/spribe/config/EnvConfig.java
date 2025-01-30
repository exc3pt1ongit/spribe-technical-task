package spribe.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spribe.api.ValueRetriever;

@Getter
@RequiredArgsConstructor
public enum EnvConfig implements ValueRetriever {
    ENV_SERVICE_URL("http://3.68.165.45"),
    ENV_SERVICE_TIMEOUT("20000");

    private final String value;
}
