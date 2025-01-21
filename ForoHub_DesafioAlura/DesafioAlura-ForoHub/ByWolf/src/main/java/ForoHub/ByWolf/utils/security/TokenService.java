package ForoHub.ByWolf.utils.security;
import ForoHub.ByWolf.models.UserService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


@Service
public class TokenService {

    @Value("${api.security.expiration}")
    private Integer apiExpiration;
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(UserService userService) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create().withIssuer("challenge_foro_hub")
                    .withSubject(userService.getUsername())
                    .withClaim("id", userService.getId())
                    .withExpiresAt(generateDateExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if(token == null) {
            throw new RuntimeException("Token nulo");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("challenge_foro_hub")
                    .build()
                    .verify(token);
            String subject = verifier.getSubject();
            if(subject == null) {
                throw new RuntimeException("Verificador Invalido");
            }
            return subject;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Token Invalido", e);
        }
    }
    private Instant generateDateExpiration() {
        return ZonedDateTime.now(ZoneOffset.of("+03:00"))
                .plusHours(apiExpiration)
                .toInstant();
    }
}
