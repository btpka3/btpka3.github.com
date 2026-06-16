package com.github.btpka3.firstopenapi1st.client.ext;

import com.github.btpka3.firstopenapi1st.client.ApiClient;
import com.github.btpka3.firstopenapi1st.client.api.PetApi;
import com.github.btpka3.firstopenapi1st.client.api.StoreApi;
import com.github.btpka3.firstopenapi1st.client.api.UserApi;

import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Builder for creating pre-configured API client instances with
 * custom authentication, tracing, and timeout settings.
 *
 * <p>Usage:
 * <pre>{@code
 * PetApi petApi = ClientFactory.builder("https://api.example.com/v1")
 *     .bearerAuth(() -> "my-jwt-token")
 *     .apiKey("X-API-Key", "my-api-key")
 *     .readTimeout(Duration.ofSeconds(30))
 *     .build()
 *     .petApi();
 * }</pre>
 */
public class ClientFactory {

    private final ApiClient apiClient;
    private final Map<String, String> staticHeaders = new HashMap<>();
    private Supplier<String> bearerTokenSupplier;
    private boolean traceEnabled = false;

    private ClientFactory(String baseUrl) {
        this.apiClient = new ApiClient();
        this.apiClient.updateBaseUri(baseUrl);
    }

    public static ClientFactory builder(String baseUrl) {
        return new ClientFactory(baseUrl);
    }

    /**
     * Configure Bearer token authentication. The supplier is called on each request,
     * allowing token refresh without recreating the client.
     */
    public ClientFactory bearerAuth(Supplier<String> tokenSupplier) {
        this.bearerTokenSupplier = tokenSupplier;
        return this;
    }

    /**
     * Add a static API key header (e.g. "X-API-Key").
     */
    public ClientFactory apiKey(String headerName, String apiKeyValue) {
        this.staticHeaders.put(headerName, apiKeyValue);
        return this;
    }

    /**
     * Add any static header to all requests.
     */
    public ClientFactory header(String name, String value) {
        this.staticHeaders.put(name, value);
        return this;
    }

    /**
     * Enable automatic X-Request-Id trace header injection.
     */
    public ClientFactory withTracing() {
        this.traceEnabled = true;
        return this;
    }

    /**
     * Set read timeout for all API calls.
     */
    public ClientFactory readTimeout(Duration timeout) {
        this.apiClient.setReadTimeout(timeout);
        return this;
    }

    /**
     * Set connection timeout.
     */
    public ClientFactory connectTimeout(Duration timeout) {
        this.apiClient.setConnectTimeout(timeout);
        return this;
    }

    /**
     * Add a custom response interceptor (e.g. for logging or metrics).
     */
    public ClientFactory onResponse(Consumer<java.net.http.HttpResponse<java.io.InputStream>> interceptor) {
        this.apiClient.setResponseInterceptor(interceptor);
        return this;
    }

    /**
     * Build the factory. After this call, the internal interceptor chain is frozen.
     */
    public ClientFactory build() {
        Consumer<HttpRequest.Builder> composed = requestBuilder -> {
            // Static headers
            staticHeaders.forEach(requestBuilder::header);

            // Bearer auth
            if (bearerTokenSupplier != null) {
                requestBuilder.header("Authorization", "Bearer " + bearerTokenSupplier.get());
            }

            // Trace ID
            if (traceEnabled) {
                requestBuilder.header("X-Request-Id", UUID.randomUUID().toString());
            }
        };

        apiClient.setRequestInterceptor(composed);
        return this;
    }

    /**
     * Get the underlying ApiClient for advanced configuration.
     */
    public ApiClient apiClient() {
        return apiClient;
    }

    public PetApi petApi() {
        return new PetApi(apiClient);
    }

    public StoreApi storeApi() {
        return new StoreApi(apiClient);
    }

    public UserApi userApi() {
        return new UserApi(apiClient);
    }
}
