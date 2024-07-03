package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;

public class Main {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        UserController userController = new UserController(client, objectMapper);

        User bob = createUser();
        User alice = createUser();

        // Task 1.1: Create User
        User createdUser = userController.createUser(URI.create(BASE_URL), bob);
        System.out.println("createdUser = " + createdUser);

        // Task 1.2: Update User
        int userIdToUpdate = 2;
        User updatedUser = userController.updateUser(URI.create(BASE_URL + "/" + userIdToUpdate), alice);
        System.out.println("User updated successfully:\n" + updatedUser);

        // Task 1.3: Delete User
        int userIdToDelete = 1;
        int statusCode = userController.deleteUser(URI.create(BASE_URL + "/" + userIdToDelete));
        System.out.println("HTTP DELETE Response Status Code: " + statusCode);

        // Task 1.4: Get List of Users
        List<User> users = userController.getUsers(URI.create(BASE_URL));
        System.out.println("users = " + users);

        // Task 1.5: Get User by ID
        int userId = 1;
        User userById = userController.getUser(URI.create(BASE_URL + "/" + userId));
        System.out.println("userById:\n" + userById);

        // Task 1.6: Get User by Username
        String userName = "Delphine";
        List<User> usersByUserName = userController.getUsers(URI.create(BASE_URL + "?username=" + userName));
        if (usersByUserName.isEmpty()) {
            System.out.println("No user found with username: " + userName);
        } else {
            User userByUserName = usersByUserName.get(0);
            System.out.println("User found by username:\n" + userByUserName);
        }
    }

    private static User createUser() {
        User user = new User();
        user.setName("Bob");
        user.setUsername("Meowskers");
        user.setEmail("bob@mail.com");

        User.Address address = new User.Address();
        address.setStreet("Curious path");
        address.setSuite("Apt. 42");
        address.setCity("Meowcroft");
        address.setZipcode("111-222");

        User.Address.Geo geo = new User.Address.Geo();
        geo.setLat("24.2133");
        geo.setLng("75.3645");
        address.setGeo(geo);

        user.setAddress(address);
        user.setPhone("123-456-769 meow");
        user.setWebsite("catpaw.com");

        User.Company company = new User.Company();
        company.setName("Purrlock-Holmes");
        company.setCatchPhrase("When the yarn unravels, I’m on the case!");
        company.setBs("Solving mysteries, one paw at a time");

        user.setCompany(company);

        return user;
    }
}