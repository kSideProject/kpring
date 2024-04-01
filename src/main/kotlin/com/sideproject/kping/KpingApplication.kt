package com.sideproject.kping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KpingApplication

fun main(args: Array<String>) {
	runApplication<KpingApplication>(*args)
}
