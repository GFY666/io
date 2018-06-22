package com.qbaoio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hepen on 7/12/2017.
 */
@Configuration
public class JwtTokenUtil {
    public String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(, username());
        return Jwts.builder()
//                .setClaims(claims)
                .setSubject(username)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, "pengfei") //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }

    private Date generateExpirationDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("pengfei")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = this.getClaimsFromToken(token);
        return claims.getSubject();
    }

    public boolean validateToken(String token, String user) {
        String nameInToken = getUsernameFromToken(token);
        return nameInToken.equals(user);
    }

    @Bean //@Bean注解在方法上,返回值是一个类的实例,并声明这个返回值(返回一个对象)是spring上下文环境中的一个bean
    public JwtTokenUtil getThreadBean() {
        return new JwtTokenUtil();
    }
}
