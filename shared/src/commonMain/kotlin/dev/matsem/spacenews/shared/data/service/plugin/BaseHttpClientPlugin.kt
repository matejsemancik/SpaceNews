package dev.matsem.spacenews.shared.data.service.plugin

import io.ktor.client.HttpClientConfig

interface BaseHttpClientPlugin {

    fun install(receiver: HttpClientConfig<*>)
}
