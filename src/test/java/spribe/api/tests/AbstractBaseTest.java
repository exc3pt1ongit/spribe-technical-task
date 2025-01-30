package spribe.api.tests;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Listeners;
import spribe.data.GrantedPlayerDataSource;
import spribe.data.GrantedPlayerDataSourceImpl;
import spribe.data.fetch.EnumGrantedPlayerFetcher;
import spribe.data.fetch.GrantedPlayerFetcher;
import spribe.listeners.ResponsiveListener;

@Log4j2
@Listeners({ResponsiveListener.class})
public abstract class AbstractBaseTest {

    protected final GrantedPlayerFetcher grantedPlayerFetcher;
    protected final GrantedPlayerDataSource grantedPlayerDataSource;

    protected AbstractBaseTest() {
        grantedPlayerFetcher = new EnumGrantedPlayerFetcher();
        grantedPlayerDataSource = new GrantedPlayerDataSourceImpl(grantedPlayerFetcher);
    }
}
