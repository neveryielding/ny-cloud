package com.nycloud.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/6 0006
 * @modified By:
 * @version: 1.0
 **/
public class JwtUtil {

    private static final String SECRET = "123434508340!)_~(";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String generateToken(Map<String, Object> map) {
        // 设置 Jwt 过期时间
        long expValue =  60 * 60 * 12 + System.currentTimeMillis();
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(expValue))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return "Bearer." + jwt;
    }

    public static JwtEntity parseToken(String token) {
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace("Bearer.",""))
                .getBody();
        JwtEntity jwt = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(body);
            jwt = objectMapper.readValue(jsonString, JwtEntity.class);
        } catch (Exception e) {
        }
        return jwt;
    }


    public static void validateToken(String token) {
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer.",""))
                    .getBody();


        }catch (Exception e){
            throw new IllegalStateException("Invalid Token. "+e.getMessage());
        }
    }
}