package sh.byv.keycloak;

import org.keycloak.protocol.oidc.OIDCWellKnownProviderFactory;

public class CimdDiscoveryWellKnownProviderFactory extends AbstractCimdDiscoveryWellKnownProviderFactory {

    public static final String PROVIDER_ID = "cimd-openid-configuration";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getAlias() {
        return OIDCWellKnownProviderFactory.PROVIDER_ID;
    }
}
