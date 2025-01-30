package spribe.api.tests;

import lombok.extern.log4j.Log4j2;
import spribe.data.GrantedPlayerDataSource;
import spribe.data.GrantedPlayerDataSourceImpl;
import spribe.data.fetch.EnumGrantedPlayerFetcher;
import spribe.data.fetch.GrantedPlayerFetcher;

@Log4j2
public abstract class AbstractBaseTest {

    protected final GrantedPlayerFetcher grantedPlayerFetcher;
    protected final GrantedPlayerDataSource grantedPlayerDataSource;

    protected AbstractBaseTest() {
        grantedPlayerFetcher = new EnumGrantedPlayerFetcher();
        grantedPlayerDataSource = new GrantedPlayerDataSourceImpl(grantedPlayerFetcher);
    }
}
