package com.app.ecom.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.ecom.dto.UserLoginRequest;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private String secretKey = null;

    public String generateToken(UserLoginRequest userLoginRequest) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(userLoginRequest.getEmail())
                    .issuer("DCB")
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+ 60*10*1000))
                    .and()
                    .signWith(generateKey())
                    .compact();
    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey() {
        return secretKey = "HyRJkPFuTh6y610cnwOqra1JouSCb1vKdEX4vZDdICw=";
    }

}
