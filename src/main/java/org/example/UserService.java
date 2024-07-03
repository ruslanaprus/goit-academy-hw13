package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dto.User;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

public class UserService {

    private final HttpUtil httpUtil;
    private final String baseUrl;

    public UserService(HttpUtil httpUtil, String baseUrl) {
        this.httpUtil = httpUtil;
        this.baseUrl = baseUrl;
    }

    public User getUser(URI uri) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), User.class);
    }

    public User createUser(URI uri, User user) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(httpUtil.serialize(user)))
                .header("Content-type", "application/json"), User.class);
    }

    public User updateUser(URI uri, User user) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(httpUtil.serialize(user)))
                .header("Content-type", "application/json"), User.class);
    }

    public int deleteUser(URI uri) {
        return httpUtil.sendDeleteRequest(uri, HttpRequest.newBuilder().DELETE());
    }

    public List<User> getUsers(URI uri) {
        return httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<User>>() {
        });
    }
}