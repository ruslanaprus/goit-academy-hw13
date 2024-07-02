package org.example;

import java.net.URI;
import java.util.List;

public class Main {
    private static final String CREATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String UPDATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String BASE_DELETE_USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String GET_USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String GET_USER_BY_ID_URL = "https://jsonplaceholder.typicode.com/users/{id}";
    private static final String GET_USER_BY_USERNAME_URL = "https://jsonplaceholder.typicode.com/users?username={username}";

    public static void main(String[] args) {

        // USER TO ADD TO THE LIST
        User bob = new User();
        bob.setId(1);
        bob.setName("Bob");
        bob.setUsername("Meowskers");
        bob.setEmail("bob@mail.com");

        User.Address address = new User.Address();
        address.setStreet("Curious path");
        address.setSuite("Apt. 42");
        address.setCity("Meowcroft");
        address.setZipcode("111-222");

        User.Address.Geo geo = new User.Address.Geo();
        geo.setLat("24.2133");
        geo.setLng("75.3645");
        address.setGeo(geo);

        bob.setAddress(address);
        bob.setPhone("123-456-769 meow");
        bob.setWebsite("catpaw.com");

        User.Company company = new User.Company();
        company.setName("Purrlock-Holmes");
        company.setCatchPhrase("When the yarn unravels, I’m on the case!");
        company.setBs("Solving mysteries, one paw at a time");

        bob.setCompany(company);

        // UPDATED INFORMATION ABOUT USER
        User alice = new User();
        alice.setName("Alice");
        alice.setUsername("WhiskerWonder");
        alice.setEmail("alice@mail.com");

        alice.setAddress(address);
        alice.setPhone("123-456-769 purr");
        alice.setWebsite("catpaw.com");

        alice.setCompany(company);

        // TASK 1.1: CREATE USER
        final User createdUser = UserController.sendPost(URI.create(CREATE_USER_URL), bob);
        System.out.println("createdUser = " + createdUser);

        // TASK 1.2: UPDATE USER
        int userIdToUpdate = 2;
        User updatedUser = UserController.sendPut(URI.create(UPDATE_USER_URL), userIdToUpdate, alice);
        if (updatedUser != null) {
            System.out.println("User updated successfully:\n" + updatedUser);
        } else {
            System.out.println("Failed to update user.");
        }

        // TASK 1.3: DELETE USER
        int userIdToDelete = 1;
        URI baseUri = URI.create(BASE_DELETE_USERS_URL);
        int statusCode = UserController.sendDelete(baseUri, userIdToDelete);
        System.out.println("HTTP DELETE Response Status Code: " + statusCode);

        if (statusCode == 200 || statusCode == 204) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Failed to delete user.");
        }

        //TASK 1.4: GET LIST OF USERS
        final List<User> users = UserController.sendGetWithListOfResults(URI.create(GET_USERS_URL));
        System.out.println("users = " + users);

        // TASK 1.5: GET USER BY ID
        int userId = 1;
        String userByIdUrl = GET_USER_BY_ID_URL.replace("{id}", String.valueOf(userId));
        final User userById = UserController.sendGet(URI.create(userByIdUrl));
        System.out.println("userById:\n" + userById);

        // TASK 1.6: GET USER BY USERNAME
        String userName = "Delphine";
        String userByUserNameUrl = GET_USER_BY_USERNAME_URL.replace("{username}", userName);

        List<User> usersByUserName = UserController.sendGetWithListOfResults(URI.create(userByUserNameUrl));

        if (usersByUserName.isEmpty()) {
            System.out.println("No user found with username: " + userName);
        } else {
            User userByUserName = usersByUserName.get(0);
            System.out.println("User found by username:\n" + userByUserName);
        }

    }
}