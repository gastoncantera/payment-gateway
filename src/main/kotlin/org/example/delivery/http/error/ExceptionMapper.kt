package org.example.delivery.http.error

import io.ktor.http.*
import kotlinx.serialization.SerializationException
import org.example.core.domain.exception.BusinessException

class ExceptionMapper {

    fun toHttpCode(throwable: Throwable): HttpStatusCode {
        return when (throwable) {
            is BusinessException -> HttpStatusCode.BadRequest
            else -> HttpStatusCode.InternalServerError
        }
    }

    fun toDomainCode(error: Throwable) = when (error) {
        is SerializationException -> "2000"
        else -> "UNDEFINED"
    }
}