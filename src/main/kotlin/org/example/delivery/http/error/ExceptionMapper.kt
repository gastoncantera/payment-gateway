package org.example.delivery.http.error

import io.ktor.http.*
import io.ktor.server.plugins.BadRequestException
import kotlinx.serialization.SerializationException
import org.example.core.domain.exception.BusinessException

class ExceptionMapper {

    fun toHttpCode(throwable: Throwable): HttpStatusCode {
        return when (throwable) {
            is BadRequestException -> HttpStatusCode.BadRequest
            is BusinessException -> HttpStatusCode.BadRequest
            else -> HttpStatusCode.InternalServerError
        }
    }

    fun toDomainCode(error: Throwable) = when (error) {
        is SerializationException -> "2000"
        is BadRequestException -> "4000"
        is BusinessException -> "6000"
        else -> "UNDEFINED"
    }
}