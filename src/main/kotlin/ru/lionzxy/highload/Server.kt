package ru.lionzxy.highload

import kotlinx.coroutines.experimental.launch
import ru.lionzxy.highload.exceptions.Forbidden
import ru.lionzxy.highload.exceptions.MethodNotAllowed
import ru.lionzxy.highload.exceptions.NotFound
import ru.lionzxy.highload.exceptions.ServerException
import ru.lionzxy.highload.models.Request
import ru.lionzxy.highload.models.RequestMethod
import ru.lionzxy.highload.models.Response
import java.io.BufferedOutputStream
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset

var rootFile = File("./")

fun main(args: Array<String>) {
    var port = 5000
    val iterrator = args.iterator()
    while (iterrator.hasNext()) {
        when (iterrator.next()) {
            "-p" -> port = iterrator.next().toInt()
            "-r" -> rootFile = File(iterrator.next())
        }
    }
    println("Start server on port: $port and static folder: ${rootFile.absoluteFile}")
    val server = ServerSocket(port)
    while (true) {
        val socket = server.accept()
        launch {
            try {
                processRequest(socket)
            } finally {
                socket.close()
            }
            socket.close()
        }
    }
}

fun processRequest(socket: Socket) {
    val request = Request(socket.getInputStream())

    val response = try {
        getResponse(request)
    } catch (serverException: ServerException) {
        Response(serverException)
    }

    BufferedOutputStream(socket.getOutputStream()).use { outputStream ->
        outputStream.write(response.getHeaders().toByteArray(Charset.forName("UTF-8")))
        if (request.requestMethod != RequestMethod.HEAD) {
            response.file?.inputStream().use { it?.copyTo(outputStream) }
        }
    }
}

fun getResponse(request: Request): Response {
    if (request.requestMethod == RequestMethod.UNKNOWN) {
        throw MethodNotAllowed()
    }

    if (request.path.contains("../")) {
        throw Forbidden()
    }

    val file = File(rootFile, request.path)
    println(file.absoluteFile)
    if (!file.exists()) {
        if (request.isIndex) {
            throw Forbidden()
        }
        throw NotFound()
    }

    return Response(200, "OK", file)
}