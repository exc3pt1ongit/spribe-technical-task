package spribe.data.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spribe.api.ValueRetriever;

@Getter
@RequiredArgsConstructor
public enum PlayerRole implements ValueRetriever {
    SUPERVISOR("supervisor"),
    ADMIN("admin"),
    USER("user");

    private final String value;
}
