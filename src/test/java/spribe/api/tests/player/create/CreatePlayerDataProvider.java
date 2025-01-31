package spribe.api.tests.player.create;

import org.testng.annotations.DataProvider;
import spribe.api.tests.player.BasePlayerGrantedPermissionsDataProvider;
import spribe.data.models.PlayerRole;

public class CreatePlayerDataProvider extends BasePlayerGrantedPermissionsDataProvider {
    
    @DataProvider(name = "invalidAgeParameters")
    private Object[][] invalidAgeParameters() {
        return new Object[][]{
                {0},
                {15},
                {61}
        };
    }

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
