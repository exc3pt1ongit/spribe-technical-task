package spribe.data;

import spribe.data.entity.GrantedPlayer;
import spribe.utils.models.PlayerRole;

import java.util.List;

public interface GrantedPlayerDataSource {

    GrantedPlayer getPlayerByLogin(String login);

    GrantedPlayer getPlayerByRole(PlayerRole role);

    List<GrantedPlayer> getAllPlayers();
}
