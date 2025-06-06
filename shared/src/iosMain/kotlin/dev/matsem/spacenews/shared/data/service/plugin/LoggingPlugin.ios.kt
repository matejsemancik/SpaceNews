package dev.matsem.spacenews.shared.data.service.plugin

import io.ktor.client.plugins.logging.LoggingFormat

internal actual fun getLoggingFormat() = LoggingFormat.Default
