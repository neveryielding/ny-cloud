package com.nycloud.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
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
    private static final long EXPIRATION_TIME = 3600_000_000L;

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String generateToken(Map<String, Object> map) {
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        System.out.println("Bearer." + jwt);
        return "Bearer." + jwt;
    }

    public static JwtEntity parseToken(String token) {
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace("Bearer.",""))
                .getBody();
        JwtEntity jwt = null;
        try {
            String username = (String) body.get("username");
            String userId = (String) body.get("userId");
            Integer exp = (Integer) body.get("exp");
            String roles = (String) body.get("roles");
            jwt = new JwtEntity(userId, username, exp, roles);
        } catch (Exception e) {
        }
        return jwt;
    }

    public static Map<String, Object> parseToken1(String token) {
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace("Bearer.",""))
                .getBody();
        return body;
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

    public static boolean isJwtBearerToken(String token) {
        return StringUtils.countMatches(token, ".") == 3 && (token.startsWith("Bearer") || token.startsWith("bearer"));
    }

    public static void main(String[] args) {
        String token = "Bearer.eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MzQ3MzE0OTR9.8r5-8w0R4Y4BC0Dwn1jzZ-sIC8oaQ-rrKcAXolqWqgZiRtczHsYVd6p9hMis3AcFhb8JUeoZiCmEQz83lk7MNQ";
        System.out.println(parseToken(token).toString());
    }

}