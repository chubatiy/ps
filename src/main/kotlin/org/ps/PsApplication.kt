package org.ps

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PsApplication

fun main(args: Array<String>) {
    runApplication<PsApplication>(*args)
}
