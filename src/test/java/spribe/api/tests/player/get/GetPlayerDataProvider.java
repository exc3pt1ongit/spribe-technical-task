package spribe.api.tests.player.get;

import org.testng.annotations.DataProvider;

public class GetPlayerDataProvider {

    @DataProvider(name = "invalidIdStringParameters")
    private Object[][] invalidIdStringParameters() {
        return new Object[][]{
                {""},
                {"spribe"},
                {"老天保佑金山银山前路有"},
                {"\uD83E\uDD43\uD83E\uDDD1\u200D\uD83D\uDCBB"}
        };
    }
    
    @DataProvider(name = "invalidIdNumberParameters")
    private Object[][] invalidIdNumberParameters() {
        return new Object[][]{
                {-1L},
                {0L},
                {Long.MAX_VALUE},
                {Long.MIN_VALUE},
        };
    }
}
