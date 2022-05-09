package simple.practice.board.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import simple.practice.board.contoller.dto.UserDto;
import simple.practice.board.service.UserService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam @Min(0) int page,
            @RequestParam @Min(1) int size) {

        Page<UserDto> userDtos = userService.getUsers(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);

    }

}
