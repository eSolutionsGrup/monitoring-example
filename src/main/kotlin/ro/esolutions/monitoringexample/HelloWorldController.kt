package ro.esolutions.monitoringexample

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/hello")
class HelloWorldController {

    @GetMapping
    fun get(): ResponseEntity<Map<String, String>> {
        return if (Random.nextBoolean()) {
            ok(mapOf("greeting" to "successful"))
        } else {
            badRequest().body(mapOf("greeting" to "failed"))
        }
    }
}
