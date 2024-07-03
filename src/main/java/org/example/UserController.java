package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class UserController {

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public UserController(HttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public User getUser(URI uri) {
        return sendRequest(uri, HttpRequest.newBuilder().GET(), User.class);
    }

    public User createUser(URI uri, User user) {
        return sendRequest(uri, HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(serialize(user))).header("Content-type", "application/json"), User.class);
    }

    public User updateUser(URI uri, User user) {
        return sendRequest(uri, HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(serialize(user))).header("Content-type", "application/json"), User.class);
    }

    public int deleteUser(URI uri) {
        return sendDeleteRequest(uri, HttpRequest.newBuilder().DELETE());
    }

    public List<User> getUsers(URI uri) {
        return sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<User>>() {});
    }

    private <T> T sendRequest(URI uri, HttpRequest.Builder requestBuilder, Class<T> responseType) {
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

    private <T> T sendRequest(URI uri, HttpRequest.Builder requestBuilder, TypeReference<T> responseType) {
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

    private int sendDeleteRequest(URI uri, HttpRequest.Builder requestBuilder) {
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

    private String serialize(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize user", e);
        }
    }

    private void handleError(Exception e) {
        System.err.println("Error occurred: " + e.getMessage());
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}