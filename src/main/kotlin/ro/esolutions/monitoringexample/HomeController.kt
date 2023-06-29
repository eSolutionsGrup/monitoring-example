package ro.esolutions.monitoringexample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HomeController {

    @GetMapping
    fun get(): Map<String, String> {
        return mapOf("status" to "success")
    }
}
