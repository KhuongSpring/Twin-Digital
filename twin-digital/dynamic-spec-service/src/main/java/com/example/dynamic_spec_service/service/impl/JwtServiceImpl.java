//package com.example.dynamic_spec_service.service.impl;
//
//import com.example.dynamic_spec_service.exception.InternalServerException;
//import com.example.dynamic_spec_service.repository.InvalidatedTokenRepository;
//import com.example.dynamic_spec_service.service.JwtService;
//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.experimental.FieldDefaults;
//import lombok.experimental.NonFinal;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//import java.util.function.Function;
//
//@Service
//@Data
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class JwtServiceImpl implements JwtService {
//
//    @NonFinal
//    @Value("${jwt.secret}")
//    String secretKey;
//
//    InvalidatedTokenRepository invalidatedTokenRepository;
//
//    private Key getSignInKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
//    }
//
//    @Override
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    @Override
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    @Override
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
//    }
//
//    @Scheduled(cron = "0 0 3 * * *")
//    public void cleanExpiredInvalidatedTokens() {
//        Date now = new Date();
//        invalidatedTokenRepository.deleteByExpiryTimeBefore(now);
//    }
//
//    @Override
//    public boolean isTokenExpired(String token) {
//        return !extractExpiration(token).before(new Date());
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}