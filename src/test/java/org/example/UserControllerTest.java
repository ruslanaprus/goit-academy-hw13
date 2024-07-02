package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest {
    private static final WireMockServer wireMockServer = new WireMockServer(8080);
    private static final String GET_USER_BY_ID_URL = "http://localhost:8080/users/{id}";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
    }

    @AfterAll
    public static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testSendGet() throws URISyntaxException, IOException, InterruptedException {
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

        wireMockServer.stubFor(get(WireMock.urlPathMatching("/users/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(OBJECT_MAPPER.writeValueAsString(bob))));

        URI uri = new URI(GET_USER_BY_ID_URL.replace("{id}", "1"));
        User user = UserController.sendGet(uri);

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
}
