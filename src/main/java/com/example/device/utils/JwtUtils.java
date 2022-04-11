package com.example.device.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    private final static Long access_token_expiration = 7200L;

    public String generateJwtToken(int user_id) {

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("id", user_id);

        return Jwts.builder()
                .setHeader(map)
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())

                .setIssuedAt(new Date())                                          //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expiration * 1000)) //过期时间
                .setSubject("user")                                               //面向用户

                .signWith(SignatureAlgorithm.HS256, SECRET)                       //签名
                .compact();                                                       //压缩生成token

    }

    public boolean checkToken(String token) {

        if (StringUtils.isEmpty(token)) return false;
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            return checkToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public int getUserIdFromToken(String token) {
        if (!checkToken(token)) {
            return -1;
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (int) claims.get("id");
    }

    public int getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return getUserIdFromToken(token);

    }

}
