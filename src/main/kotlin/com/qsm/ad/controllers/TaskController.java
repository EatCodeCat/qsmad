package com.qsm.ad.controllers;

import com.qsm.ad.entitys.Task;
import com.qsm.ad.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by TQ on 2017/8/15.
 */
@RestController
@RequestMapping("/task")
public class TaskController extends CrudController<Task> {
    @Autowired
    public void setRepository(TaskService taskService) {
        setCrudService(taskService);
    }
}
