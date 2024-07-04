package org.example.dto;

/**
 * Represents a Task entity with attributes such as userId, id, title, and completion status.
 */
public class Task {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    /**
     * Gets the user ID associated with this task.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the task ID.
     *
     * @return the task ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title of the task.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
}