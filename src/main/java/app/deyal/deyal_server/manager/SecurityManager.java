package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class SecurityManager {

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
     * Generates a token from user object with validity of hours variable
     *
     * @param user  userdata
     * @param hours token validity time
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
                .claim("email", text)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .compact();
    }

    /**
     * Verify a token and returns the user id
     *
     * @param token token to verify
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

    /**
     * generate hash from the original password
     *
     * @return String Hash
     */
    public String getHash(String password) {
        return DigestUtils.sha256Hex(password);
    }

    /**
     * match the original password with the hash to check if they are equal.
     * Returns true if they are equal and false otherwise
     *
     * @param password password to check
     * @param hash     hash code of real password
     * @return true if password matches with hash, false otherwise
     */
    public boolean matchWithHash(String password, String hash) {
        String newHash = getHash(password);
        return newHash.equals(hash);
    }

    /**
     * generates an OTP of 6 digits
     *
     * @return OTP
     */
    public String generateOTP() {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   //characters to use in password
        Random random = new Random();
        char[] password = new char[6];  //setting password size to 6
        IntStream.range(0, password.length).forEach(i -> password[i] = values.charAt(random.nextInt(values.length())));
        return String.valueOf(password);
    }

}
