package dev.matsem.spacenews.shared.design.theme

import androidx.compose.animation.core.tween
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimationProvider
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

private val STACK_ANIMATION_TWEEN = tween<Float>(100)
private val STACK_ANIMATION_CROSS_FADE_SPEC = fade(STACK_ANIMATION_TWEEN) + scale(STACK_ANIMATION_TWEEN)

/**
 * Stack animation provides animation for all stacks, not necessary to implement fallback animation in children components.
 */
internal object SpaceNewsStackAnimationProvider : StackAnimationProvider {
    override fun <C : Any, T : Any> provide(): StackAnimation<C, T> = stackAnimation(STACK_ANIMATION_CROSS_FADE_SPEC)
}
