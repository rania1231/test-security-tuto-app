package org.example.ineventory_service.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),//les roles que sont extraits normalement depuis l'attribut scope du jwt
                extractResourceRoles(jwt).stream()// recuperer les roles que sont realms_access.roles
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities,jwt.getClaim("preferred_username"));
    }
    //Cette fonction va extraire les roles que se trouve dans realm_access.roles
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String , Object> realmAccess;
        Collection<String> roles;
        if(jwt.getClaim("realm_access")==null){
            return Set.of();
        }
        realmAccess = jwt.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
    }

}


/** {
 "exp": 1739893534,
 "iat": 1739893234,
 "jti": "e35d3d91-d2b5-4649-9302-22ef4c4c6d1d",
 "iss": "http://localhost:8080/realms/ecom-web-app",
 "aud": "account",
 "sub": "86ca748a-e32d-467a-bdde-388ae0c4bc55",
 "typ": "Bearer",
 "azp": "front-end-angular-client",
 "sid": "fd99f513-4a9d-496d-be2f-bca6f3fc0040",
 "acr": "1",
 "allowed-origins": [
 "http://localhost:4200"
 ],
 "realm_access": {
 "roles": [
 "offline_access",
 "uma_authorization",
 "USER",
 "default-roles-ecom-web-app"
 ]
 },
 "resource_access": {
 "account": {
 "roles": [
 "manage-account",
 "manage-account-links",
 "view-profile"
 ]
 }
 },
 "scope": "email profile",
 "email_verified": false,
 "name": "Rania Hamdi",
 "preferred_username": "user1",
 "given_name": "Rania",
 "family_name": "Hamdi",
 "email": "user1@gmail.com"
 }**/