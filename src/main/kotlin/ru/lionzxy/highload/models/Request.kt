package ru.lionzxy.highload.models

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URLDecoder
import java.net.URLEncoder

class Request {
    val requestMethod: RequestMethod
    val path: String
    var isIndex: Boolean = false

    constructor(inputStream: InputStream) {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val request = reader.readLine().split(" ")
        requestMethod = RequestMethod.valueOfString(request[0])
        var pathStr = if (request.size > 1) request[1].substringBefore("?") else ""
        pathStr = URLDecoder.decode(pathStr, "UTF-8")
        if (pathStr.endsWith("/") || pathStr.isEmpty()) {
            pathStr += "index.html"
            isIndex = true
        }
        path = pathStr
    }
}