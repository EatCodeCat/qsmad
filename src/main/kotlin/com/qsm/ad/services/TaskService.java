package com.qsm.ad.services;

import com.qsm.ad.entitys.Task;
import com.qsm.ad.repositroys.TaskRepository;
import com.qsm.ad.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by TQ on 2017/8/15.
 */
@Service
public class TaskService extends CrudService<Task> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    public  void setRepository( TaskRepository repository){
        super.setRepository(repository);
    }

    @Override
    public Page<Task> findAll(Pageable pageable, Map<String, Object> criterial){

        Authentication u = TokenAuthenticationService.getAuthentication((request));
        criterial.put("username", u.getName());
        return super.findAll(pageable, criterial);

    }
}
