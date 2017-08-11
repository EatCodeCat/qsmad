package com.qsm.ad.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.qsm.ad.entitys.User
import com.qsm.ad.util.JSONResult
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by TQ on 2017/8/11.
 */
class JWTLoginFilter(url: String, authManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(
            req: HttpServletRequest, res: HttpServletResponse): Authentication {

        // JSON反序列化成 AccountCredentials
        val creds = ObjectMapper().readValue(req.inputStream, User::class.java)

        // 返回一个验证令牌
        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        creds.username,
                        creds.password
                )
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            req: HttpServletRequest,
            res: HttpServletResponse, chain: FilterChain?,
            auth: Authentication) {

        TokenAuthenticationService.addAuthentication(res, auth.name)
    }


    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_OK
        response.outputStream.println(JSONResult.fillResultString(500, "Internal Server Error!!!", ""))
    }
}