package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtManager {

    private SecretKey jwtKey;

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @PostConstruct
    public void init() {
        jwtKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    /**
     * Generates a token from user object
     *
     * @param user
     * @param hours
     * @return String token
     */
    public String sign(User user, long hours) {
        String text = user.getEmail();
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + hours * 3_600_000);
        return Jwts.builder()
                .signWith(jwtKey)
                .setSubject(user.getId())
                .setIssuer(jwtIssuer)
                .setId(user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .compact();
    }

    /**
     * Verify a token and returns the user id
     *
     * @param token
     * @return String _id
     */
    public String verify(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .requireIssuer(jwtIssuer)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
