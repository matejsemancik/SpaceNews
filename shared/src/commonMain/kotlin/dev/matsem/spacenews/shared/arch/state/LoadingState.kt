package dev.matsem.spacenews.shared.arch.state

/**
 * Represents the loading state of a data operation.
 *
 * @param T The type of data being loaded
 */
sealed interface LoadingState<out T : Any> {
    /**
     * Represents a successful data state.
     *
     * @param T The type of data
     * @property data The loaded data
     */
    data class Data<T : Any>(val data: T) : LoadingState<T>

    /**
     * Represents a loading state while data is being fetched.
     */
    data object Loading : LoadingState<Nothing>

    /**
     * Represents an error state when data loading fails.
     *
     * @property error User-facing error.
     */
    data class Error(val error: UiError) : LoadingState<Nothing>
}
