package dev.matsem.spacenews.shared.data.repo.model

/**
 * Represents the state of a paginated list of data.
 *
 * This class is typically used in conjunction with a ViewModel or a similar component
 * to manage the state of a list that loads its data in chunks or pages.
 *
 * @param T The type of data items in the list. Must be non-nullable.
 * @property nextPage The pointer to fetch the next page of data. `null` if there is no next page or if the initial page hasn't loaded yet.
 * @property hasReachedEnd `true` if all pages have been loaded, `false` otherwise.
 * @property data The currently loaded list of data items.
 * @property error An optional [Throwable] representing an error that occurred during data loading. `null` if no error has occurred.
 * @property isLoading `true` if data is currently being loaded, `false` otherwise.
 */
data class PagedListState<T : Any>(
    val nextPage: NextPagePointer?,
    val hasReachedEnd: Boolean,
    val data: List<T>,
    val error: Throwable?,
    val isLoading: Boolean,
)

/**
 * Represents the pointer to the next page of data in a paginated list.
 * This sealed interface defines the different ways to specify the next page to be loaded.
 *
 * It can be either:
 * - [LimitOffset]: Uses a limit and offset to define the next page.
 * - [Url]: Uses a specific URL to fetch the next page.
 */
sealed interface NextPagePointer {
    data class LimitOffset(val limit: Int, val offset: Int) : NextPagePointer
    data class Url(val nextPageUrl: String) : NextPagePointer
}

/**
 * Creates an initial [PagedListState] for initial loading.
 *
 * @param initialPageSize initial page size.
 */
inline fun <reified T : Any> initialPagedListState(initialPageSize: Int): PagedListState<T> = PagedListState(
    nextPage = NextPagePointer.LimitOffset(limit = initialPageSize, offset = 0),
    hasReachedEnd = false,
    data = emptyList(),
    error = null,
    isLoading = false,
)

/**
 * Returns new [PagedListState] with loading flag set to `true`.
 *
 * @return [PagedListState] representing `loading` state.
 */
inline fun <reified T : Any> PagedListState<T>.loading(): PagedListState<T> = copy(
    isLoading = true,
)

/**
 * Returns new [PagedListState] with provided [error], resets `isLoading` flag.
 *
 * @return [PagedListState] representing `error` state.
 */
inline fun <reified T : Any> PagedListState<T>.error(error: Throwable): PagedListState<T> = copy(
    isLoading = false,
    error = error,
)
