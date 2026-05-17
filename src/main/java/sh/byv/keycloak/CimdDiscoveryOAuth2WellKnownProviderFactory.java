package sh.byv.keycloak;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.oauth2.OAuth2WellKnownProviderFactory;
import org.keycloak.wellknown.WellKnownProvider;
import org.keycloak.wellknown.WellKnownProviderFactory;

public class CimdDiscoveryOAuth2WellKnownProviderFactory implements WellKnownProviderFactory {

    public static final String PROVIDER_ID = "oauth-authorization-server";

    private volatile WellKnownProviderFactory delegate;

    @Override
    public WellKnownProvider create(final KeycloakSession session) {
        return new CimdDiscoveryWellKnownProvider(delegate.create(session));
    }

    @Override
    public void init(final Config.Scope config) {
    }

    @Override
    public void postInit(final KeycloakSessionFactory factory) {
        delegate = factory.getProviderFactoriesStream(WellKnownProvider.class)
                .filter(f -> f.getClass() == OAuth2WellKnownProviderFactory.class)
                .map(f -> (WellKnownProviderFactory) f)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("OAuth2WellKnownProviderFactory not found"));
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    // Must be higher than OAuth2WellKnownProviderFactory.order() == 0
    @Override
    public int order() {
        return 1;
    }
}
