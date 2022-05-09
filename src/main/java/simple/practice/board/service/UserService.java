package simple.practice.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import simple.practice.board.contoller.dto.UserDto;
import simple.practice.board.entity.UserEntity;
import simple.practice.board.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDto> getUsers(int page, int size) {
        Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(page, size));
        return userEntities.map(userEntity ->
                UserDto.builder()
                        .userId(userEntity.getUserId())
                        .createDate(userEntity.getCreatedDate())
                        .email(userEntity.getEmail())
                        .build()
        );
    }

}
