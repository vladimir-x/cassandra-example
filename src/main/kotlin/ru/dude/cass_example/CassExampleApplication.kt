package ru.dude.cass_example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CassExampleApplication

fun main(args: Array<String>) {
	runApplication<CassExampleApplication>(*args)
}
