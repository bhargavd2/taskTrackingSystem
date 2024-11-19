package org.airTribe.taskTrackingSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.airTribe.taskTrackingSystem.controller.RegistrationController;
import org.airTribe.taskTrackingSystem.entity.User;
import org.airTribe.taskTrackingSystem.repository.UserRepository;
import org.airTribe.taskTrackingSystem.service.JwtService;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.*;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository _userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    User getDefaultUser()
    {
        return User.builder()
                .userId(1l)
                .email("t1@gmail.com")
                .firstName("t1")
                .lastName("t")
                .password("test1").build();
    }

    @BeforeEach
    void setUp() {
        User user = getDefaultUser();
        given(_userRepository.findByEmail("t1@gmail.com")).willReturn(Optional.of(user));
    }

    @Test
    public void contextLoads() {}
    
    public void RegisterUser() throws Exception
    {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getDefaultUser())))
                .andExpect(status().isOk());
    }
}
