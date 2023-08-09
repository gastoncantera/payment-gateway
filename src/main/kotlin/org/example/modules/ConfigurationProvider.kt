package org.example.modules

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.yaml.YamlParser
import org.example.delivery.http.HttpApiServer

object ConfigurationProvider {

    private const val CONFIG_FILE_NAME = "/config.yaml"

    data class Configuration constructor(
        val app: HttpApiServer.AppConfig,
        val http: GatewaysProvider.HttpEngineConfiguration
    )

    val config: Configuration = ConfigLoader.Builder()
        .addFileExtensionMapping("yaml", YamlParser())
        .build()
        .loadConfigOrThrow(CONFIG_FILE_NAME)
}