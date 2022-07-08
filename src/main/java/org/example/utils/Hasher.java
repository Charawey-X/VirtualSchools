package org.example.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.models.Users;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

public class Hasher {
    public static Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

    public static String hash(String password) {
        try {
            return encoder.encode(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String password, String hash) {
        return encoder.matches(password, hash);
    }

    public static String createJwt(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")
                    // Expiration time set to 1 year from now
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 365)))
                    .withClaim("email", user.getEmail())
                    .withClaim("id", user.getId())
                    .withClaim("sub", "user")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Could not create JWT");
        }
    }

    public static DecodedJWT decodeJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DecodedJWT jwt = JWT.require(algorithm)
                .withIssuer("auth0")
                .build()
                .verify(token);
        return jwt;
    }

}
