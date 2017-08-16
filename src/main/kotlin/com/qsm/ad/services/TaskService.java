package com.qsm.ad.services;

import com.qsm.ad.entitys.Task;
import com.qsm.ad.repositroys.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by TQ on 2017/8/15.
 */
@Service
public class TaskService extends CrudService<Task> {

    @Autowired
    TaskRepository repository;
}
