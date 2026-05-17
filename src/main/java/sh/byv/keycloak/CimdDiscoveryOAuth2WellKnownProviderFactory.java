package sh.byv.keycloak;

import org.keycloak.protocol.oauth2.OAuth2WellKnownProviderFactory;

public class CimdDiscoveryOAuth2WellKnownProviderFactory extends AbstractCimdDiscoveryWellKnownProviderFactory {

    public static final String PROVIDER_ID = "cimd-oauth-authorization-server";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getAlias() {
        return OAuth2WellKnownProviderFactory.PROVIDER_ID;
    }

    @Override
    public boolean isAvailableViaServerMetadata() {
        return true;
    }
}
