# kc-cimd-discovery

A Keycloak SPI that advertises `"none"` in `token_endpoint_auth_methods_supported` on the OIDC and OAuth 2.0 discovery documents.

## What it is

A small extension (single JAR) that overrides Keycloak's built-in well-known providers and decorates their output. It adds `"none"` to the list of supported token endpoint authentication methods exposed at:

- `/.well-known/openid-configuration`
- `/.well-known/oauth-authorization-server`

Everything else in the discovery document is left untouched — the SPI wraps the built-in provider and only mutates that one field.

## Why it exists

The [CIMD profile](https://hl7.org/fhir/uv/cimd/) (Consumer-facing Identity Management Discovery, used in FHIR / SMART-on-FHIR contexts) requires public clients to use `token_endpoint_auth_method = "none"`, and conformance tooling validates that the authorization server's discovery document explicitly advertises `"none"` as a supported method.

Keycloak supports the `"none"` auth method at runtime for public clients, but does not list it in the discovery document by default. That mismatch causes CIMD conformance checks to fail even though the server behaves correctly. This SPI closes that gap by patching the advertised metadata.

## How it works

Two factories register against the same aliases as Keycloak's built-in providers (`openid-configuration`, `oauth-authorization-server`) with a lower priority value, so they win the lookup. Each factory resolves the original provider via `KeycloakSession.getProvider(...)`, wraps it, and appends `"none"` to `tokenEndpointAuthMethodsSupported` if absent.

## Build

```sh
mvn clean package
```

Produces `target/kc-cimd-discovery-1.0.0.jar`.

## Install

Drop the JAR into Keycloak's `providers/` directory and rebuild:

```sh
cp target/kc-cimd-discovery-1.0.0.jar $KEYCLOAK_HOME/providers/
$KEYCLOAK_HOME/bin/kc.sh build
```

Verify by fetching the discovery document for any realm and confirming `"none"` appears in `token_endpoint_auth_methods_supported`:

```sh
curl https://<host>/realms/<realm>/.well-known/openid-configuration | jq .token_endpoint_auth_methods_supported
```

## Compatibility

Built and tested against Keycloak **26.6.0** (see `keycloak.version` in `pom.xml`). Requires Java 17+.