package sh.byv.keycloak;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.oauth2.OAuth2WellKnownProviderFactory;
import org.keycloak.wellknown.WellKnownProvider;
import org.keycloak.wellknown.WellKnownProviderFactory;

public class CimdDiscoveryOAuth2WellKnownProviderFactory implements WellKnownProviderFactory {

    public static final String PROVIDER_ID = "cimd-oauth-authorization-server";

    @Override
    public WellKnownProvider create(final KeycloakSession session) {
        final var delegate = session.getProvider(WellKnownProvider.class, OAuth2WellKnownProviderFactory.PROVIDER_ID);
        return new CimdDiscoveryWellKnownProvider(delegate);
    }

    @Override
    public void init(final Config.Scope config) {
    }

    @Override
    public void postInit(final KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    // Override the URL alias so this factory serves "/.well-known/oauth-authorization-server".
    // Lowest getPriority() wins for a given alias; our default (1) beats Keycloak's (100).
    @Override
    public String getAlias() {
        return OAuth2WellKnownProviderFactory.PROVIDER_ID;
    }

    @Override
    public boolean isAvailableViaServerMetadata() {
        return true;
    }
}