package com.github.btpka3.firstopenapi1st.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.btpka3.firstopenapi1st.client.api.PetApi;
import com.github.btpka3.firstopenapi1st.client.model.*;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for generated client-api code.
 *
 * <p>Demonstrates three testing patterns:
 * <ol>
 *   <li>Model serialization/deserialization with Jackson ObjectMapper</li>
 *   <li>ApiClient configuration (base URL, timeouts, interceptors)</li>
 *   <li>End-to-end API call with JDK built-in HttpServer as mock backend</li>
 * </ol>
 *
 * <p>Zero external test dependencies — uses only JUnit 5 + JDK built-in HttpServer.
 */
class GeneratedClientTest {

    // ================================================================
    // Pattern 1: Model serialization / deserialization
    // ================================================================

    @Nested
    @DisplayName("Model Serialization")
    class ModelSerializationTest {

        private ObjectMapper mapper;

        @BeforeEach
        void setUp() {
            mapper = ApiClient.createDefaultObjectMapper();
        }

        @Test
        void pet_roundTrip_serialization() throws Exception {
            Pet pet = new Pet();
            pet.setId(42L);
            pet.setName("Buddy");
            pet.setStatus(PetStatus.AVAILABLE);

            Category category = new Category();
            category.setId(1L);
            category.setName("Dogs");
            pet.setCategory(category);

            // Serialize to JSON
            String json = mapper.writeValueAsString(pet);
            assertNotNull(json);
            assertTrue(json.contains("\"name\":\"Buddy\""));
            assertTrue(json.contains("\"status\":\"available\""));

            // Deserialize back
            Pet deserialized = mapper.readValue(json, Pet.class);
            assertEquals(42L, deserialized.getId());
            assertEquals("Buddy", deserialized.getName());
            assertEquals(PetStatus.AVAILABLE, deserialized.getStatus());
            assertEquals("Dogs", deserialized.getCategory().getName());
        }

        @Test
        void pet_unknownFields_ignored() throws Exception {
            String json = """
                {
                    "id": 1,
                    "name": "Test",
                    "status": "pending",
                    "unknownField": "should be ignored"
                }
                """;

            Pet pet = mapper.readValue(json, Pet.class);
            assertEquals("Test", pet.getName());
            assertEquals(PetStatus.PENDING, pet.getStatus());
        }

        @Test
        void petListResponse_deserialization() throws Exception {
            String json = """
                {
                    "data": [
                        {"id": 1, "name": "Rex", "status": "available"},
                        {"id": 2, "name": "Mimi", "status": "sold"}
                    ],
                    "page": {
                        "pageNum": 1,
                        "pageSize": 20,
                        "totalCount": 2,
                        "totalPages": 1
                    }
                }
                """;

            PetListResponse response = mapper.readValue(json, PetListResponse.class);
            assertNotNull(response.getData());
            assertEquals(2, response.getData().size());
            assertEquals("Rex", response.getData().get(0).getName());
            assertEquals(PetStatus.SOLD, response.getData().get(1).getStatus());

            assertNotNull(response.getPage());
            assertEquals(1, response.getPage().getPageNum());
            assertEquals(2L, response.getPage().getTotalCount());
        }

        @Test
        void createPetRequest_serialization() throws Exception {
            CreatePetRequest request = new CreatePetRequest();
            request.setName("NewPet");
            request.setStatus(PetStatus.AVAILABLE);

            String json = mapper.writeValueAsString(request);
            assertTrue(json.contains("\"name\":\"NewPet\""));
            assertTrue(json.contains("\"status\":\"available\""));
        }

        @Test
        void errorResponse_deserialization() throws Exception {
            String json = """
                {
                    "traceId": "046b6c7f-0b8a-43b9-b35d-6489e6daee91",
                    "code": "PET_NOT_FOUND",
                    "message": "Pet with id 123 not found",
                    "fieldErrors": [
                        {"field": "petId", "message": "must exist"}
                    ]
                }
                """;

            ErrorResponse error = mapper.readValue(json, ErrorResponse.class);
            assertEquals("PET_NOT_FOUND", error.getCode());
            assertEquals("Pet with id 123 not found", error.getMessage());
            assertNotNull(error.getFieldErrors());
            assertEquals(1, error.getFieldErrors().size());
            assertEquals("petId", error.getFieldErrors().get(0).getField());
        }
    }

    // ================================================================
    // Pattern 2: ApiClient configuration
    // ================================================================

    @Nested
    @DisplayName("ApiClient Configuration")
    class ApiClientConfigTest {

        @Test
        void defaultBaseUri_fromSpec() {
            ApiClient client = new ApiClient();
            // The spec defines: https://api.example.com/v1
            assertEquals("https://api.example.com/v1", client.getBaseUri());
        }

        @Test
        void customBaseUri() {
            ApiClient client = new ApiClient();
            client.updateBaseUri("http://localhost:8080/api");
            assertEquals("http://localhost:8080/api", client.getBaseUri());
        }

        @Test
        void readTimeout() {
            ApiClient client = new ApiClient();
            assertNull(client.getReadTimeout());

            client.setReadTimeout(Duration.ofSeconds(30));
            assertEquals(Duration.ofSeconds(30), client.getReadTimeout());
        }

        @Test
        void connectTimeout() {
            ApiClient client = new ApiClient();
            assertNull(client.getConnectTimeout());

            client.setConnectTimeout(Duration.ofSeconds(5));
            assertEquals(Duration.ofSeconds(5), client.getConnectTimeout());
        }

        @Test
        void requestInterceptor_canBeSetAndRetrieved() {
            ApiClient client = new ApiClient();
            assertNull(client.getRequestInterceptor());

            client.setRequestInterceptor(builder ->
                builder.header("X-Custom", "value")
            );
            assertNotNull(client.getRequestInterceptor());
        }

        @Test
        void objectMapper_isNotShared() {
            ApiClient client = new ApiClient();
            ObjectMapper mapper1 = client.getObjectMapper();
            ObjectMapper mapper2 = client.getObjectMapper();
            // getObjectMapper() returns a copy each time
            assertNotSame(mapper1, mapper2);
        }

        @Test
        void fluentConfiguration() {
            ApiClient client = new ApiClient()
                .setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(3));

            assertEquals(Duration.ofSeconds(10), client.getReadTimeout());
            assertEquals(Duration.ofSeconds(3), client.getConnectTimeout());
        }
    }

    // ================================================================
    // Pattern 3: End-to-end with mock HttpServer
    // ================================================================

    @Nested
    @DisplayName("End-to-End API Call")
    class EndToEndTest {

        private HttpServer mockServer;
        private int port;

        @BeforeEach
        void startMockServer() throws IOException {
            mockServer = HttpServer.create(new InetSocketAddress(0), 0);
            port = mockServer.getAddress().getPort();

            mockServer.createContext("/pets/42", exchange -> {
                String json = """
                    {"id": 42, "name": "Buddy", "status": "available"}
                    """;
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            });

            mockServer.setExecutor(null);
            mockServer.start();
        }

        @AfterEach
        void stopMockServer() {
            if (mockServer != null) {
                mockServer.stop(0);
            }
        }

        @Test
        void getPetById_returnsDeserializedPet() {
            ApiClient client = new ApiClient();
            client.updateBaseUri("http://localhost:" + port);
            client.setReadTimeout(Duration.ofSeconds(5));

            PetApi petApi = new PetApi(client);
            Pet pet = petApi.getPetById(42L);

            assertNotNull(pet);
            assertEquals(42L, pet.getId());
            assertEquals("Buddy", pet.getName());
            assertEquals(PetStatus.AVAILABLE, pet.getStatus());
        }

        @Test
        void getPetById_withAuthHeader() {
            ApiClient client = new ApiClient();
            client.updateBaseUri("http://localhost:" + port);
            client.setRequestInterceptor(builder ->
                builder.header("Authorization", "Bearer test-token")
            );

            PetApi petApi = new PetApi(client);
            Pet pet = petApi.getPetById(42L);

            assertNotNull(pet);
        }

        @Test
        void getPetById_notFound_throwsApiException() {
            // Add a 404 handler
            mockServer.createContext("/pets/999", exchange -> {
                String json = """
                    {"code": "PET_NOT_FOUND", "message": "not found"}
                    """;
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(404, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            });

            ApiClient client = new ApiClient();
            client.updateBaseUri("http://localhost:" + port);

            PetApi petApi = new PetApi(client);

            ApiException ex = assertThrows(ApiException.class, () ->
                petApi.getPetById(999L)
            );
            assertEquals(404, ex.getCode());
            assertTrue(ex.getMessage().contains("getPetById"));
        }
    }

    // ================================================================
    // Pattern 4: Utility methods
    // ================================================================

    @Nested
    @DisplayName("ApiClient Utilities")
    class UtilityTest {

        @Test
        void urlEncode_specialCharacters() {
            assertEquals("hello%20world", ApiClient.urlEncode("hello world"));
            assertEquals("foo%26bar", ApiClient.urlEncode("foo&bar"));
            assertEquals("a%2Bb", ApiClient.urlEncode("a+b"));
        }

        @Test
        void parameterToPairs_nullValue_returnsEmpty() {
            List<Pair> pairs = ApiClient.parameterToPairs("key", (Object) null);
            assertTrue(pairs.isEmpty());
        }

        @Test
        void parameterToPairs_normalValue() {
            List<Pair> pairs = ApiClient.parameterToPairs("status", "available");
            assertEquals(1, pairs.size());
            assertEquals("status", pairs.get(0).getName());
            assertEquals("available", pairs.get(0).getValue());
        }

        @Test
        void valueToString_offsetDateTime() {
            OffsetDateTime dt = OffsetDateTime.parse("2026-01-15T10:30:00+08:00");
            String result = ApiClient.valueToString(dt);
            assertTrue(result.contains("2026-01-15"));
        }

        @Test
        void valueToString_null_returnsEmpty() {
            assertEquals("", ApiClient.valueToString(null));
        }
    }
}
