/**
 * This package contains Data Transfer Objects (DTOs) for the application.
 *
 * The application leverages these DTOs in conjunction with utility and service classes
 * such as HttpUtil, UserService, PostService, and TaskService to facilitate HTTP communication
 * and data manipulation. These DTOs represent the core entities that the application interacts with,
 * enabling a clear and structured way to handle data exchanged with a remote server.
 *
 * Classes:
 *
 * - User: Represents a user with attributes such as ID, name, username, email, address, phone, website, and company.
 * - Post: Represents a blog post with attributes such as userId, id, title, and body.
 * - Task: Represents a task with attributes such as userId, id, title, and completed status.
 * - Comment: Represents a comment with attributes such as postId, id, name, email, and body.
 *
 * Usage:
 *
 * - The UserService class utilizes the User DTO to manage user-related operations, including fetching, creating, updating, and deleting users.
 * - The PostService class utilizes the Post and Comment DTOs to manage blog post-related operations, including fetching posts and comments, and writing comments to a file.
 * - The TaskService class utilizes the Task DTO to manage task-related operations, including fetching open tasks for a user.
 *
 * These DTOs are essential for the application's data flow, providing a structured way to represent and handle data obtained from or sent to the server via HTTP requests.
 */
package org.example.dto;