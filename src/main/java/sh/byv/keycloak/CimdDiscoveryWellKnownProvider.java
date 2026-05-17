package sh.byv.keycloak;

import org.keycloak.protocol.oidc.representations.OIDCConfigurationRepresentation;
import org.keycloak.wellknown.WellKnownProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CimdDiscoveryWellKnownProvider implements WellKnownProvider {

    private final WellKnownProvider delegate;

    public CimdDiscoveryWellKnownProvider(final WellKnownProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getConfig() {
        final var config = delegate.getConfig();
        if (config instanceof OIDCConfigurationRepresentation oidcConfig) {
            final var existing = Optional.ofNullable(oidcConfig.getTokenEndpointAuthMethodsSupported())
                    .orElseGet(List::of);
            final var methods = Stream.concat(existing.stream(), Stream.of("none"))
                    .distinct()
                    .collect(Collectors.toList());
            oidcConfig.setTokenEndpointAuthMethodsSupported(methods);
        }
        return config;
    }

    @Override
    public void close() {
        delegate.close();
    }
}
