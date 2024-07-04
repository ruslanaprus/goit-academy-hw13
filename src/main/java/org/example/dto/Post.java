package org.example.dto;

/**
 * Represents a Post entity with attributes such as userId, id, title, and body.
 */
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    /**
     * Gets the user ID associated with this post.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the post ID.
     *
     * @return the post ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title of the post.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the body content of the post.
     *
     * @return the body content
     */
    public String getBody() {
        return body;
    }
}