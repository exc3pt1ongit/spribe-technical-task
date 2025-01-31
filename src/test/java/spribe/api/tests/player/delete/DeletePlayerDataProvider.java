package spribe.api.tests.player.delete;

import org.testng.annotations.DataProvider;
import spribe.api.tests.player.BasePlayerGrantedPermissionsDataProvider;
import spribe.data.models.PlayerRole;

public class DeletePlayerDataProvider extends BasePlayerGrantedPermissionsDataProvider {
    @DataProvider(name = "rolesWithAvailablePermissionsToMutate")
    @Override
    public Object[][] rolesWithAvailablePermissionsToMutate() {
        return new Object[][]{
                {PlayerRole.ADMIN}
        };
    }

    @DataProvider(name = "rolesWithNotAvailablePermissionsToMutate")
    @Override
    public Object[][] rolesWithNotAvailablePermissionsToMutate() {
        return new Object[][]{
                {PlayerRole.USER}
        };
    }
}
