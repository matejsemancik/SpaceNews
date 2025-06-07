package dev.matsem.spacenews.shared.feature.home.model

import dev.matsem.spacenews.shared.arch.errors.UiError
import dev.matsem.spacenews.shared.arch.errors.toUiError

interface LoadingFooterItem {
    data object Loading : LoadingFooterItem
    data class Error(val error: UiError) : LoadingFooterItem {
        constructor(throwable: Throwable) : this(throwable.toUiError())
    }
}
