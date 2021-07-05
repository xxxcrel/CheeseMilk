package cn.qisee.cheesemilk.util;

import cn.qisee.cheesemilk.constant.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtUtils {

    private static String secret = JwtConstants.JWT_SECRET;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public static String parseToken(String token) throws JwtException {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = body.getSubject();
        return username;

    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param username the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(String username) {
        String resultToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .setHeaderParam("typ", JwtConstants.TOKEN_TYPE)
                .setIssuer(JwtConstants.TOKEN_ISSUER)
                .setAudience(JwtConstants.TOKEN_AUDIENCE)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .compact();
        return resultToken;
    }
}
