package spribe.data.fetch;

import io.qameta.allure.Allure;
import spribe.data.GrantedPlayers;
import spribe.data.entity.GrantedPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumGrantedPlayerFetcher implements GrantedPlayerFetcher {

    @Override
    public List<GrantedPlayer> fetch() {
        return Allure.step("Fetch list of granted players", () -> Arrays.stream(GrantedPlayers.values())
                .map(EnumGrantedPlayerFetcher::mapToGrantedUser)
                .collect(Collectors.toList()));
    }

    private static GrantedPlayer mapToGrantedUser(final GrantedPlayers player) {
        return Allure.step("Map to granted user", () -> GrantedPlayer.builder()
                .id(player.getId())
                .login(player.getLogin())
                .role(player.getRole())
                .build());
    }
}
