# JSONPlaceholder Client Toolkit

## Overview

This is the solution for the GoIT Academy Module 13 [Web. Protocols] homework.

The JSONPlaceholder Client Toolkit is a Java application that demonstrates how to interact with the JSONPlaceholder API. This toolkit provides functionality to manage users, posts, comments, and tasks through various service classes. It leverages HTTP communication to fetch, create, update, and delete data using the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/).

## Packages

### `org.example`

This package serves as the entry point of the application and demonstrates the usage of various services to interact with the JSONPlaceholder API. The `Main` class coordinates the interaction between the data transfer objects (DTOs) and the service classes.

### `org.example.placeholderservice`

This package contains utility and service classes to facilitate HTTP communication and data manipulation. It includes the following classes:

- **HttpUtil**: Utility class for sending HTTP requests and processing JSON responses using `HttpClient` and `Jackson ObjectMapper`.
- **UserService**: Service class for handling user-related operations, such as creating, updating, deleting, and fetching users.
- **PostService**: Service class for managing blog post-related operations, including fetching posts and comments, and writing comments to a file.
- **TaskService**: Service class for managing task-related operations, such as fetching open tasks for a user.

### `org.example.dto`

This package contains Data Transfer Objects (DTOs) used in the application. These DTOs represent the core entities that the application interacts with, enabling a structured way to handle data exchanged with a remote server. The classes include:

- **User**: Represents a user with attributes such as ID, name, username, email, address, phone, website, and company.
- **Post**: Represents a blog post with attributes such as userId, id, title, and body.
- **Task**: Represents a task with attributes such as userId, id, title, and completed status.
- **Comment**: Represents a comment with attributes such as postId, id, name, email, and body.

## Features

- **User Management**: Create, update, delete, and fetch user information.
- **Post and Comment Management**: Fetch posts and comments, and write comments to a file.
- **Task Management**: Fetch open tasks for a user.

## Examples of Usage

To run the application, execute the `Main` class that already has a mock user `bob` pre-created to demonstrate the work of the client application in certain cases. All requests will be sent to the base URL `https://jsonplaceholder.typicode.com` with specific paths added to fetch the required data for each task.

The application can perform the following tasks:

1. **Create a User**: Creates a new user with predefined data.
   ```java
   User createdUser = userService.createUser(URI.create(BASE_URL + "/users"), bob);
   ```
2. **Update a User**: Updates an existing user with new data.

    ```Java
    User updatedUser = userService.updateUser(URI.create(BASE_URL + "/users/" + userIdToUpdate), bob);
    ```

3. **Delete a User**: Deletes a user by ID.

    ```Java
    int statusCode = userService.deleteUser(URI.create(BASE_URL + "/users/" + userIdToDelete));
    ```

4. **Fetch Users**: Fetches a list of all users.

    ```Java
    List<User> users = userService.getUsers(URI.create(BASE_URL + "/users"));
    ```

5. **Fetch User by ID**: Fetches a user by their ID.

    ```Java
    User userById = userService.getUser(URI.create(BASE_URL + "/users/" + userId));
    ```

6. **Fetch User by Username**: Fetches all users with a provided username.

    ```Java
    List<User> usersByUserName = userService.getUsers(URI.create(BASE_URL + "/users?username=" + userName));
    ```
   
7. **Write Comments to a File**: Writes comments of the last post of a user to a file with the name `user-X-post-Y-comments.json`, where `X` is the user ID, `Y` is the post number. Information will be written in Json format. Fi

    ```Java
    postService.writeCommentsOfLastPostToFile(userIdComments, DIRECTORY_PATH);
    ```
   
8. **Fetch Open Tasks**: Fetches uncompleted tasks for a user by their ID.

    ```Java
    List<String> tasks = taskService.getOpenTasksByUserId(URI.create(BASE_URL + "/users/" + userIdTasks + "/todos"));
    ```

## Project Structure

This structure outlines the organization of the project's source code, detailing the main packages and classes involved.

```css
src/
├── main/
│   ├── java/
│   │   ├── org/
│   │   │   ├── example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Post.java
│   │   │   │   │   ├── Task.java
│   │   │   │   │   ├── Comment.java
│   │   │   │   ├── placeholderservice/
│   │   │   │   │   ├── HttpUtil.java
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   ├── PostService.java
│   │   │   │   │   ├── TaskService.java
│   ├── resources/
│       ├── ... (resource files)
```
