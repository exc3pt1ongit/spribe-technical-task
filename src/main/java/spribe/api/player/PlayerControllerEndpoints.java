package spribe.api.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spribe.api.ValueRetriever;

@Getter
@RequiredArgsConstructor
public enum PlayerControllerEndpoints implements ValueRetriever {
    GET("/player/get"),
    GET_ALL("/player/get/all"),
    CREATE("/player/create/{editor}"),
    UPDATE("/player/update/{editor}/{id}"),
    DELETE("/player/delete/{editor}");

    private final String value;
}
