package org.example.serverautoescuela.ee.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.example.serverautoescuela.ee.security.utils.Constantes;

import java.security.Key;

public class KeyProvider {

    @Produces
    @Singleton
    @Named(Constantes.JWT)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
