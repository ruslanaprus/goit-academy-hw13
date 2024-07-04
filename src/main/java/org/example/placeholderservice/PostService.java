package org.example.placeholderservice;

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

/**
 * Service class for handling post-related operations.
 */
public class PostService {

    private final HttpUtil httpUtil;
    private final String baseUrl;

    /**
     * Constructs an instance of PostService with the provided HttpUtil and base URL.
     *
     * @param httpUtil the HttpUtil to use for sending requests
     * @param baseUrl  the base URL for post-related endpoints
     */
    public PostService(HttpUtil httpUtil, String baseUrl) {
        this.httpUtil = httpUtil;
        this.baseUrl = baseUrl;
    }

    /**
     * Fetches a list of posts for a specific user by user ID.
     *
     * @param userId the ID of the user
     * @return the list of Post objects, or null if an error occurs
     */
    public List<Post> getPostsByUserId(int userId) {
        URI uri = URI.create(baseUrl + "/users/" + userId + "/posts");
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Post>>() {});
    }

    /**
     * Fetches a list of comments for a specific post by post ID.
     *
     * @param postId the ID of the post
     * @return the list of Comment objects, or null if an error occurs
     */
    public List<Comment> getCommentsByPostId(int postId) {
        URI uri = URI.create(baseUrl + "/posts/" + postId + "/comments");
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Comment>>() {});
    }

    /**
     * Writes the comments of the latest post by a user to a specified file.
     *
     * @param userId       the ID of the user
     * @param directoryPath the directory path where the file should be saved
     */
    public void writeCommentsOfLastPostToFile(int userId, String directoryPath) {
        List<Post> posts = getPostsByUserId(userId);
        Optional<Post> lastPost = posts.stream()
                .max(Comparator.comparingInt(Post::getId));

        if (lastPost.isPresent()) {
            int lastPostId = lastPost.get().getId();
            List<Comment> comments = getCommentsByPostId(lastPostId);

            if (comments.isEmpty()) {
                System.out.println("No comments found for the last post.");
            } else {
                String filePath = String.format("%s/user-%d-post-%d-comments.json", directoryPath, userId, lastPostId);
                writeJsonToFile(comments, filePath);
                System.out.println("Comments written to " + filePath);
            }
        } else {
            System.out.println("No posts found for user with ID: " + userId);
        }
    }

    /**
     * Writes the given data to a file in JSON format.
     *
     * @param data     the data to write to the file
     * @param filePath the path of the file
     * @param <T>      the type of the data
     */
    private <T> void writeJsonToFile(List<T> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = httpUtil.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            writer.write(json);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
    }
}