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

internal interface UseCaseExecutionScope {

    val useCaseCoroutineScope: CoroutineScope

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
