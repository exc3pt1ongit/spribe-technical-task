package spribe.api.tests.player;

import org.testng.annotations.DataProvider;
import spribe.utils.models.PlayerRole;

public class BasePlayerGrantedPermissionsDataProvider {

    @DataProvider(name = "rolesWithAvailablePermissionsToMutate")
    public Object[][] rolesWithAvailablePermissionsToMutate() {
        return new Object[][]{
                {PlayerRole.SUPERVISOR}, {PlayerRole.ADMIN}
        };
    }

    @DataProvider(name = "rolesWithNotAvailablePermissionsToMutate")
    public Object[][] rolesWithNotAvailablePermissionsToMutate() {
        return new Object[][]{
                {PlayerRole.USER}
        };
    }
}
