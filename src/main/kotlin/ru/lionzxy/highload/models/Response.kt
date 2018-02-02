package ru.lionzxy.highload.models

import ru.lionzxy.highload.exceptions.ServerException
import ru.lionzxy.highload.utils.getMimeType
import ru.lionzxy.highload.utils.toFormattedString
import java.io.File
import java.util.*

open class Response(val code: Int, val status: String = "UNKNOWN", val file: File? = null) {

    constructor(exception: ServerException) : this(exception.code, exception.description)

    fun getHeaders(): String {
        var defaultHeader = "HTTP/1.1 $code $status\r\n" +
                "Date: ${Date().toFormattedString()}\r\n" +
                "Server: highload-corutines\r\n" +
                "Connection: Close\r\n"

        if (file != null && file.exists()) {
            defaultHeader += "Content-Length: ${file.length()}\r\n" +
                    "Content-Type: ${file.getMimeType()}\r\n\r\n"
        }
        return defaultHeader
    }

    override fun toString(): String {
        return getHeaders() + file?.absoluteFile
    }
}