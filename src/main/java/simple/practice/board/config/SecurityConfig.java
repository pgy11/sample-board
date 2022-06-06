package simple.practice.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import simple.practice.board.security.PasswordEncoder;
import simple.practice.board.security.cookie.CookieHelper;
import simple.practice.board.security.jwt.JwtHelper;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { return new PasswordEncoder(); }

    @Bean
    public CookieHelper cookieHelper() { return new CookieHelper(); }

    @Bean
    public JwtHelper jwtHelper() { return new JwtHelper(); }

}
