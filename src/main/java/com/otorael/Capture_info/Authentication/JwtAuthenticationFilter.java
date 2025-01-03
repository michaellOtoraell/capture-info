package com.otorael.Capture_info.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    public JwtAuthenticationFilter(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    private static final Set<String> TokenBlacklist = new HashSet<>();

    /**
     *
     * @param token takes token to check for their validity
     * @return if its true or false after check
     */
    public boolean isTokenBlacklisted(String token){
        return TokenBlacklist.contains(token);
    }

    /**
     * <p>
     *     After an expiration time is reach or a user logout their token needs to be revoked for secure activities
     * </p>
     *
     * @param token input a token to blacklist/revoke it
     */
    public static void BlacklistToken(String token){

        TokenBlacklist.add(token);
    }


    /**
     * Filters the request for JWT token validation and sets the authentication.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain to proceed with.
     * @throws ServletException If there is a servlet exception.
     * @throws IOException If there is an I/O exception.
     */

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")){

            String token = authHeader.substring(7);

            if (isTokenBlacklisted(token)){

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token not valid");
            }

            try {

                Claims claims = Jwts.parser()
                        .setSigningKey(jwtUtility.secretKey)
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();

                if (email != null){

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            new User(email, "", Collections.emptyList()),
                            null,
                            Collections.emptyList()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception err){

            }
        }

        filterChain.doFilter(request, response);
    }
}
