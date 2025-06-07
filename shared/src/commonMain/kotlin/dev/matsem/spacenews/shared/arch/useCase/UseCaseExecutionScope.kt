package dev.matsem.spacenews.shared.arch.useCase

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Interface that provides execution utilities for [UseCase] and [FlowUseCase] instances.
 * 
 * This interface should be implemented by classes that need to execute use cases,
 * such as ViewModels or Presenters. It provides a structured way to handle
 * use case execution with proper lifecycle management, error handling, and cancellation.
 * 
 * ## Key Features:
 * - Automatic cancellation of previous executions
 * - Structured error handling with callback support
 * - Main dispatcher switching for UI updates
 * - Lifecycle-aware execution (onStart, onSuccess, onError, etc.)
 * 
 * ## Implementation Example:
 * ```kotlin
 * class ArticleViewModel(
 *     private val getArticlesUseCase: GetArticlesUseCase,
 *     private val observeArticlesUseCase: ObserveArticlesUseCase
 * ) : ViewModel(), UseCaseExecutionScope {
 *     
 *     override val useCaseCoroutineScope: CoroutineScope = viewModelScope
 *     
 *     fun loadArticles() {
 *         getArticlesUseCase.execute(
 *             input = GetArticlesUseCase.Input(),
 *             onStart = { _loading.value = true },
 *             onSuccess = { articles -> _articles.value = articles },
 *             onError = { error -> _error.value = error }
 *         )
 *     }
 * }
 * ```
 * 
 * @see UseCase
 * @see FlowUseCase
 */
internal interface UseCaseExecutionScope {

    /**
     * The coroutine scope used for executing use cases.
     * 
     * This scope should be lifecycle-aware and automatically cancelled when
     * the implementing class is destroyed (e.g., viewModelScope for ViewModels).
     */
    val useCaseCoroutineScope: CoroutineScope

    /**
     * Executes a [UseCase] with proper lifecycle management and error handling.
     * 
     * This function automatically:
     * - Cancels any previous execution of the same use case
     * - Executes the use case in a background context
     * - Switches to the Main dispatcher for callback execution
     * - Handles cancellation gracefully without triggering error callbacks
     * 
     * @param input The input required by the use case
     * @param onStart Callback invoked when the use case execution starts (called on Main dispatcher)
     * @param onError Callback invoked when an error occurs during execution (called on Main dispatcher)
     * @param onSuccess Callback invoked when the use case completes successfully with the result (called on Main dispatcher)
     * 
     * ## Usage Example:
     * ```kotlin
     * getUserUseCase.execute(
     *     input = GetUserUseCase.Input(userId = "123"),
     *     onStart = { showLoading() },
     *     onSuccess = { user -> displayUser(user) },
     *     onError = { error -> showError(error.message) }
     * )
     * ```
     */
    fun <Input : Any, Output : Any?> UseCase<Input, Output>.execute(
        input: Input,
        onStart: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onSuccess: (Output) -> Unit = {},
    ) {
        deferred?.cancel()
        deferred = useCaseCoroutineScope
            .async(start = CoroutineStart.LAZY) { invoke(input) }
            .also { deferred ->
                useCaseCoroutineScope.launch(Dispatchers.Main) {
                    onStart()
                    try {
                        onSuccess(deferred.await())
                    } catch (_: CancellationException) {
                        // Swallow cancellations
                    } catch (throwable: Throwable) {
                        onError(throwable)
                    }
                }
            }
    }

    /**
     * Executes a [FlowUseCase] with proper lifecycle management and error handling.
     * 
     * This function automatically:
     * - Cancels any previous execution of the same flow use case
     * - Observes the Flow in the provided coroutine scope
     * - Handles cancellation gracefully without triggering error callbacks
     * - Provides callbacks for each emission and completion
     * 
     * @param input The input required by the flow use case
     * @param onStart Callback invoked when the Flow observation starts
     * @param onError Callback invoked when an error occurs during Flow observation
     * @param onNext Callback invoked for each value emitted by the Flow
     * @param onComplete Callback invoked when the Flow completes successfully (without errors)
     * 
     * ## Usage Example:
     * ```kotlin
     * observeArticlesUseCase.execute(
     *     input = ObserveArticlesUseCase.Input(category = "tech"),
     *     onStart = { showLoading() },
     *     onNext = { articles -> updateArticlesList(articles) },
     *     onComplete = { hideLoading() },
     *     onError = { error -> showError(error.message) }
     * )
     * ```
     * 
     * ## Flow Lifecycle:
     * - `onStart` is called once when the Flow observation begins
     * - `onNext` is called for each emitted value
     * - `onComplete` is called once when the Flow completes without errors
     * - `onError` is called once if an error occurs (Flow will not complete normally)
     * - Cancellation does not trigger any callbacks
     */
    fun <Input : Any, Output : Any?> FlowUseCase<Input, Output>.execute(
        input: Input,
        onStart: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onNext: (Output) -> Unit = {},
        onComplete: () -> Unit = {},
    ) {
        job?.cancel()
        job = invoke(input)
            .onStart { onStart() }
            .onEach { onNext(it) }
            .onCompletion { throwable ->
                when (throwable) {
                    is CancellationException -> Unit // Swallow cancellations
                    null -> onComplete()
                    else -> onError(throwable)
                }
            }
            .launchIn(useCaseCoroutineScope)
    }
}
