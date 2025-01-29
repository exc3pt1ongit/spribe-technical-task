package spribe.utils.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spribe.api.ValueRetriever;

@Getter
@RequiredArgsConstructor
public enum PlayerGender implements ValueRetriever {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");
    
    private final String value;
}
