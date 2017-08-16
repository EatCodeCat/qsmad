package com.qsm.ad.controllers;

import com.qsm.ad.services.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by think on 2017/7/30.
 */
@RestController
public abstract class CrudController<T> {

    CrudService<T> crudService;

    public void setCrudService(CrudService<T> crudService) {
        this.crudService = crudService;
    }

    @RequestMapping(value = "/listByPage", method = RequestMethod.GET, headers = "Accept=application/json")
    public Page<T> listByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size, @RequestParam Map<String, Object> criteriaMap) {
        criteriaMap.remove("page");
        criteriaMap.remove("size");
        return crudService.findAll(new PageRequest(page, size), criteriaMap);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public T entityById(@PathVariable int id) {
        return crudService.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public T save(@RequestBody T entity,HttpServletRequest request,
                  HttpServletResponse response) {
        return crudService.save(entity);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, headers = "Accept=application/json")
    public T update(@RequestBody T entity) {
        return crudService.update(entity);

    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void delete(@PathVariable("id") int id) {
        crudService.delete(id);
    }
}
