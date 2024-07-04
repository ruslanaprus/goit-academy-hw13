/**
 * This package contains services for communicating with the <a href="https://jsonplaceholder.typicode.com/">JSONPlaceholder API</a>.
 * <p>
 * The services within this package facilitate HTTP communication to interact with various endpoints provided
 * by the JSONPlaceholder API. These services use the <b>HttpUtil</b> class to send HTTP requests and process responses,
 * enabling operations such as fetching, creating, updating, and deleting data related to users, posts, comments,
 * and tasks.
 * </p>
 *
 * <h2>Classes:</h2>
 * <ul>
 *   <li><b>HttpUtil</b>: Utility class for sending HTTP requests and processing JSON responses using HttpClient and Jackson ObjectMapper.</li>
 *   <li><b>UserService</b>: Service class for handling user-related operations, including fetching, creating, updating, and deleting users.</li>
 *   <li><b>TaskService</b>: Service class for managing task-related operations, specifically fetching open tasks for a user.</li>
 *   <li><b>PostService</b>: Service class for handling post-related operations, including fetching posts and comments, and writing comments to a file.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <ul>
 *   <li>These services can be used to interact with the JSONPlaceholder API, providing a convenient and reliable way
 *   to prototype and test applications that require HTTP communication with a remote server.</li>
 * </ul>
 */
package org.example.placeholderservice;