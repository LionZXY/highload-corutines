package ru.lionzxy.highload.utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("EEE, dd MMM, yyyy HH:mm:ss z", Locale.ENGLISH).apply { timeZone = TimeZone.getTimeZone("GMT") }

fun File.getMimeType(): String {
    return when (this.name.substringAfterLast('.')) {
        "txt" -> "text/plain"
        "html" -> "text/html"
        "css" -> "text/css"
        "js" -> "text/javascript"
        "jpg" -> "image/jpeg"
        "jpeg" -> "image/jpeg"
        "gif" -> "image/gif"
        "png" -> "image/png"
        "swf" -> "application/x-shockwave-flash"
        else -> "application/octet-stream"
    }
}

fun Date.toFormattedString(): String {
   return dateFormat.format(this)
}