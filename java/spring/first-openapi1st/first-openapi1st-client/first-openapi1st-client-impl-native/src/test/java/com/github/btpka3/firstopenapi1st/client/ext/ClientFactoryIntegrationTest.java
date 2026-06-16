package com.github.btpka3.firstopenapi1st.client.ext;

import com.github.btpka3.firstopenapi1st.client.api.PetApi;
import com.github.btpka3.firstopenapi1st.client.model.Pet;
import com.github.btpka3.firstopenapi1st.client.model.PetListResponse;
import com.github.btpka3.firstopenapi1st.client.model.PetStatus;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for PetApi using JDK's built-in HttpServer as a mock backend.
 * Tests the full request/response cycle including custom auth header injection.
 */
class ClientFactoryIntegrationTest {

    private static HttpServer mockServer;
    private static int port;

    @BeforeAll
    static void startMockServer() throws IOException {
        mockServer = HttpServer.create(new InetSocketAddress(0), 0);
        port = mockServer.getAddress().getPort();

        // GET /pets/{petId} -> return a canned Pet
        mockServer.createContext("/pets/42", exchange -> {
            String method = exchange.getRequestMethod();
            if ("GET".equals(method)) {
                String json = """
                    {
                      "id": 42,
                      "name": "Buddy",
                      "status": "available",
                      "category": {"id": 1, "name": "Dogs"},
                      "tags": [{"id": 1, "name": "friendly"}]
                    }
                    """;
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
            }
        });

        // GET /pets -> return a canned PetListResponse
        mockServer.createContext("/pets", exchange -> {
            String method = exchange.getRequestMethod();
            if ("GET".equals(method)) {
                String json = """
                    {
                      "data": [
                        {"id": 1, "name": "Rex", "status": "available"},
                        {"id": 2, "name": "Mimi", "status": "pending"}
                      ],
                      "page": {"pageNum": 1, "pageSize": 20, "totalCount": 2, "totalPages": 1}
                    }
                    """;
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
            }
        });

        mockServer.setExecutor(null); // default executor
        mockServer.start();
    }

    @AfterAll
    static void stopMockServer() {
        if (mockServer != null) {
            mockServer.stop(0);
        }
    }

    @Test
    void getPetById_returnsDeserializedPet() {
        PetApi petApi = ClientFactory.builder("http://localhost:" + port)
                .build()
                .petApi();

        Pet pet = petApi.getPetById(42L);

        assertNotNull(pet);
        assertEquals(42L, pet.getId());
        assertEquals("Buddy", pet.getName());
        assertEquals(PetStatus.AVAILABLE, pet.getStatus());
        assertNotNull(pet.getCategory());
        assertEquals("Dogs", pet.getCategory().getName());
    }

    @Test
    void listPets_returnsDeserializedList() {
        PetApi petApi = ClientFactory.builder("http://localhost:" + port)
                .build()
                .petApi();

        PetListResponse response = petApi.listPets(1, 20, null, null);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("Rex", response.getData().get(0).getName());
    }

    @Test
    void bearerAuth_injectsAuthorizationHeader() {
        AtomicReference<String> capturedAuthHeader = new AtomicReference<>();

        // Add a context that captures the auth header
        mockServer.createContext("/pets/99", exchange -> {
            capturedAuthHeader.set(exchange.getRequestHeaders().getFirst("Authorization"));
            String json = """
                {"id": 99, "name": "TestPet", "status": "sold"}
                """;
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        PetApi petApi = ClientFactory.builder("http://localhost:" + port)
                .bearerAuth(() -> "test-jwt-token-12345")
                .build()
                .petApi();

        petApi.getPetById(99L);

        assertEquals("Bearer test-jwt-token-12345", capturedAuthHeader.get());
    }

    @Test
    void customHeaders_areInjectedIntoRequests() {
        AtomicReference<String> capturedApiKey = new AtomicReference<>();
        AtomicReference<String> capturedTraceId = new AtomicReference<>();

        mockServer.createContext("/pets/88", exchange -> {
            capturedApiKey.set(exchange.getRequestHeaders().getFirst("X-API-Key"));
            capturedTraceId.set(exchange.getRequestHeaders().getFirst("X-Request-Id"));
            String json = """
                {"id": 88, "name": "HeaderPet", "status": "available"}
                """;
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        PetApi petApi = ClientFactory.builder("http://localhost:" + port)
                .apiKey("X-API-Key", "secret-key-abc")
                .withTracing()
                .build()
                .petApi();

        petApi.getPetById(88L);

        assertEquals("secret-key-abc", capturedApiKey.get());
        assertNotNull(capturedTraceId.get(), "X-Request-Id should be set when tracing is enabled");
    }
}
