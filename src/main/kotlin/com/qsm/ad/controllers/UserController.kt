package com.qsm.ad.controllers

import com.qsm.ad.entitys.User
import com.qsm.ad.pageprocessor.getQsmLoginCodeResult
import com.qsm.ad.services.UserService
import org.apache.http.client.methods.HttpGet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import sun.net.www.http.HttpClient
import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.cxf.jaxrs.utils.InjectionUtils.getEntity
import org.apache.http.NameValuePair
import org.apache.http.client.methods.HttpPost
import org.jsoup.Jsoup
import org.apache.http.message.BasicNameValuePair
import java.util.ArrayList
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.protocol.HTTP


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

    fun qsmLoginCode(): String {
        val doc = Jsoup.connect("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/login.aspx").get()
        var ele = doc.select("#qcaptcha_img").first()
        return ele.attr("src")
    }

    fun qsmLoginForm(name: String, password: String): Map<String, String> {
        val doc = Jsoup.connect("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/login.aspx").get()
        var ele = doc.select("form input")
        val map = mutableMapOf<String, String>()
        for (e in ele) {

            map.put(e.attr("name"), e.`val`());
        }
        map.put("txtLoginID", name)
        map.put("txtLoginPwd", password)
        return map
    }

    @RequestMapping(value = "/qsmlogin", method = arrayOf(RequestMethod.POST))
    fun qsmlogin(name: String, password: String, code: String): String {
        val httpClient = DefaultHttpClient()
        var httpPost = HttpPost("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/login.aspx")
        var form = qsmLoginForm(name, password)
        val params = arrayListOf(*form.map { BasicNameValuePair(it.key, it.value) }.toTypedArray())
        httpPost.entity = UrlEncodedFormEntity(params, HTTP.UTF_8)
        var httpResponse = httpClient.execute(httpPost)
        val cookie = httpClient.cookieStore
        return cookie.cookies.map { it.name + ":" + it.value }.joinToString(";")
    }

    @RequestMapping(value = "/image", method = arrayOf(RequestMethod.GET))
    @Throws(IOException::class)
    fun image(response: HttpServletResponse) {
        val httpClient = DefaultHttpClient()
        var url = qsmLoginCode()
        var httpGet = HttpGet(url)
        var httpResponse = httpClient.execute(httpGet)
        val entity = httpResponse.entity
        response.contentType = "image/png"
        var stream = response.outputStream
        stream.write(entity.content.readBytes())
        stream.flush()
        stream.close()
        httpClient.close()

        return
    }
}
