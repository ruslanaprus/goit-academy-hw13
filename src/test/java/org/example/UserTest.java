package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final String JSON = "{ \"id\": 1, \"name\": \"Leanne Graham\", \"username\": \"Bret\", \"email\": \"Sincere@april.biz\", \"address\": { \"street\": \"Kulas Light\", \"suite\": \"Apt. 556\", \"city\": \"Gwenborough\", \"zipcode\": \"92998-3874\", \"geo\": { \"lat\": \"-37.3159\", \"lng\": \"81.1496\" } }, \"phone\": \"1-770-736-8031 x56442\", \"website\": \"hildegard.org\", \"company\": { \"name\": \"Romaguera-Crona\", \"catchPhrase\": \"Multi-layered client-server neural-net\", \"bs\": \"harness real-time e-markets\" } }";

    @Test
    public void testUserToJson() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Leanne Graham");
        user.setUsername("Bret");
        user.setEmail("Sincere@april.biz");

        User.Address address = new User.Address();
        address.setStreet("Kulas Light");
        address.setSuite("Apt. 556");
        address.setCity("Gwenborough");
        address.setZipcode("92998-3874");

        User.Address.Geo geo = new User.Address.Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");
        address.setGeo(geo);

        user.setAddress(address);
        user.setPhone("1-770-736-8031 x56442");
        user.setWebsite("hildegard.org");

        User.Company company = new User.Company();
        company.setName("Romaguera-Crona");
        company.setCatchPhrase("Multi-layered client-server neural-net");
        company.setBs("harness real-time e-markets");

        user.setCompany(company);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        User deserializedUser = mapper.readValue(json, User.class);

        assertEquals(user.getId(), deserializedUser.getId());
        assertEquals(user.getName(), deserializedUser.getName());
        assertEquals(user.getUsername(), deserializedUser.getUsername());
        assertEquals(user.getEmail(), deserializedUser.getEmail());
        assertEquals(user.getAddress().getStreet(), deserializedUser.getAddress().getStreet());
        assertEquals(user.getAddress().getSuite(), deserializedUser.getAddress().getSuite());
        assertEquals(user.getAddress().getCity(), deserializedUser.getAddress().getCity());
        assertEquals(user.getAddress().getZipcode(), deserializedUser.getAddress().getZipcode());
        assertEquals(user.getAddress().getGeo().getLat(), deserializedUser.getAddress().getGeo().getLat());
        assertEquals(user.getAddress().getGeo().getLng(), deserializedUser.getAddress().getGeo().getLng());
        assertEquals(user.getPhone(), deserializedUser.getPhone());
        assertEquals(user.getWebsite(), deserializedUser.getWebsite());
        assertEquals(user.getCompany().getName(), deserializedUser.getCompany().getName());
        assertEquals(user.getCompany().getCatchPhrase(), deserializedUser.getCompany().getCatchPhrase());
        assertEquals(user.getCompany().getBs(), deserializedUser.getCompany().getBs());
    }

    @Test
    public void testJsonToUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(JSON, User.class);

        assertEquals(1, user.getId());
        assertEquals("Leanne Graham", user.getName());
        assertEquals("Bret", user.getUsername());
        assertEquals("Sincere@april.biz", user.getEmail());
        assertEquals("Kulas Light", user.getAddress().getStreet());
        assertEquals("Apt. 556", user.getAddress().getSuite());
        assertEquals("Gwenborough", user.getAddress().getCity());
        assertEquals("92998-3874", user.getAddress().getZipcode());
        assertEquals("-37.3159", user.getAddress().getGeo().getLat());
        assertEquals("81.1496", user.getAddress().getGeo().getLng());
        assertEquals("1-770-736-8031 x56442", user.getPhone());
        assertEquals("hildegard.org", user.getWebsite());
        assertEquals("Romaguera-Crona", user.getCompany().getName());
        assertEquals("Multi-layered client-server neural-net", user.getCompany().getCatchPhrase());
        assertEquals("harness real-time e-markets", user.getCompany().getBs());
    }

}