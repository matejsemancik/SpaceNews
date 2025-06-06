package dev.matsem.spacenews.shared.injection

import dev.matsem.spacenews.shared.data.service.plugin.BaseHttpClientPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient

internal actual fun getNativeHttpClient(plugins: List<BaseHttpClientPlugin>): HttpClient = HttpClient(OkHttp) {
    plugins.forEach { it.install(this) }
    engine {
        preconfigured = OkHttpClient.Builder().build()
    }
}
