/**
 * This package contains Data Transfer Objects (DTOs) for the application.
 * <p>
 * The application leverages these DTOs in conjunction with utility and service classes
 * such as HttpUtil, UserService, PostService, and TaskService to facilitate HTTP communication
 * and data manipulation. These DTOs represent the core entities that the application interacts with,
 * enabling a clear and structured way to handle data exchanged with a remote server.
 * </p>
 *
 * <h2>Classes:</h2>
 * <ul>
 *   <li><b>User</b>: Represents a user with attributes such as ID, name, username, email, address, phone, website, and company.</li>
 *   <li><b>Post</b>: Represents a blog post with attributes such as userId, id, title, and body.</li>
 *   <li><b>Task</b>: Represents a task with attributes such as userId, id, title, and completed status.</li>
 *   <li><b>Comment</b>: Represents a comment with attributes such as postId, id, name, email, and body.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <ul>
 *     <li>The <b>UserService</b> class utilizes the User DTO to manage user-related operations, including fetching, creating, updating, and deleting users.</li>
 *     <li>The <b>PostService</b> class utilizes the Post and Comment DTOs to manage blog post-related operations, including fetching posts and comments, and writing comments to a file.</li>
 *     <li>The <b>TaskService</b> class utilizes the Task DTO to manage task-related operations, including fetching open tasks for a user.</li>
 * </ul>
 *
 * <p>
 * These DTOs are essential for the application's data flow, providing a structured way to represent and handle data obtained from or sent to the server via HTTP requests.
 * </p>
 */
package org.example.dto;