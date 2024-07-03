package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dto.Task;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    private final HttpUtil httpUtil;

    public TaskService(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    public List<String> getOpenTasksByUserId(URI uri) {
        List<Task> tasks = httpUtil.sendRequest(uri, HttpRequest.newBuilder().GET(), new TypeReference<List<Task>>() {});
        if (tasks != null) {
            return tasks.stream()
                    .filter(task -> !task.isCompleted())
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
        }
        return null;
    }
}