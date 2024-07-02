package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpTimeoutException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class UserController {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static User sendGet(URI uri) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return OBJECT_MAPPER.readValue(response.body(), User.class);
        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error occurred: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public static User sendPost(URI uri, User user) {
        try {
            final String requestBody = OBJECT_MAPPER.writeValueAsString(user);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-type", "application/json")
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return OBJECT_MAPPER.readValue(response.body(), User.class);
        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error occurred: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return null;
    }

    public static User sendPut(URI baseUri, int userId, User user) {
        try {
            final String requestBody = OBJECT_MAPPER.writeValueAsString(user);

            URI uri = URI.create(baseUri.toString() + "/" + userId);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-type", "application/json")
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return OBJECT_MAPPER.readValue(response.body(), User.class);

        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error occurred: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return null;
    }

    public static int sendDelete(URI baseUri, int userId) {
        try {
            URI uri = URI.create(baseUri.toString() + "/" + userId);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-type", "application/json")
                    .method("DELETE", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();

        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error occurred: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return -1;
    }

    public static List<User> sendGetWithListOfResults(URI uri) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<User>>() {
            });

        } catch (HttpTimeoutException e) {
            System.err.println("Request timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error occurred: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return List.of();
    }
}
