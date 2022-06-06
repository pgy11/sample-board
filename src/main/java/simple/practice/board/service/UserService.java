package simple.practice.board.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import simple.practice.board.contoller.dto.UserSignInDto;
import simple.practice.board.contoller.dto.UserSignUpDto;
import simple.practice.board.contoller.dto.UserStateDto;
import simple.practice.board.entity.Role;
import simple.practice.board.entity.UserEntity;
import simple.practice.board.repository.UserRepository;
import simple.practice.board.repository.redis.RedisTemplate;
import simple.practice.board.security.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate redisTemplate;

    private final EmailService emailService;

    public Page<UserSignInDto> getUsers(int page, int size) {
        Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(page, size));
        return userEntities.map(userEntity ->
                UserSignInDto.builder()
                        .userId(userEntity.getUserId())
                        .userEmail(userEntity.getEmail())
                        .build()
        );
    }

    @Transactional
    public void signUp(UserSignUpDto userSignUpDto) {
        String password = userSignUpDto.getPassword();
        String salt = passwordEncoder.generateSalt();
        UserEntity userEntity = UserEntity.builder()
                .userId(userSignUpDto.getUserId())
                .email(userSignUpDto.getUserEmail())
                .salt(salt)
                .password(passwordEncoder.encodePassword(salt, password))
                .build();

        userRepository.save(userEntity);
    }

    public UserSignInDto login(UserSignInDto userSignInDto) throws Exception {
        UserEntity userEntity = userRepository.findByUserEmail(userSignInDto.getUserEmail());
        if(userEntity == null) throw new Exception("user not found");
        String salt = userEntity.getSalt();
        String password = userSignInDto.getPassword();
        String encodedPassword = passwordEncoder.encodePassword(salt, password);

        if(!userEntity.getPassword().equals(encodedPassword)) {
            log.warn(userEntity.getPassword() + "\n" + encodedPassword);
            throw new Exception("password not correct");
        }

        return userSignInDto;
    }

    public void sendVerificationMail(UserSignInDto userSignInDto) throws Exception {
        String verificationLink = "http://localhost:8080/user/verify/";
        if(userSignInDto == null) throw new Exception("user not input");
        UUID uuid = UUID.randomUUID();
        redisTemplate.setDataExpire(uuid.toString(), userSignInDto.getUserEmail(), 60 * 30L);
        emailService.sendMail(userSignInDto.getUserEmail(), "회원가입 인증 메일입니다.", verificationLink + uuid.toString());
    }

    public void modifyUserRole(UserEntity userEntity, Role userRole) {
        userEntity.setRole(userRole);
        userRepository.save(userEntity);
    }

    public boolean isPasswordUuidValidate(String key) {
        String userId = redisTemplate.getData(key);
        return !userId.equals("");
    }

    public void changePassword(UserStateDto userStateDto, String password) throws Exception {
        if(userStateDto == null) throw new Exception("user not found");
        UserEntity userEntity = userRepository.findByUserEmail(userStateDto.getUserEmail());
        if(userEntity == null) throw new Exception("user not found");

        String salt = passwordEncoder.generateSalt();
        userEntity.setSalt(salt);
        userEntity.setPassword(passwordEncoder.encodePassword(salt, password));
    }

    public void requestChangePassword(UserStateDto userStateDto) throws Exception {
        String passwordLink = "http://localhost:8080/user/password/";
        String REDIS_CHANGE_PASSWORD_PREFIX="CPW";
        if(userStateDto == null) throw new Exception("user not found");
        String key = REDIS_CHANGE_PASSWORD_PREFIX+UUID.randomUUID();
        redisTemplate.setDataExpire(key, userStateDto.getUserEmail(), 60*30L);
        emailService.sendMail(userStateDto.getUserEmail(), "사용자 비밀번호 안내메일", passwordLink);
    }

}
