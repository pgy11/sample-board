package simple.practice.board.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import simple.practice.board.contoller.UserController;
import simple.practice.board.contoller.dto.UserSignInDto;
import simple.practice.board.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsers() throws Exception {

        int page = 0;
        int size = 3;
        List<UserSignInDto> userDtoList = Arrays.asList(
                UserSignInDto.builder().userId("hello-world1").userEmail("ex1@hello.com").build(),
                UserSignInDto.builder().userId("hello-world2").userEmail("ex2@hello.com").build(),
                UserSignInDto.builder().userId("hello-world3").userEmail("ex3@hello.com").build()
        );

        PageImpl<UserSignInDto> userDtoPage = new PageImpl<>(userDtoList);
        when(userService.getUsers(page, size)).thenReturn(userDtoPage);
        mockMvc.perform(get("/api/users?page=0&size=3")).andDo(print());

    }

}
