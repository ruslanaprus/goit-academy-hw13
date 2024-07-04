package org.example;

import org.example.dto.User;

public class MockUser {
    public static User createNewUser() {
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

    public static User createUpdatedUser() {

        User user = new User();
        user.setName("Alice");
        user.setUsername("WhiskerWonder");
        user.setEmail("alice@mail.com");

        User.Address address = new User.Address();
        address.setStreet("Pawsome box");
        address.setSuite("Apt. 8");
        address.setCity("Purrito");
        address.setZipcode("221-222");

        User.Address.Geo geo = new User.Address.Geo();
        geo.setLat("24.2133");
        geo.setLng("75.3645");
        address.setGeo(geo);

        user.setAddress(address);
        user.setPhone("987-654-321 meow");
        user.setWebsite("catpaw.com");

        User.Company company = new User.Company();
        company.setName("Purrlock-Holmes");
        company.setCatchPhrase("Elementary, my dear whiskers!");
        company.setBs("Solving mysteries, one paw at a time");

        user.setCompany(company);

        return user;
    }
}
