package dev.matsem.spacenews.shared.arch.state

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.matsem.spacenews.resources.MR
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.io.IOException

/**
 * Represents a user-friendly error that can be displayed in the UI.
 *
 * @property title A localized string description for the error title
 * @property message A localized string description for the error message
 */
data class UiError(
    val title: StringDesc,
    val message: StringDesc,
) {
    companion object {
        fun generic() = UiError(
            title = MR.strings.error_generic_title.desc(),
            message = MR.strings.error_generic_message.desc(),
        )

        fun connection() = UiError(
            title = MR.strings.error_connection_title.desc(),
            message = MR.strings.error_connection_message.desc(),
        )
    }
}

/**
 * Converts a [Throwable] to a user-friendly [UiError].
 *
 * This extension function maps different types of exceptions to appropriate user-friendly error messages.
 * Currently handles:
 * - [IOException] and [UnresolvedAddressException] as connection errors
 * - All other exceptions as generic errors
 *
 * @return A [UiError] with localized title and message appropriate for the exception type
 */
internal fun Throwable.toUiError() = when (this) {
    is IOException, is UnresolvedAddressException -> UiError.connection()
    else -> UiError.generic()
}
