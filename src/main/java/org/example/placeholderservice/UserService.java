package org.example.placeholderservice;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dto.User;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

/**
 * Service class for handling user-related operations.
 */
public class UserService {

    private final HttpUtil httpUtil;
    private final String baseUrl;

    /**
     * Constructs an instance of UserService with the provided HttpUtil and base URL.
     *
     * @param httpUtil the HttpUtil to use for sending requests
     * @param baseUrl  the base URL for user-related endpoints
     */
    public UserService(HttpUtil httpUtil, String baseUrl) {
        this.httpUtil = httpUtil;
        this.baseUrl = baseUrl;
    }

    /**
     * Fetches a user by the given URI.
     *
     * @param uri the URI of the user endpoint
     * @return the fetched User object, or null if an error occurs
     */
    public User getUser(URI uri) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), User.class);
    }

    /**
     * Creates a new user by sending a POST request with the user data.
     *
     * @param uri  the URI of the user creation endpoint
     * @param user the User object containing user data
     * @return the created User object, or null if an error occurs
     */
    public User createUser(URI uri, User user) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(httpUtil.serialize(user)))
                .header("Content-type", "application/json"), User.class);
    }

    /**
     * Updates an existing user by sending a PUT request with the updated user data.
     *
     * @param uri  the URI of the user update endpoint
     * @param user the User object containing updated user data
     * @return the updated User object, or null if an error occurs
     */
    public User updateUser(URI uri, User user) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(httpUtil.serialize(user)))
                .header("Content-type", "application/json"), User.class);
    }

    /**
     * Deletes a user by sending a DELETE request to the given URI.
     *
     * @param uri the URI of the user deletion endpoint
     * @return the status code of the response, or -1 if an error occurs
     */
    public int deleteUser(URI uri) {
        return httpUtil.sendDeleteRequest(uri, HttpRequest.newBuilder().DELETE());
    }

    /**
     * Fetches a list of users by the given URI.
     *
     * @param uri the URI of the users endpoint
     * @return the list of User objects, or null if an error occurs
     */
    public List<User> getUsers(URI uri) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<User>>() {
        });
    }
}