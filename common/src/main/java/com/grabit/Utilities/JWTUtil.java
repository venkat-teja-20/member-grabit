package com.grabit.Utilities;

import com.grabit.exception.CustomException;
import com.grabit.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.InsufficientAuthenticationException;

public class JWTUtil {

    public static String extractEmail(String token) throws JwtAuthenticationException {
        return extractClaims(token).getSubject();
    }

    private static Claims extractClaims(String token) throws JwtAuthenticationException {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(System.getenv("secret_key").getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (SignatureException e){
            throw new JwtAuthenticationException("INVALID_SIGNATURE");
        }
        catch (ExpiredJwtException e){
            throw new JwtAuthenticationException("AUTHENTICATION_EXPIRED");
        }
        catch (Exception e){
            throw new JwtAuthenticationException("INVALID_TOKEN");
        }
    }
}
