package dev.matsem.spacenews.shared.arch.useCase

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

internal abstract class FlowUseCase<Input : Any, Output : Any?> {

    var job: Job? = null

    abstract operator fun invoke(input: Input): Flow<Output>
}
