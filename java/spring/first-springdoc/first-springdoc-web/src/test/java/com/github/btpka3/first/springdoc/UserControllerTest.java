package com.github.btpka3.first.springdoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void crudWorkflow() throws Exception {
        String userJson = """
                {"name": "zhangsan", "email": "zhangsan@example.com", "bio": "test"}
                """;

        // UserController 未显式设置状态码，create 默认返回 200
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("zhangsan"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("zhangsan"));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        String updateJson = """
                {"name": "lisi", "email": "lisi@example.com", "bio": "updated"}
                """;
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("lisi"));

        // void 返回默认 200
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        // 删除后查询，ResponseStatusException 返回 404
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void orderCreateShouldReturn201WithLocationHeader() throws Exception {
        String orderJson = """
                {"userId": 1, "amount": 99.99}
                """;

        // OrderController 通过 HttpServletResponse 显式设置 201 + Location 头
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void orderGetByIdNotFoundShouldReturn404() throws Exception {
        // OrderController 通过 HttpServletResponse.setStatus(404) 设置
        mockMvc.perform(get("/api/orders/9999"))
                .andExpect(status().isNotFound());
    }
}
