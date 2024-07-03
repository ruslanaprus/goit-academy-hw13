package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dto.Comment;
import org.example.dto.Post;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PostService {

    private final HttpUtil httpUtil;
    private final String baseUrl;

    public PostService(HttpUtil httpUtil, String baseUrl) {
        this.httpUtil = httpUtil;
        this.baseUrl = baseUrl;
    }

    public List<Post> getPostsByUserId(int userId) {
        URI uri = URI.create(baseUrl + "/users/" + userId + "/posts");
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Post>>() {
        });
    }

    public List<Comment> getCommentsByPostId(int postId) {
        URI uri = URI.create(baseUrl + "/posts/" + postId + "/comments");
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Comment>>() {
        });
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

    private <T> void writeJsonToFile(List<T> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = httpUtil.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            writer.write(json);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
    }
}