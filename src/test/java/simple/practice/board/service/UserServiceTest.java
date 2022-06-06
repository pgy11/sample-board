package simple.practice.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import simple.practice.board.contoller.dto.UserSignInDto;
import simple.practice.board.entity.Role;
import simple.practice.board.entity.UserEntity;
import simple.practice.board.repository.UserRepository;
import simple.practice.board.repository.redis.RedisTemplate;
import simple.practice.board.security.PasswordEncoder;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;


    @BeforeEach
    public void setup() {
        this.passwordEncoder = new PasswordEncoder();
        this.userService = new UserService(userRepository, passwordEncoder, redisTemplate, emailService);
    }

    @Test
    public void sign_in_when_valid_credential() throws Exception {
        String userEmail = "hello@exmaple.com";
        String password = "1234";
        PasswordEncoder encoder = new PasswordEncoder();
        String salt = encoder.generateSalt();
        System.out.println("salt = " + salt);
        String encodedPassword = encoder.encodePassword(salt, password);
        System.out.println("encodedPassword = " + encodedPassword);
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .userId("1")
                .email(userEmail)
                .salt(salt)
                .password(encodedPassword)
                .role(Role.ROLE_User)
                .isActive(true)
                .build();

        UserSignInDto userSignInDto = UserSignInDto.builder().userId("1").userEmail(userEmail).password(password).build();

        given(userRepository.findByUserEmail(userEmail)).willReturn(userEntity);
        UserSignInDto dto = userService.login(userSignInDto);
        System.out.println("dto = " + dto);

    }

}
