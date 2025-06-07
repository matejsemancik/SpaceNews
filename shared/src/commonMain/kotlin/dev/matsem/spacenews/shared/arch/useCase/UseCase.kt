package dev.matsem.spacenews.shared.arch.useCase

import kotlinx.coroutines.Deferred

internal abstract class UseCase<Input : Any, Output : Any?> {

    var deferred: Deferred<Output>? = null

    abstract suspend operator fun invoke(input: Input): Output
}
