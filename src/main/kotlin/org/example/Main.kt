package org.example

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.example.modules.Banner
import org.example.modules.HttpDeliveryProvider.apiServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main() {
    val logger: Logger = LoggerFactory.getLogger("Main")

    println(Banner.TEXT)

    logger.info("Starting main application ...")

    registerShutdownHook()
    apiServer.start()
}

private fun registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(ShutdownHook)
}

object ShutdownHook : Thread() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    override fun run() {
        logger.info("Termination signal was received")
        runBlocking {
            logger.info("Stopping consumers...")
            apiServer.stop()
            LogManager.shutdown()
        }
    }
}