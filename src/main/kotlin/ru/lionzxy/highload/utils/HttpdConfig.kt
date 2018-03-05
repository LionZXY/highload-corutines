package ru.lionzxy.highload.utils

import java.io.File
import java.nio.file.Files

class HttpdConfig private constructor(file: File) {
    private var configVars: HashMap<String, String> = HashMap()

    companion object {
        fun readConfig(file: File): HttpdConfig {
            return HttpdConfig(file)
        }

        fun readDefaultConfig(): HttpdConfig {
            return HttpdConfig(File("/etc/httpd.conf"))
        }
    }

    init {
        Files.readAllLines(file.toPath())
                .map { it.split(" ") }
                .forEach { configVars[it[0]] = it[1] }
    }


    public operator fun get(key: String): String? {
        return configVars[key]
    }
}