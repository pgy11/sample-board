package simple.practice.board.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.practice.board.contoller.dto.UserSignInDto;
import simple.practice.board.service.UserService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserSignInDto>> getUsers(
            @RequestParam @Min(0) int page,
            @RequestParam @Min(1) int size) {

        Page<UserSignInDto> userDtos = userService.getUsers(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);

    }

}
