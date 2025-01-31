package spribe.api.tests.player;

import spribe.helpers.models.PlayerRole;

public interface CheckPlayerGrantedPermissionsTests {

    void playerPermissionsAvailableTest(PlayerRole role);

    void playerPermissionsNotAvailableTest(PlayerRole role);
}
