package dev.matsem.spacenews.shared.arch.useCase

import kotlinx.coroutines.Deferred

/**
 * Abstract base class for implementing use cases that execute a single operation and return a result.
 * 
 * @param Input The type of input parameter required by the use case. Must be a non-null type.
 * @param Output The type of output returned by the use case. Can be nullable.
 * 
 * ## Usage Example:
 * ```kotlin
 * class GetUserUseCase : UseCase<GetUserUseCase.Input, User>() {
 *     data class Input(val userId: String)
 *     
 *     override suspend operator fun invoke(input: Input): User {
 *         return userRepository.getUserById(input.userId)
 *     }
 * }
 * ```
 * 
 * ## Execution:
 * Use cases should be executed through [UseCaseExecutionScope.execute] which provides
 * proper lifecycle management, error handling, and cancellation support.
 * 
 * @see FlowUseCase For streaming operations that return multiple values
 * @see UseCaseExecutionScope For executing use cases with proper lifecycle management
 */
internal abstract class UseCase<Input : Any, Output : Any?> {

    /**
     * Holds the current deferred execution of this use case.
     * Used for cancellation when a new execution is started.
     * Managed automatically by [UseCaseExecutionScope.execute].
     */
    var deferred: Deferred<Output>? = null

    /**
     * Executes the use case with the provided input.
     * 
     * This method contains the actual business logic and should be implemented by subclasses.
     * It will be called within a coroutine context when executed through [UseCaseExecutionScope.execute].
     * 
     * @param input The input parameters required for the use case execution
     * @return The result of the use case operation
     * @throws Exception Any business logic related exceptions should be propagated
     */
    abstract suspend operator fun invoke(input: Input): Output
}
