package simple.practice.board.contoller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserSignUpDto {

    private LocalDateTime createDate;
    private String userId;
    private String password;
    private String userEmail;

}
