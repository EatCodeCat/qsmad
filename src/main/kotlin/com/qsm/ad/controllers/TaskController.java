package com.qsm.ad.controllers;

import com.qsm.ad.constant.TaskStatus;
import com.qsm.ad.entitys.Task;
import com.qsm.ad.security.TokenAuthenticationService;
import com.qsm.ad.services.TaskService;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by TQ on 2017/8/15.
 */
@RestController
@RequestMapping("/api/task")
public class TaskController extends CrudController<Task> {
    @Autowired
    public void setRepository(TaskService taskService) {
        setCrudService(taskService);
    }


    @Override
    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public Task save(@RequestBody Task entity, HttpServletRequest request,
                     HttpServletResponse response) {
        Authentication u = TokenAuthenticationService.getAuthentication((request));
        entity.setStatus(TaskStatus.WAITING);
        entity.setUsername(u.getName());
        entity.setCreateTime(new Timestamp((new Date()).getTime()));
        return crudService.save(entity);


    }
}
