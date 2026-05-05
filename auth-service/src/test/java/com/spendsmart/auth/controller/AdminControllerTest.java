package com.spendsmart.auth.controller;

import com.spendsmart.auth.entity.User;
import com.spendsmart.auth.repository.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false) // ✅ IMPORTANT: disables security
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    // ✅ TEST: GET /admin/users
    @Test
    void testGetAllUsers() throws Exception {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("Aaruu");

        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@mail.com"))
                .andExpect(jsonPath("$[0].name").value("Aaruu"));
    }

    // ✅ TEST: DELETE /admin/users/{id}
    @Test
    void testDeleteUser() throws Exception {

        doNothing().when(userRepository).deleteById(1L);

        mockMvc.perform(delete("/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    // ✅ TEST: GET /admin/stats
    @Test
    void testStats() throws Exception {

        when(userRepository.count()).thenReturn(5L);

        mockMvc.perform(get("/admin/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(5));
    }
}