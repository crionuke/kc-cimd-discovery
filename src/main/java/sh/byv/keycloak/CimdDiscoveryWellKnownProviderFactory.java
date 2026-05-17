package sh.byv.keycloak;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.oidc.OIDCWellKnownProviderFactory;
import org.keycloak.wellknown.WellKnownProvider;
import org.keycloak.wellknown.WellKnownProviderFactory;

public class CimdDiscoveryWellKnownProviderFactory implements WellKnownProviderFactory {

    public static final String PROVIDER_ID = "openid-configuration";

    private WellKnownProviderFactory delegate;

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
                .filter(f -> f instanceof OIDCWellKnownProviderFactory)
                .map(f -> (WellKnownProviderFactory) f)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("OIDCWellKnownProviderFactory not found"));
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    // Must be higher than OIDCWellKnownProviderFactory.order() == 0
    @Override
    public int order() {
        return 1;
    }
}
