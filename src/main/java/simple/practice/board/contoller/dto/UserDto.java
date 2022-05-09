package simple.practice.board.contoller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {

    private LocalDateTime createDate;
    private String userId;
    private String email;

}
