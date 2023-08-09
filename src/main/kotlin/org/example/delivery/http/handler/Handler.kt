package org.example.delivery.http.handler

import io.ktor.server.application.Application

interface Handler {
    fun routing(a: Application)
}