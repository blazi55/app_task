package com.application.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/createJob")
    public void executeJob(@RequestBody Task task) {
        this.taskService.runThread(task);
    }

    @GetMapping("/findResult")
    public List<String> getResult() {
        return this.taskService.getResults();
    }

    @GetMapping("/findRunningJob")
    public List<String> getCurrent() {
        return this.taskService.getCurrent();
    }

}
