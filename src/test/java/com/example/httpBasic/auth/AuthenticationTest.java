package com.example.httpBasic.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAuthenticateWithValidCredentials() throws Exception {
        mockMvc.perform(get("/api/user")
                        .with(httpBasic("gibson@example.com", "admin")))
                .andExpect(status().isAccepted());
    }
}
