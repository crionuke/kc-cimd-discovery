package sh.byv.keycloak;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.wellknown.WellKnownProvider;
import org.keycloak.wellknown.WellKnownProviderFactory;

/**
 * Shared boilerplate for CIMD well-known factories.
 * <p>
 * Subclasses pick their own {@link #getId()} but reuse the built-in provider's id as
 * {@link #getAlias()} so they serve the same URL path (e.g. "/.well-known/openid-configuration").
 * The lowest getPriority() wins for a given alias; our default (1) beats Keycloak's built-in (100).
 */
abstract class AbstractCimdDiscoveryWellKnownProviderFactory implements WellKnownProviderFactory {

    @Override
    public WellKnownProvider create(final KeycloakSession session) {
        final var delegate = session.getProvider(WellKnownProvider.class, getAlias());
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
}
