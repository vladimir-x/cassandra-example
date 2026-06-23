package ru.dude.cass_example.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * Общий API
 *
 * @author Vladimir X
 * Date: 11.04.2026
 */
@RestController
internal class RestApi {

    @GetMapping("/ping")
    fun ping() = "pong"

}
