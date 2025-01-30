package spribe.data.fetch;

import spribe.data.entity.GrantedPlayer;

import java.util.List;

@FunctionalInterface
public interface GrantedPlayerFetcher {
    List<GrantedPlayer> fetch();
}
