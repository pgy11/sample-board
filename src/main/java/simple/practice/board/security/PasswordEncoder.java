package simple.practice.board.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class PasswordEncoder {

    public String encodePassword(String salt, String password) {
        return BCrypt.hashpw(password, salt);
    }

    public String generateSalt() {
        return BCrypt.gensalt();
    }

}
