package spribe.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spribe.helpers.models.PlayerRole;

@Getter
@RequiredArgsConstructor
public enum GrantedPlayers {
    SUPERVISOR(1, "supervisor", PlayerRole.SUPERVISOR),
    ADMIN(2, "admin", PlayerRole.ADMIN);

    private final Integer id;
    private final String login;
    private final PlayerRole role;
}
