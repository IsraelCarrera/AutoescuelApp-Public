package org.example.serverautoescuela.utils;

import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.HashMap;
import java.util.Map;

public class HashPass {
    private final Pbkdf2PasswordHash passHasheo;

    @Inject
    public HashPass(Pbkdf2PasswordHash passHasheo) {
        this.passHasheo = passHasheo;
    }

    public String hashearPass(String pass) {
        Map<String, String> inicializar = new HashMap<>();
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_ITERATIONS, Constantes.VALUE_ITERATIONS);
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_ALGORITHM, Constantes.PBKDF_2_WITH_HMAC_SHA_512);
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES, Constantes.VALUE_VECESVUELTA);
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES, Constantes.VALUE_VECESVUELTA);
        passHasheo.initialize(inicializar);
        return passHasheo.generate(pass.toCharArray());
    }

    public boolean comprobarPass(String passMandada, String passHashGuardadaBD) {
        passHasheo.initialize(inicializarHash());
        return passHasheo.verify(passMandada.toCharArray(), passHashGuardadaBD);
    }

    private Map<String, String> inicializarHash() {
        Map<String, String> inicializar = new HashMap<>();
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_ITERATIONS, Constantes.VALUE_ITERATIONS);
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_ALGORITHM, Constantes.PBKDF_2_WITH_HMAC_SHA_512);
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES, Constantes.VALUE_VECESVUELTA);
        inicializar.put(Constantes.PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES, Constantes.VALUE_VECESVUELTA);
        return inicializar;
    }
}
