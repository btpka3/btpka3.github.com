package com.github.btpka3.first.springdoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void apiDocsShouldReturnOpenApiJson() throws Exception {
        MvcResult result = mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.openapi").exists())
                .andExpect(jsonPath("$.info.title").value("First SpringDoc Demo API"))
                .andExpect(jsonPath("$.paths['/api/users']").exists())
                .andExpect(jsonPath("$.paths['/api/orders']").exists())
                .andExpect(jsonPath("$.components.securitySchemes.Bearer").exists())
                .andReturn();

        writeToFile("openapi.json", result.getResponse().getContentAsString());
    }

    @Test
    void userApiGroupShouldOnlyContainUserPaths() throws Exception {
        MvcResult result = mockMvc.perform(get("/v3/api-docs/user-api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/users']").exists())
                .andExpect(jsonPath("$.paths['/api/orders']").doesNotExist())
                .andReturn();

        writeToFile("user-api.json", result.getResponse().getContentAsString());
    }

    @Test
    void orderApiGroupShouldOnlyContainOrderPaths() throws Exception {
        MvcResult result = mockMvc.perform(get("/v3/api-docs/order-api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/orders']").exists())
                .andExpect(jsonPath("$.paths['/api/users']").doesNotExist())
                .andReturn();

        writeToFile("order-api.json", result.getResponse().getContentAsString());
    }

    @Test
    void swaggerUiShouldReturn404() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isNotFound());
    }

    private void writeToFile(String filename, String content) throws IOException {
        Path dir = Path.of("target/openapi");
        Files.createDirectories(dir);
        Files.writeString(dir.resolve(filename), content);
    }
}
