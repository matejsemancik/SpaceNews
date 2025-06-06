package dev.matsem.spacenews.shared.data.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    @SerialName("name")
    val name: String,
)
