package dev.matsem.spacenews.shared.data.repo.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class ArticleId(val id: Int)
