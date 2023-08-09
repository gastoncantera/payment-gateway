package org.example

import org.example.modules.Banner
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main() {
    val logger: Logger = LoggerFactory.getLogger("Main")

    println(Banner.TEXT)

    logger.info("Starting main application ...")
}