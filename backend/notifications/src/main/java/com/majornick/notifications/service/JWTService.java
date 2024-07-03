package com.majornick.notifications.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private static final String SECRET_KEY = "BULg0LDP2qlO+eDUs2xW/0FJHWtGCY8uo4FbLH5+FEHTldAC9DGAM9kiOZJ2ZWOY";
    public String extractUsername(String jwt){
        return extractClaim(jwt,Claims::getSubject);
    }
    public <T> T extractClaim(String jwt, Function<Claims,T> claimProvider){
        Claims claims = extractAllClaims(jwt);
        return claimProvider.apply(claims);
    }

    public String generateToken(Map<String,String> claims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public String generateToken( UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);

    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    private Claims extractAllClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
