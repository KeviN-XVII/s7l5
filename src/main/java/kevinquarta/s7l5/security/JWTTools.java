package kevinquarta.s7l5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.UnauthroizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

// ------GENERA TOKEN
    public String generateToken(Utente utente){
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(utente.getId())) //ID A CUI APPARTIENE IL TOKEN
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

// ------VERIFICA TOKEN
    public void verifyToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthroizedException("Problemi col token!");
        }
    }

// ------ESTRAE TOKEN
    public long extractIdFromToken (String token){
        return Long.parseLong(Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject());
    }
}
