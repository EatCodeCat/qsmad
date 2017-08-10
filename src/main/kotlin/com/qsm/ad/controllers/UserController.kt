package com.qsm.ad.controllers

import com.qsm.ad.entitys.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by think on 2017/7/30.
 */


@RestController
@RequestMapping("/user")
class UserController : CrudController<User>() {

//    @Autowired
//    fun setUserRepository(userService: UserService) {
//        setCrudService(userService)
//    }
//
//    // 省略之前的内容...
//    @RequestMapping("/login")
//    fun login(): String {
//        return "login"
//    }

}
