package ro.esolutions.monitoringexample

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/db")
class DatabaseLoadController(private val jdbcTemplate: JdbcTemplate) {

    @GetMapping
    fun longRunning(): Map<String, String> {
        jdbcTemplate.query("select count(1) from pg_sleep(60)") {}

        return mapOf("success" to "true")
    }
}
