package spribe.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spribe.api.ValueRetriever;

@Getter
@RequiredArgsConstructor
public enum Config implements ValueRetriever {
    SERVICE_URL("http://3.68.165.45");

    private final String value;
}
