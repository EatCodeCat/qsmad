package com.qsm.ad.util

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by TQ on 2017/8/11.
 */
object JSONResult {
    fun fillResultString(status: Int = 200, message: String, result: Any): String {

        val mapper = ObjectMapper() //转换器
        var m = mapOf<String, Any>(("stata" to status), ("message" to message), ("result" to result))
        var json = mapper.writeValueAsString(m)
        return json
    }
}
fun fillResultString(status: Int = 200, message: String, result: Any): String {

    return JSONResult.fillResultString(status, message, result)
}
