package org.example.delivery.http.handler.administrative

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.delivery.http.handler.Handler

class StatusHandler : Handler {
    override fun routing(a: Application) {
        a.routing {
            get("/status") {
                call.respondText("Ok", ContentType.Text.Plain)
            }
        }
    }
}