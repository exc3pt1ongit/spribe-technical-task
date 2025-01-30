package spribe.api.tests.player;

import spribe.utils.models.PlayerRole;

public interface CheckPlayerGrantedPermissionsTests {

    void playerPermissionsAvailableSchemaTest(PlayerRole role);

    void playerPermissionsNotAvailableSchemaTest(PlayerRole role);
}
