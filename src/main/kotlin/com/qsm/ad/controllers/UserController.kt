package com.qsm.ad.controllers

import com.qsm.ad.entitys.QsmUser
import com.qsm.ad.entitys.User
import com.qsm.ad.services.QsmUserService
import com.qsm.ad.services.UserService
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.protocol.HTTP
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession


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

    @Autowired
    lateinit var qsmUserService: QsmUserService

    fun qsmLoginCode(): String {
        val doc = Jsoup.connect("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/login.aspx").get()
        var ele = doc.select("#captcha_req_no").first()
        return ele.`val`()
    }

    fun qsmLoginForm(name: String, password: String, code: String, captcha_req_no: String): Map<String, String> {
        val doc = Jsoup.connect("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/login.aspx").get()
        var ele = doc.select("form input")
        val map = mutableMapOf<String, String>()
        for (e in ele) {
            map.put(e.attr("name"), e.`val`());
        }

        var img = doc.select("#qcaptcha_img").first()
        map.put("captcha_req_no", captcha_req_no)
        map.put("recaptcha_response_field", code)
        map.put("txtLoginID", name)
        map.put("txtLoginPwd", password)

        return map
    }

    @RequestMapping(value = "/qsmlogin/{code}", method = arrayOf(RequestMethod.POST), headers = arrayOf("Accept=application/json"))
    fun qsmlogin(@RequestBody user: QsmUser, @PathVariable code: String, session: HttpSession): Map<String, Any> {
        val httpClient = DefaultHttpClient()
        var httpPost = HttpPost("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/login.aspx")
        var form = qsmLoginForm(user.qsmUsername ?: "", user.qsmPassword ?: "", code, session.getAttribute("qcaptchr_req_no").toString())
        val params = arrayListOf(*form.map { BasicNameValuePair(it.key, it.value) }.toTypedArray())
        httpPost.entity = UrlEncodedFormEntity(params, HTTP.UTF_8)
        httpPost.addHeader("host", "qsm.qoo10.jp")
        httpPost.addHeader("connection", "keep-alive")
        httpPost.addHeader("cache-control", "max-age=0")
        httpPost.addHeader("pgrade-insecure-requests", "1")
        httpPost.addHeader("content-type", "application/json")
        httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
        httpPost.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        httpPost.addHeader("accept-encoding", "gzip, deflate, sdch")
        httpPost.addHeader("accept-language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,nb;q=0.2,sk;q=0.2,zh-TW;q=0.2")
        var httpResponse = httpClient.execute(httpPost)
        val cookie = httpClient.cookieStore
        val cust_no = cookie.cookies.filter { it.name == "qsm_cust_no" }
        val cookieStr = cookie.cookies.map { it.name + ":" + it.value }.joinToString(";")
        if (cust_no.isNotEmpty() && !cust_no.get(0).value.isNullOrEmpty()) {
            var u = QsmUser()
            var entity = qsmUserService.findByUsername(user.qsmUsername)
            if (entity != null) {
                u = entity
            }
            u.qsmUsername = user.qsmUsername
            u.qsmPassword = user.qsmPassword
            u.cookie = cookieStr
            u.status = 1
            u.loginTime = Timestamp(Date().time)

            if (entity.id ?: 0 > 0) {
                qsmUserService.update(entity)
            } else {
                qsmUserService.save(u)
            }
            return mapOf<String, Any>(("code" to 1))
        }
        val msg = EntityUtils.toString(httpResponse.entity, httpResponse.entity.contentEncoding.value)
        return mapOf<String, Any>(("code" to 0), ("msg" to msg))
    }

    @RequestMapping(value = "/image", method = arrayOf(RequestMethod.GET))
    @Throws(IOException::class)
    fun image(response: HttpServletResponse, session: HttpSession) {

        val httpClient = DefaultHttpClient()
        var qcaptchr_req_no = qsmLoginCode()
        var httpGet = HttpGet("https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/Common/Page/qcaptcha.ashx?qcaptchr_req_no=" + qcaptchr_req_no)
        session.setAttribute("qcaptchr_req_no", qcaptchr_req_no);
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
