package spribe.api.tests.player.update;

import org.testng.annotations.DataProvider;
import spribe.api.player.dto.update.PlayerUpdateRequestDto;
import spribe.api.tests.player.BasePlayerGrantedPermissionsDataProvider;
import spribe.helpers.models.PlayerGender;
import spribe.helpers.models.PlayerRole;

import java.util.UUID;

public class UpdatePlayerDataProvider extends BasePlayerGrantedPermissionsDataProvider {

    @DataProvider(name = "updatePlayerAvailableMutations")
    public Object[][] updatePlayerAvailableMutations() {
        return new Object[][]{
                {PlayerUpdateRequestDto.builder().age(19).build()},
                {PlayerUpdateRequestDto.builder().role(PlayerRole.USER.getValue()).build()},
                {PlayerUpdateRequestDto.builder().gender(PlayerGender.OTHER.getValue()).build()},
                {PlayerUpdateRequestDto.builder().screenName("testScreenNameUpd_" + UUID.randomUUID()).build()},
                {PlayerUpdateRequestDto.builder().login("testLoginUpd_" + UUID.randomUUID()).build()},
                {PlayerUpdateRequestDto.builder().password("testPasswordUpd_" + UUID.randomUUID()).build()}
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
