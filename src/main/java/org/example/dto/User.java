/**
 * Data Transfer Objects (DTOs) for various entities such as User, Post, Task, and Comment.
 */
package org.example.dto;

/**
 * Represents a User entity with various attributes such as id, name, username, email, address, phone, website, and company.
 */
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Represents an Address entity with various attributes such as street, suite, city, zipcode, and geo coordinates.
     */
    public static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        /**
         * Represents a Geo entity with various attributes such as latitude and longitude.
         */
        public static class Geo {
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            /**
             * Returns a string representation of geo coordinates.
             *
             * @return the string representation of geo coordinates.
             */
            @Override
            public String toString() {
                return "\n\t\tlat='" + lat + '\'' +
                        ",\n\t\tlng='" + lng + '\'';
            }
        }

        /**
         * Returns a string representation of the address.
         *
         * @return the string representation of the address.
         */
        @Override
        public String toString() {
            return "\n\tstreet='" + street + '\'' +
                    ",\n\tsuite='" + suite + '\'' +
                    ",\n\tcity='" + city + '\'' +
                    ",\n\tzipcode='" + zipcode + '\'' +
                    ",\n\tgeo=" + geo;
        }
    }

    /**
     * Represents a Company entity with various attributes such as name, catchPhrase and slogan.
     */
    public static class Company {
        private String name;
        private String catchPhrase;
        private String bs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

        /**
         * Returns a string representation of the company.
         *
         * @return the string representation of the company.
         */
        @Override
        public String toString() {
            return "\n\tname='" + name + '\'' +
                    ",\n\tcatchPhrase='" + catchPhrase + '\'' +
                    ",\n\tbs='" + bs + '\'';
        }
    }

    /**
     * Returns a string representation of the user.
     *
     * @return the string representation of the user.
     */
    @Override
    public String toString() {
        return "\nid=" + id +
                ",\nname='" + name + '\'' +
                ",\nusername='" + username + '\'' +
                ",\nemail='" + email + '\'' +
                ",\naddress=" + address +
                ",\nphone='" + phone + '\'' +
                ",\nwebsite='" + website + '\'' +
                ",\ncompany=" + company;
    }
}
