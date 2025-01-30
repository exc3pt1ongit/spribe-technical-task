package spribe.api.tests.player.create;

import org.testng.annotations.DataProvider;

public class CreatePlayerDataProvider {
    
    @DataProvider(name = "invalidAgeParameters")
    private Object[][] invalidAgeParameters() {
        return new Object[][]{
                {0},
                {15},
                {61}
        };
    }
}
