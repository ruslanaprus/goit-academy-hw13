package org.example.placeholderservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Utility class for sending HTTP requests and processing JSON responses using HttpClient and Jackson ObjectMapper.
 */
public class HttpUtil {

    public final ObjectMapper objectMapper;
    private final HttpClient client;

    /**
     * Constructs an instance of HttpUtil with the provided HttpClient and ObjectMapper.
     *
     * @param objectMapper  the ObjectMapper to use for JSON processing
     * @param client        the HttpClient to use for sending requests
     */
    public HttpUtil(HttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    /**
     * Sends an HTTP request and deserializes the response into the specified response type.
     *
     * @param uri           the URI of the request
     * @param requestBuilder the builder for constructing the HTTP request
     * @param responseType  the class of the response type
     * @param <T>           the type of the response
     * @return the deserialized response, or null if an error occurs
     */
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

    /**
     * Sends an HTTP request and deserializes the response into the specified generic type.
     *
     * @param uri           the URI of the request
     * @param requestBuilder the builder for constructing the HTTP request
     * @param responseType  the TypeReference of the response type
     * @param <T>           the type of the response
     * @return the deserialized response, or null if an error occurs
     */
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

    /**
     * Sends a DELETE request and returns the status code of the response.
     *
     * @param uri            the URI of the request
     * @param requestBuilder the builder for constructing the HTTP request
     * @return the status code of the response, or -1 if an error occurs
     */
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

    /**
     * Serializes an object into a JSON string.
     *
     * @param obj the object to serialize
     * @return the JSON string representation of the object
     * @throws RuntimeException if serialization fails
     */
    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    /**
     * Handles exceptions by logging the error message and interrupting the thread if the exception is an InterruptedException.
     *
     * @param e the exception to handle
     */
    private void handleError(Exception e) {
        System.err.println("Error occurred: " + e.getMessage());
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}