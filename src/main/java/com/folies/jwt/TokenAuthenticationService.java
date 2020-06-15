package com.folies.jwt;

import com.folies.security.SecurityUserDetailsManager;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;

@Service
public class TokenAuthenticationService {
    private static final String SECRET = "Secret";
    private static final long EXPIRATION_TIME = 864_000_000;
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final Key SIGNING_KEY = MacProvider.generateKey(SIGNATURE_ALGORITHM);

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthenticationService.class);
    private static SecurityUserDetailsManager securityUserDetailsManager;

    @Autowired
    public TokenAuthenticationService(SecurityUserDetailsManager securityUserDetailsManager) {
        TokenAuthenticationService.securityUserDetailsManager = securityUserDetailsManager;
    }

    static void addAuthentication(HttpServletResponse response, String username) {
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + generateToken(username));
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        var token  = getToken(request);
        if (!Strings.hasText(token)) {
            return null;
        }

        var username = getUsername(token);
        var user = securityUserDetailsManager.loadUserByUsername(username);

        if (user == null) return null;

        return new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
    }

    private static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY, SIGNATURE_ALGORITHM)
                .compact();
    }

    private static String getToken(HttpServletRequest request) {
        if (request.getHeader(HEADER_STRING) != null)
            return request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX + " ", "");
        else
            return null;
    }

    private static String getUsername(String token) {
        try {
            return token != null ? Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject() : null;
        } catch (JwtException e) {
            LOG.info("Ошибка обработки токена: " + token);
            return null;
        }
    }
}
