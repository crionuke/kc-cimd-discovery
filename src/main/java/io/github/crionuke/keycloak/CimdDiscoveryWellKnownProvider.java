package io.github.crionuke.keycloak;

import org.keycloak.protocol.oidc.representations.OIDCConfigurationRepresentation;
import org.keycloak.wellknown.WellKnownProvider;

import java.util.ArrayList;
import java.util.List;

public class CimdDiscoveryWellKnownProvider implements WellKnownProvider {

    private final WellKnownProvider delegate;

    public CimdDiscoveryWellKnownProvider(WellKnownProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getConfig() {
        Object config = delegate.getConfig();
        if (config instanceof OIDCConfigurationRepresentation oidcConfig) {
            List<String> methods = oidcConfig.getTokenEndpointAuthMethodsSupported();
            if (methods == null) {
                methods = new ArrayList<>();
            } else {
                methods = new ArrayList<>(methods);
            }
            if (!methods.contains("none")) {
                methods.add("none");
            }
            oidcConfig.setTokenEndpointAuthMethodsSupported(methods);
        }
        return config;
    }

    @Override
    public void close() {
        delegate.close();
    }
}
