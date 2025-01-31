package spribe.data;

import lombok.RequiredArgsConstructor;
import spribe.data.entity.GrantedPlayer;
import spribe.data.fetch.GrantedPlayerFetcher;
import spribe.helpers.models.PlayerRole;

import java.util.List;

@RequiredArgsConstructor
public class GrantedPlayerDataSourceImpl implements GrantedPlayerDataSource {

    private final GrantedPlayerFetcher grantedPlayerFetcher;

    @Override
    public GrantedPlayer getPlayerByLogin(String login) {
        return grantedPlayerFetcher.fetch().stream()
                .filter(player -> player.getLogin().equals(login))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find player with login: " + login));
    }

    @Override
    public GrantedPlayer getPlayerByRole(PlayerRole role) {
        return grantedPlayerFetcher.fetch().stream()
                .filter(player -> player.getRole().equals(role))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find player with role: " + role));
    }

    @Override
    public List<GrantedPlayer> getAllPlayers() {
        return grantedPlayerFetcher.fetch();
    }
}
