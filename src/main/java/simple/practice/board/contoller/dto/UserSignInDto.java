package simple.practice.board.contoller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserSignInDto {

    private String userId;
    private String password;
    private String userEmail;

}
