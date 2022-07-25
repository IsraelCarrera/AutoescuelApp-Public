package org.example.serverautoescuela.ee.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.domain.PersonaLogin;
import org.example.serverautoescuela.ee.security.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

public class DataBaseIdentityStore implements IdentityStore {
    private final ServiciosPersona sp;

    @Inject
    public DataBaseIdentityStore(ServiciosPersona sp) {
        this.sp = sp;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = null;
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
            Either<ApiError, Persona> usuario = sp.hacerLogin(PersonaLogin.builder()
                    .dni(user.getCaller())
                    .pass(user.getPasswordAsString())
                    .build());
            if(usuario.isRight()){
                switch (usuario.get().getTipoPersona()) {
                    case Constantes.ALUMNO:
                        credentialValidationResult = new CredentialValidationResult(usuario.get().getDni(), Set.of(Constantes.ALUMNO));
                        break;
                    case Constantes.PROFESOR:
                        credentialValidationResult = new CredentialValidationResult(usuario.get().getDni(), Set.of(Constantes.PROFESOR, Constantes.ALUMNO));
                        break;
                    case Constantes.ADMINISTRADOR:
                        credentialValidationResult = new CredentialValidationResult(usuario.get().getDni(), Set.of(Constantes.ADMINISTRADOR, Constantes.ALUMNO, Constantes.PROFESOR));
                        break;
                    default:
                        credentialValidationResult = INVALID_RESULT;
                        break;
                }
            }
        }
        return credentialValidationResult;
    }
}
