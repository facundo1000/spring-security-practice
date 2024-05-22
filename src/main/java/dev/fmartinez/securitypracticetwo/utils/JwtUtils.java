package dev.fmartinez.securitypracticetwo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value(value = "${spring.security.jwt.key.private}")
    private String key;

    @Value(value = "${spring.security.jwt.user.generator}")
    private String userGenerator;


    /**
     * A partir de la interfaz 'Authentication' se extrae el
     * usuario y las autorizaciones
     *
     * @param authentication Authentication
     * @return String
     */
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC512(key); //Algoritmo de encriptacion

        /**
         * Se obtiene el usuario contenido dentro del 'principal'
         */
        String username = authentication.getPrincipal().toString();


        /**
         * Se obtienen las 'authorities' de los usuarios
         * y se los separa por una coma (,)
         */
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = JWT.create()
                .withIssuer(userGenerator) //Usuario que genera el token -> el backend
                .withSubject(username) //el sujeto al cual se le genera el token, en este caso al usuario registrado
                .withClaim("authorities", authorities) //se muestran los permisos del usuario
                .withIssuedAt(new Date()) //fecha en la que se genera el token
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // el tiempo de expiracion (siempre en milisegundos), en este caso 30 minuto
                .withJWTId(UUID.randomUUID().toString()) //se le asigna un id al token
                .withNotBefore(new Date(System.currentTimeMillis())) //indica a partir de que momento el token sera valido

                .sign(algorithm);
        return token;
    }

    /**
     * @param token
     * @return DecodedJWT retorna el json web token decodificado
     */

    public DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(key); //Algoritmo de encriptacion
        try {

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            return verifier.verify(token);

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token validation failed");
        }

    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }


    public Claim getSpecificClaim(DecodedJWT jwt, String claimName) {
        return jwt.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
