package org.example.dto;

/**
 * Represents a Comment entity with attributes such as postId, id, name, email, and body.
 */
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    /**
     * Gets the post ID associated with this comment.
     *
     * @return the post ID
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Gets the comment ID.
     *
     * @return the comment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the comment author.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the email of the comment author.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the body content of the comment.
     *
     * @return the body content
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns a string representation of the comment.
     *
     * @return the string representation of the comment
     */
    @Override
    public String toString() {
        return "\nComment:\n" +
                '\'' + body + '\'';
    }
}