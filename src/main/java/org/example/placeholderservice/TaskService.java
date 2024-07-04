package org.example.placeholderservice;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dto.Task;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides services for managing task-related operations, specifically fetching open tasks for a user.
 */
public class TaskService {

    private final HttpUtil httpUtil;

    /**
     * Constructs an instance of TaskService with the provided HttpUtil.
     *
     * @param httpUtil the HttpUtil to use for sending requests
     */
    public TaskService(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    /**
     * Fetches a list of open tasks for a specific user by user ID.
     *
     * @param uri the URI of the tasks endpoint
     * @return the list of open task titles, or an empty list if no open tasks are found
     */
    public List<String> getOpenTasksByUserId(URI uri) {
        List<Task> tasks = httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Task>>() {});
        if (tasks != null) {
            return tasks.stream()
                    .filter(task -> !task.isCompleted())
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}