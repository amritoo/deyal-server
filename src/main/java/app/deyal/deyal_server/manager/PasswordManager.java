package app.deyal.deyal_server.manager;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager {

    /**
     * generate hash from the original password
     *
     * @param password String
     * @return
     */
    public String getHash(String password) {
        return DigestUtils.sha256Hex(password);
    }

    /**
     * match the original password with the hash to check if they are equal
     *
     * @param password String
     * @param hash     String
     * @return
     */
    public boolean matchWithHash(String password, String hash) {
        String newHash = getHash(password);
        return newHash.equals(hash);
    }

}
