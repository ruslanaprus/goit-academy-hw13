package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.dto.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {
    private static final WireMockServer wireMockServer = new WireMockServer(8080);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static UserService userService;

    @BeforeAll
    public static void setup() {
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
        HttpClient client = HttpClient.newHttpClient();
        HttpUtil httpUtil = new HttpUtil(client, OBJECT_MAPPER);
        userService = new UserService(httpUtil, "http://localhost:8080");
    }

    @AfterAll
    public static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetUser() throws URISyntaxException, IOException {
        User bob = createUser();
        wireMockServer.stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(OBJECT_MAPPER.writeValueAsString(bob))));

        URI uri = new URI("http://localhost:8080/users/1");
        User user = userService.getUser(uri);

        assertNotNull(user);
        assertEquals(bob.getId(), user.getId());
        assertEquals(bob.getName(), user.getName());
        assertEquals(bob.getUsername(), user.getUsername());
        assertEquals(bob.getEmail(), user.getEmail());
        assertEquals(bob.getAddress().getStreet(), user.getAddress().getStreet());
        assertEquals(bob.getAddress().getSuite(), user.getAddress().getSuite());
        assertEquals(bob.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(bob.getAddress().getZipcode(), user.getAddress().getZipcode());
        assertEquals(bob.getAddress().getGeo().getLat(), user.getAddress().getGeo().getLat());
        assertEquals(bob.getAddress().getGeo().getLng(), user.getAddress().getGeo().getLng());
        assertEquals(bob.getPhone(), user.getPhone());
        assertEquals(bob.getWebsite(), user.getWebsite());
        assertEquals(bob.getCompany().getName(), user.getCompany().getName());
        assertEquals(bob.getCompany().getCatchPhrase(), user.getCompany().getCatchPhrase());
        assertEquals(bob.getCompany().getBs(), user.getCompany().getBs());
    }

    @Test
    public void testCreateUser() throws URISyntaxException, IOException {
        User bob = createUser();
        String userJson = OBJECT_MAPPER.writeValueAsString(bob);

        wireMockServer.stubFor(post(urlEqualTo("/users"))
                .withRequestBody(equalToJson(userJson))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(userJson)));

        URI uri = new URI("http://localhost:8080/users");
        User createdUser = userService.createUser(uri, bob);

        assertNotNull(createdUser);
        assertEquals(bob.getName(), createdUser.getName());
    }

    @Test
    public void testUpdateUser() throws URISyntaxException, IOException {
        User bob = createUser();
        String userJson = OBJECT_MAPPER.writeValueAsString(bob);

        wireMockServer.stubFor(put(urlEqualTo("/users/2"))
                .withRequestBody(equalToJson(userJson))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(userJson)));

        URI uri = new URI("http://localhost:8080/users/2");
        User updatedUser = userService.updateUser(uri, bob);

        assertNotNull(updatedUser);
        assertEquals(bob.getName(), updatedUser.getName());
    }

    @Test
    public void testDeleteUser() throws URISyntaxException {
        wireMockServer.stubFor(delete(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)));

        URI uri = new URI("http://localhost:8080/users/1");
        int statusCode = userService.deleteUser(uri);

        assertEquals(200, statusCode);
    }

    @Test
    public void testGetUsers() throws URISyntaxException, IOException {
        User user1 = createUser();
        User user2 = createUser();
        List<User> users = List.of(user1, user2);
        String usersJson = OBJECT_MAPPER.writeValueAsString(users);

        wireMockServer.stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(usersJson)));

        URI uri = new URI("http://localhost:8080/users");
        List<User> result = userService.getUsers(uri);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user2.getName(), result.get(1).getName());
    }

    private User createUser() {
        User user = new User();
        user.setId(1);
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
