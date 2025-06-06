package dev.matsem.spacenews.shared.injection

import dev.matsem.spacenews.shared.data.service.plugin.BaseHttpClientPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

internal actual fun getNativeHttpClient(plugins: List<BaseHttpClientPlugin>): HttpClient = HttpClient(Darwin) {
    plugins.forEach { it.install(this) }
}
