package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Comment;
import org.example.dto.Post;
import org.example.dto.Task;
import org.example.dto.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public UserService(HttpClient client, ObjectMapper objectMapper, String baseUrl) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
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

    public List<Post> getPostsByUserId(int userId) {
        URI uri = URI.create(baseUrl + "/users/" + userId + "/posts");
        return sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Post>>() {});
    }

    public List<Comment> getCommentsByPostId(int postId) {
        URI uri = URI.create(baseUrl + "/posts/" + postId + "/comments");
        return sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Comment>>() {});
    }

    public void writeCommentsOfLastPostToFile(int userId) {
        List<Post> posts = getPostsByUserId(userId);
        Optional<Post> lastPost = posts.stream()
                .max(Comparator.comparingInt(Post::getId));

        if (lastPost.isPresent()) {
            int lastPostId = lastPost.get().getId();
            List<Comment> comments = getCommentsByPostId(lastPostId);

            if (comments.isEmpty()) {
                System.out.println("No comments found for the last post.");
            } else {
                String filePath = String.format("src/main/resources/user-%d-post-%d-comments.json", userId, lastPostId);
                writeJsonToFile(comments, filePath);
                System.out.println("Comments written to " + filePath);
            }
        } else {
            System.out.println("No posts found for user with ID: " + userId);
        }
    }

    public List<String> getOpenTasksByUserId(URI uri) {
        List<Task> tasks = sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Task>>() {});
        if (tasks != null) {
            return tasks.stream()
                    .filter(task -> !task.isCompleted())
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
        }
        return null;
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

    private <T> void writeJsonToFile(List<T> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            writer.write(json);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
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