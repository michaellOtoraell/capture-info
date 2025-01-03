package com.otorael.Capture_info.Authentication;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtility {

    /**
     *
     * <p>
     *     @Value gets the secret key from application.yaml for token generation
     * </p>
     */
    @Value("${jwt.secretKey}")
    public String secretKey;

    /**
     *
     * @param email unique email to pass as a subject for a specific user
     * @return a token
     *
     */
    public String TokenGeneration(String email){

        return Jwts.builder()
                .setIssuer("otorael")
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 3))
                .compact();
    }
}
