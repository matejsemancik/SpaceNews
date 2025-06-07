package dev.matsem.spacenews.shared.arch.useCase

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

/**
 * Abstract base class for implementing use cases that return a stream of values using Flow.
 * 
 * Flow use cases are designed for operations that emit multiple values over time,
 * such as observing data changes, real-time updates, or streaming operations.
 * 
 * @param Input The type of input parameter required by the use case. Must be a non-null type.
 * @param Output The type of output emitted by the Flow. Can be nullable.
 * 
 * ## Usage Example:
 * ```kotlin
 * class ObserveArticlesUseCase : FlowUseCase<ObserveArticlesUseCase.Input, List<Article>>() {
 *     data class Input(val category: String)
 *     
 *     override operator fun invoke(input: Input): Flow<List<Article>> {
 *         return articleRepository.observeArticlesByCategory(input.category)
 *     }
 * }
 * ```
 * 
 * ## Execution:
 * Flow use cases should be executed through [UseCaseExecutionScope.execute] which provides
 * proper lifecycle management, error handling, and automatic cancellation of previous executions.
 * 
 * @see UseCase For single-result operations
 * @see UseCaseExecutionScope For executing flow use cases with proper lifecycle management
 */
internal abstract class FlowUseCase<Input : Any, Output : Any?> {

    /**
     * Holds the current job for the running Flow.
     * Used for cancellation when a new execution is started.
     * Managed automatically by [UseCaseExecutionScope.execute].
     */
    var job: Job? = null

    /**
     * Creates and returns a Flow that emits values based on the provided input.
     * 
     * This method contains the actual business logic for creating the reactive stream
     * and should be implemented by subclasses. The returned Flow will be observed
     * when executed through [UseCaseExecutionScope.execute].
     * 
     * @param input The input parameters required for the Flow creation
     * @return A Flow that will emit values of type [Output]
     */
    abstract operator fun invoke(input: Input): Flow<Output>
}
