package com.qsm.ad.controllers

import com.qsm.ad.entitys.User
import com.qsm.ad.pageprocessor.getQsmLoginCodeResult
import com.qsm.ad.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.io.IOException
import javax.servlet.http.HttpServletResponse


/**
 * Created by think on 2017/7/30.
 */


@RestController
@RequestMapping("/api/user")
class UserController : CrudController<User>() {

    @Autowired
    fun setUserRepository(userService: UserService) {
        setCrudService(userService)
    }

    @RequestMapping(value = "/image", method = arrayOf(RequestMethod.GET))
    @Throws(IOException::class)
    fun image(response: HttpServletResponse) {

        getQsmLoginCodeResult(object : Pipeline {
            override fun process(var1: ResultItems, var2: Task) {
                response.contentType = "image/png"
                var stream = response.outputStream
                stream.write("2343242".toByteArray())
                stream.flush()
                stream.close()
            }
        })
        return
    }
}
