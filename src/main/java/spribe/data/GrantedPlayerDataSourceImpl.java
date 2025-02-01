package spribe.data;

import lombok.RequiredArgsConstructor;
import spribe.data.entity.GrantedPlayer;
import spribe.data.fetch.GrantedPlayerFetcher;
import spribe.data.models.PlayerRole;
import spribe.utils.exceptions.PlayerNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class GrantedPlayerDataSourceImpl implements GrantedPlayerDataSource {

    private final GrantedPlayerFetcher grantedPlayerFetcher;

    @Override
    public GrantedPlayer getPlayerByLogin(String login) {
        return grantedPlayerFetcher.fetch().stream()
                .filter(player -> player.getLogin().equals(login))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("login", login));
    }

    @Override
    public GrantedPlayer getPlayerByRole(PlayerRole role) {
        return grantedPlayerFetcher.fetch().stream()
                .filter(player -> player.getRole().equals(role))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("role", role.getValue()));
    }

    @Override
    public List<GrantedPlayer> getAllPlayers() {
        return grantedPlayerFetcher.fetch();
    }
}
