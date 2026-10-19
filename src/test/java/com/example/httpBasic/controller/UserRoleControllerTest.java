package com.example.httpBasic.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowUserDoRequestToUserEndpoint() throws Exception {
        mockMvc.perform(get("/api/user")
                        .with(httpBasic("gibson@example.com", "admin")))
                .andExpect(status()
                        .isAccepted());
    }

    @Test
    void shouldDenyUserDoRequestToAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin")
                        .with(httpBasic("gibson@example.com", "admin")))
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    void shouldAllowAdminDoRequestToAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin")
                        .with(httpBasic("admin@example.com", "admin")))
                .andExpect(status()
                        .isAccepted());
    }

    @Test
    void shouldAllowAdminDoRequestToUserEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin")
                        .with(httpBasic("admin@example.com", "admin")))
                .andExpect(status()
                        .isAccepted());
    }
}