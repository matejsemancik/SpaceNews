package dev.matsem.spacenews.shared.injection

import de.jensklingenberg.ktorfit.Ktorfit
import dev.matsem.spacenews.shared.data.service.NewsApi
import dev.matsem.spacenews.shared.data.service.NewsApiManager
import dev.matsem.spacenews.shared.data.service.NewsApiManagerImpl
import dev.matsem.spacenews.shared.data.service.createNewsApi
import dev.matsem.spacenews.shared.data.service.plugin.BaseHttpClientPlugin
import dev.matsem.spacenews.shared.data.service.plugin.ContentNegotiationPlugin
import dev.matsem.spacenews.shared.data.service.plugin.LoggingPlugin
import dev.matsem.spacenews.shared.tooling.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect fun getNativeHttpClient(plugins: List<BaseHttpClientPlugin>): HttpClient

internal fun networkModule() = module {
    includes(httpClientPluginsModule(), apiClientModule())
}

private fun httpClientPluginsModule() = module {
    factory { ContentNegotiationPlugin() }
    factory { (logLevel: LogLevel) -> LoggingPlugin(logLevel) }
}

private fun apiClientModule() = module {
    single<HttpClient> {
        val plugins = listOf(
            get<LoggingPlugin>(parameters = { parametersOf(LogLevel.INFO) }),
            get<ContentNegotiationPlugin>(),
        )

        getNativeHttpClient(plugins)
    }

    single<Ktorfit> {
        Ktorfit.Builder()
            .baseUrl(Constants.SpaceflightNewsApiUrl)
            .httpClient(get<HttpClient>())
            .build()
    }

    single<NewsApi> {
        get<Ktorfit>().createNewsApi()
    }

    singleOf(::NewsApiManagerImpl) bind NewsApiManager::class
}
