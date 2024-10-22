package org.airTribe.taskTrackingSystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

import org.airTribe.taskTrackingSystem.entity.User;
import org.airTribe.taskTrackingSystem.service.JwtService;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
public class JwtServiceTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private JwtService jwtService;

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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = getDefaultUser();
        token = jwtService.generateToken(user,"login");

    }

    @Test
    public void testValidToken() {

        boolean isValid = jwtService.isTokenValid(token, getDefaultUser());
        assertNotNull(isValid);
        assertTrue(isValid, "Token should be valid");
    }

}
