package spribe.api.tests.player;

import spribe.utils.models.PlayerRole;

public interface CheckPlayerGrantedPermissionsTests {

    void playerPermissionsAvailableTest(PlayerRole role);

    void playerPermissionsNotAvailableTest(PlayerRole role);
}
