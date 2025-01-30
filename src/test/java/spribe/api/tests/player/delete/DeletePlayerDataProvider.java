package spribe.api.tests.player.delete;

import org.testng.annotations.DataProvider;
import spribe.data.GrantedPlayers;

import java.util.Arrays;
import java.util.Iterator;

public class DeletePlayerDataProvider {

    @DataProvider(name = "grantedPlayersList")
    private Iterator<GrantedPlayers> grantedPlayersList() {
        return Arrays.stream(GrantedPlayers.values())
                .iterator();
    }
}
