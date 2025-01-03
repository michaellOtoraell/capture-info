package com.otorael.Capture_info.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {


    /**
     *
     * <P>
     *     HERE WE  TAKE THE SECRET KEY FROM
     *     application.yaml file
     * </P>
     *
     */
    @Value("${jwt.secretKey}")
    public String SECRET_KEY;
    /**
     *
     * @param email the only subject that will be used to generate a token for a specific session
     * @return the token which validates the activity within a session
     */
    public String ATokenGenerator(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 5)) // token expires within 3 hours even if i ta not blacklisted
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //sign in the token with hashed secretKey
                .compact();
    }
}
