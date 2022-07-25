package org.example.serverautoescuela.ee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.example.serverautoescuela.ee.security.utils.Constantes;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Singleton
public class JWTAuth implements HttpAuthenticationMechanism {
    private final Key key;
    private final DataBaseIdentityStore identity;

    @Inject
    public JWTAuth(DataBaseIdentityStore identity, @Named(Constantes.JWT) Key key) {
        this.identity = identity;
        this.key = key;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult c;

        //Inicializamos la variable
        AuthenticationStatus authenticationStatus = httpMessageContext.doNothing();
        //Cogemos el header
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            //basic usuario:contraseña
            //bearer token
            //Una vez sabemos que el header no es null. dividimos el header en dos para coger si es Basic o Bearer y proceder.
            String[] valores = header.split(Constantes.ESPACIO);
            if (valores[0].equalsIgnoreCase(Constantes.BASIC)) {
                //Aqui si es basic, que es porque es su primer logging.
                c = esBasic(valores, httpServletResponse);
                //En esta función hacemos el token y lo mandamos como head de la llamada. Siempre y cuando C no sea nulo, claro.
                if (c != null) {
                    authenticationStatus = httpMessageContext.notifyContainerAboutLogin(c);
                }
            } else if (valores[0].equalsIgnoreCase(Constantes.BEARER)) {
                //BEARER
                c = comprobarToken(httpServletResponse, valores);
                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    authenticationStatus = httpMessageContext.notifyContainerAboutLogin(c);
                }
            }
        }
        return authenticationStatus;
    }

    private CredentialValidationResult esBasic(String[] valores, HttpServletResponse httpServletResponse) {
        CredentialValidationResult c;
        //Aqui si es basic, que es porque es su primer logging.
        String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
        String[] userPassSeparado = userPass.split(Constantes.DOS_PUNTOS);
        c = identity.validate(new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));
        //Si el usuario no anda registrado en la app, saldrá un bonito nulo, así que lo controlamos aquí.
        if (c != null) {
            if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                //En el response meterle una cabecera con el TOKEN, mandarlo en el response.
                //Primero hacemos el token.
                String jws = generarToken(c);
                httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, jws);
            }
        }
        return c;
    }

    private String generarToken(CredentialValidationResult c) {
        //En el response meterle una cabecera con el TOKEN, mandarlo en el response.
        //Primero hacemos el token.
        //Comprobar lo del set
        return Jwts.builder()
                .setExpiration(Date
                        .from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(Constantes.USUARIO, c.getCallerPrincipal().getName())
                .claim(Constantes.GRUPO, c.getCallerGroups())
                .signWith(key)
                .compact();
    }

    private CredentialValidationResult comprobarToken(HttpServletResponse httpServletResponse, String[] valores) {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(valores[1]);
            //Respecto al set. No sabía como hacerlo mejor, así que lo mando como String, lo recojo como String y lo convierto aquí a set.
            c = new CredentialValidationResult(jws.getBody().get(Constantes.USUARIO).toString(),
                    new HashSet<>((List<String>) jws.getBody().get(Constantes.GRUPO)));
        } catch (ExpiredJwtException e) {
            httpServletResponse.setHeader(HttpHeaders.EXPIRES, Constantes.VUELVE_A_LOGGEAR);
        } catch (Exception e) {
            httpServletResponse.setStatus(Response.Status.FORBIDDEN.getStatusCode());
        }
        return c;
    }

}
