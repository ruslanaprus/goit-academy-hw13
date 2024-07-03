package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpUtil {

    private final HttpClient client;
    public final ObjectMapper objectMapper;

    public HttpUtil(HttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public <T> T sendRequest(URI uri, HttpRequest.Builder requestBuilder, Class<T> responseType) {
        try {
            HttpRequest request = requestBuilder
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseType);
        } catch (IOException | InterruptedException e) {
            handleError(e);
        }
        return null;
    }

    public <T> T sendRequest(URI uri, HttpRequest.Builder requestBuilder, TypeReference<T> responseType) {
        try {
            HttpRequest request = requestBuilder
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseType);
        } catch (IOException | InterruptedException e) {
            handleError(e);
        }
        return null;
    }

    public int sendDeleteRequest(URI uri, HttpRequest.Builder requestBuilder) {
        try {
            HttpRequest request = requestBuilder
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            handleError(e);
        }
        return -1;
    }

    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    private void handleError(Exception e) {
        System.err.println("Error occurred: " + e.getMessage());
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}