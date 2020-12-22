package com.cheese.security.jwt;

import com.cheese.constant.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAuthenticationResolver {
    public static final String AUTHENTICATION_SCHEMA_BEARER = "Bearer";

    private Charset credentialCharset = StandardCharsets.UTF_8;

    public JwtAuthenticationToken resolve(HttpServletRequest request) throws JwtException {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null)
            return null;
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEMA_BEARER)) {
            return null;
        }
        String jwtToken = header.substring(6);

        return new JwtAuthenticationToken(jwtToken, null);
    }
}
