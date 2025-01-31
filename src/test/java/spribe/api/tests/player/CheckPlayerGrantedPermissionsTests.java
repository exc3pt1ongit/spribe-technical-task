package spribe.api.tests.player;

import spribe.data.models.PlayerRole;

public interface CheckPlayerGrantedPermissionsTests {

    void playerPermissionsAvailableTest(PlayerRole role);

    void playerPermissionsNotAvailableTest(PlayerRole role);
}
