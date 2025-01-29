package spribe.utils.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseGrantedPlayers {
    SUPERVISOR(1, "supervisor", PlayerRole.SUPERVISOR),
    ADMIN(2, "admin", PlayerRole.ADMIN);

    private final Integer id;
    private final String login;
    private final PlayerRole role;
}
