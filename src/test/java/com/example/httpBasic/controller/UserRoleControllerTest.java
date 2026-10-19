package com.example.httpBasic.controller;


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
class UserRoleControllerTest {

    String USER_ENDPOINT = "/api/user";
    String ADMIN_ENDPOINT = "/api/admin";

    String USERNAME = "gibson@example.com";
    String ADMIN_USERNAME = "admin@example.com";

    String PASSWORD = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowUserDoRequestToUserEndpoint() throws Exception {
        mockMvc.perform(get(USER_ENDPOINT)
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status()
                        .isAccepted());
    }

    @Test
    void shouldDenyUserDoRequestToAdminEndpoint() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT)
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    void shouldAllowAdminDoRequestToAdminEndpoint() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT)
                        .with(httpBasic(ADMIN_USERNAME, PASSWORD)))
                .andExpect(status()
                        .isAccepted());
    }

    @Test
    void shouldAllowAdminDoRequestToUserEndpoint() throws Exception {
        mockMvc.perform(get(USER_ENDPOINT)
                        .with(httpBasic(ADMIN_USERNAME, PASSWORD)))
                .andExpect(status()
                        .isAccepted());
    }
}