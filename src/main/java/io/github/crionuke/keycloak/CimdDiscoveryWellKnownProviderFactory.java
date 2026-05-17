package io.github.crionuke.keycloak;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.oidc.OIDCWellKnownProvider;
import org.keycloak.wellknown.WellKnownProvider;
import org.keycloak.wellknown.WellKnownProviderFactory;

public class CimdDiscoveryWellKnownProviderFactory implements WellKnownProviderFactory {

    public static final String PROVIDER_ID = "openid-configuration";

    @Override
    public WellKnownProvider create(KeycloakSession session) {
        return new CimdDiscoveryWellKnownProvider(new OIDCWellKnownProvider(session));
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
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
