package spribe.api.tests.player;

import org.testng.annotations.DataProvider;

public abstract class BasePlayerGrantedPermissionsDataProvider {

    @DataProvider(name = "rolesWithAvailablePermissionsToMutate")
    public abstract Object[][] rolesWithAvailablePermissionsToMutate();

    @DataProvider(name = "rolesWithNotAvailablePermissionsToMutate")
    public abstract Object[][] rolesWithNotAvailablePermissionsToMutate();
}
