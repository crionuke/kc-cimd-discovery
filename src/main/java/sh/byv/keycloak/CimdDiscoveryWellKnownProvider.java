package sh.byv.keycloak;

import org.keycloak.protocol.oidc.representations.OIDCConfigurationRepresentation;
import org.keycloak.wellknown.WellKnownProvider;

import java.util.ArrayList;
import java.util.List;

public class CimdDiscoveryWellKnownProvider implements WellKnownProvider {

    private static final String NONE_AUTH_METHOD = "none";

    private final WellKnownProvider delegate;

    public CimdDiscoveryWellKnownProvider(final WellKnownProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getConfig() {
        final var config = delegate.getConfig();
        if (config instanceof OIDCConfigurationRepresentation oidcConfig) {
            final List<String> existing = oidcConfig.getTokenEndpointAuthMethodsSupported();
            if (existing == null || !existing.contains(NONE_AUTH_METHOD)) {
                final var methods = existing == null ? new ArrayList<String>() : new ArrayList<>(existing);
                methods.add(NONE_AUTH_METHOD);
                oidcConfig.setTokenEndpointAuthMethodsSupported(methods);
            }
        }
        return config;
    }

    @Override
    public void close() {
        delegate.close();
    }
}