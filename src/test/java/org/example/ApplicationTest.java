package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Comment;
import org.example.dto.Post;
import org.example.dto.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static HttpClient client;
    private static ObjectMapper objectMapper;
    private static HttpUtil httpUtil;
    private static UserService userService;
    private static PostService postService;
    private static TaskService taskService;
    private static final String TEST_OUTPUT_FILE = "src/main/resources/user-%d-post-%d-comments.json";

    @BeforeAll
    public static void setUp() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        httpUtil = new HttpUtil(client, objectMapper);

        userService = new UserService(httpUtil, BASE_URL);
        postService = new PostService(httpUtil, BASE_URL);
        taskService = new TaskService(httpUtil);
    }

    @Test
    public void testCreateUser() {
        User bob = MockUser.createNewUser();
        User createdUser = userService.createUser(URI.create(BASE_URL + "/users"), bob);
        System.out.println("\nTASK 1.1: CREATE NEW USER\ncreatedUser: " + createdUser + "\n");
        assertNotNull(createdUser);
        assertEquals(11, createdUser.getId());
        assertEquals(bob.getName(), createdUser.getName());
        assertEquals(bob.getUsername(), createdUser.getUsername());
        assertEquals(bob.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        User alice = MockUser.createUpdatedUser();
        int userIdToUpdate = 10;
        User updatedUser = userService.updateUser(URI.create(BASE_URL + "/users/" + userIdToUpdate), alice);
        System.out.println("\nTASK 1.2: UPDATE USER\nUser with ID=" + userIdToUpdate + " updated successfully:" + updatedUser);
        assertNotNull(updatedUser);
        assertEquals(alice.getName(), updatedUser.getName());
        assertEquals(alice.getUsername(), updatedUser.getUsername());
        assertEquals(alice.getEmail(), updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        int userIdToDelete = 1;
        int statusCode = userService.deleteUser(URI.create(BASE_URL + "/users/" + userIdToDelete));
        System.out.println("\nTASK 1.3: DELETE USER:\nHTTP DELETE Response Status Code: " + statusCode);
        assertEquals(200, statusCode);
    }

    @Test
    public void testGetUsers() {
        List<User> users = userService.getUsers(URI.create(BASE_URL + "/users"));
        System.out.println("\nTASK 1.4: GET LIST OF USERS:\n" + users);
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUserById() {
        int userId = 5;
        User userById = userService.getUser(URI.create(BASE_URL + "/users/" + userId));
        System.out.println("\nTASK 1.5: GET USER BY ID (tested with ID=" + userId + "):\n" + userById);
        assertNotNull(userById);
        assertEquals(userId, userById.getId());
    }

    @Test
    public void testGetUserByUsername() {
        String userName = "Delphine";
        List<User> usersByUserName = userService.getUsers(URI.create(BASE_URL + "/users?username=" + userName));
        if (usersByUserName.isEmpty()) {
            System.out.println("No user found with username: " + userName);
        } else {
            User userByUserName = usersByUserName.get(0);
            System.out.println("\nTASK 1.6: GET USER BY USERNAME:\nUser found by username=" + userName + "\n" + userByUserName);
            assertEquals(userName, userByUserName.getUsername());
        }
    }

    @Test
    public void testWriteCommentsOfLastPostToFile() {
        int userIdComments = 2;
        postService.writeCommentsOfLastPostToFile(userIdComments);
        System.out.println("Comments of last post for user with ID " + userIdComments + " written to file.");
    }

    @Test
    public void testWriteCommentsOfLastPostToFileWithContent() {
        int userIdComments = 3;
        postService.writeCommentsOfLastPostToFile(userIdComments);

        List<Post> posts = postService.getPostsByUserId(userIdComments);
        if (!posts.isEmpty()) {
            int lastPostId = posts.get(posts.size() - 1).getId();

            String filePath = String.format(TEST_OUTPUT_FILE, userIdComments, lastPostId);
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            assertTrue(file.exists());

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                List<Comment> comments = objectMapper.readValue(content.toString(), new TypeReference<List<Comment>>() {});
                assertNotNull(comments);
                assertFalse(comments.isEmpty());

                System.out.println(comments);
            } catch (IOException e) {
                fail("Error reading the file: " + e.getMessage());
            }
        } else {
            fail("No posts found for user with ID " + userIdComments);
        }
    }

    @Test
    public void testGetOpenTasks() {
        int userIdTasks = 3;
        List<String> tasks = taskService.getOpenTasksByUserId(URI.create(BASE_URL + "/users/" + userIdTasks + "/todos"));
        System.out.println("\nTASK 3: GET UNFINISHED TASKS (tested for userId=" + userIdTasks + "):\n" + tasks);
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }
}